package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.TokenResponeDTO;
import com.example.slotify_backend.dto.company.UserRequestLoginDTO;
import com.example.slotify_backend.dto.company.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.*;
import com.example.slotify_backend.entity.BusinessAddress;
import com.example.slotify_backend.entity.enums.Role;
import com.example.slotify_backend.exception.UserAlreadyExistException;
import com.example.slotify_backend.mapper.ClientDetailsMapper;
import com.example.slotify_backend.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.example.slotify_backend.entity.enums.Role.CLIENT;
import static com.example.slotify_backend.entity.enums.Role.USER_COMPANY;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {


    @Autowired
    private BusinessProfileRepository businessProfileRepository;
    @Autowired
    private BusinessAddressRepository businessAddressRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();

    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ServiceRepository serviceRepository;
    private final AvailabiltyRepository availabiltyRepository;
    private final ClientRepository clientRepository;


    @Transactional
    public void addUser(UserRequestRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistException("User already exists");
        }
        if (dto.role() == USER_COMPANY) {
            User user = new User(
                    dto.name(), dto.email(), passwordEncoder.encode(dto.password()), USER_COMPANY
            );
            userRepository.save(user);

            for(int i =0; i<7; i++){
                Availability availability = new Availability(
                        false,
                        "18:00",
                        "08:00",
                        i,
                        user
                );
                availabiltyRepository.save(availability);
            }



            BusinessAddress businessAddress = new BusinessAddress();
            businessAddressRepository.save(businessAddress);


            BusinessProfile businessProfile = new BusinessProfile(
                    user,
                    dto.businessName(),
                    businessAddress
            );
            businessProfileRepository.save(businessProfile);
            user.setBusinessProfile(businessProfile);
            userRepository.save(user);


            ServiceEntity serviceEntity = new ServiceEntity(
                    user,
                    "Not assigned",
                    0,
                    900,//15min
                    "",
                    false
            );
            serviceRepository.save(serviceEntity);



        } else {

            User user = new User(
                    dto.name(), dto.email(), passwordEncoder.encode(dto.password()), CLIENT, dto.phone()
            );
            userRepository.save(user);

            Client client = clientRepository.findByEmail(dto.email());
            if(client==null){
                client = new Client(dto.name(), dto.email(),dto.phone());
            }else{
                user.setEmail(client.getEmail());
                user.setPhone(client.getPhone());
                user.setName(client.getName());
            }

            client.setUserAccount(user);
            clientRepository.save(client);


        }

    }

    public TokenResponeDTO login(@Valid @RequestBody UserRequestLoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getEmail(), user.getId());
        return new TokenResponeDTO(token, user.getRole());
    }

}
