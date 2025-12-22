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
            String cleanPath = path.startsWith("/") ? path.substring(1) : path;
            String uploadUrl = supabaseUrl.trim() + "/storage/v1/object/" + supabaseBucket.trim() + "/" + cleanPath;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uploadUrl))
                    .header("Authorization", "Bearer " + supabaseKey.trim())
                    .header("Content-Type", file.getContentType())
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return supabaseUrl.trim() + "/storage/v1/object/public/" + supabaseBucket.trim() + "/" + path;
            } else {
                throw new RuntimeException("Upload failed: " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error uploading to Supabase", e);
        }

    }
    public void deletePicture(String path) {
        try {
            String deleteUrl = supabaseUrl.trim() + "/storage/v1/object/" + supabaseBucket.trim() + "/" + path;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(deleteUrl))
                    .header("Authorization", "Bearer " + supabaseKey.trim())
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new RuntimeException("Delete failed: " + path + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error deleting from Supabase", e);
        }
    }

}
