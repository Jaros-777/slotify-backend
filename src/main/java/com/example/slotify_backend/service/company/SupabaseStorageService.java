package com.example.slotify_backend.service.company;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SupabaseStorageService {

    @Value("${supabase_url}")
    private String supabaseUrl;
    @Value("${supabase_key}")
    private String supabaseKey;
    @Value("${supabase_bucket}")
    private String supabaseBucket;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String uploadPicture(MultipartFile file, String path) {
        try {
            String uploadUrl = supabaseUrl + "/storage/v1/object/" + supabaseBucket + "/" + path;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uploadUrl))
                    .header("Authorization", "Bearer " + supabaseKey)
                    .header("Content-Type", file.getContentType())
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return supabaseUrl + "/storage/v1/object/public/" + supabaseBucket + "/" + path;
            } else {
                throw new RuntimeException("Upload failed: " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error uploading to Supabase", e);
        }

    }
}
