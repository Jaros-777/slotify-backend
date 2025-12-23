package com.example.slotify_backend.service.company;

import com.example.slotify_backend.dto.client.ClientDetailsDTO;
import com.example.slotify_backend.dto.company.TokenResponeDTO;
import com.example.slotify_backend.dto.company.UserRequestLoginDTO;
import com.example.slotify_backend.dto.company.UserRequestRegisterDTO;
import com.example.slotify_backend.entity.*;
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
    private EventRepository eventRepository;

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
    private final ClientDetailsMapper clientDetailsMapper;


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

            Availability availability0 = new Availability(
                    false,
                    "18:00",
                    "08:00",
                    0,
                    user
            );
            Availability availability1 = new Availability(
                    false,
                    "18:00",
                    "08:00",
                    1,
                    user
            );
            Availability availability2 = new Availability(
                    false,
                    "18:00",
                    "08:00",
                    2,
                    user
            );
            Availability availability3 = new Availability(
                    false,
                    "18:00",
                    "08:00",
                    3,
                    user
            );
            Availability availability4 = new Availability(
                    false,
                    "18:00",
                    "08:00",
                    4,
                    user
            );
            Availability availability5 = new Availability(
                    true,
                    "18:00",
                    "08:00",
                    5,
                    user
            );
            Availability availability6 = new Availability(
                    true,
                    "18:00",
                    "08:00",
                    6,
                    user
            );

            availabiltyRepository.save(availability0);
            availabiltyRepository.save(availability1);
            availabiltyRepository.save(availability2);
            availabiltyRepository.save(availability3);
            availabiltyRepository.save(availability4);
            availabiltyRepository.save(availability5);
            availabiltyRepository.save(availability6);


            BusinessProfile businessProfile = new BusinessProfile(
                    user,
                    dto.businessName()
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

            Client client = clientRepository.findByEmail(dto.email());

            User user = new User(
                    dto.name(), dto.email(), passwordEncoder.encode(dto.password()), CLIENT
            );
            user.setPhone(dto.phone());
            userRepository.save(user);

            if(client != null) {
                eventRepository.findAllByClientId(client.getId()).forEach(event -> {
                    event.setClient(client);
                });
                clientRepository.deleteById(client.getId());
            }


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
