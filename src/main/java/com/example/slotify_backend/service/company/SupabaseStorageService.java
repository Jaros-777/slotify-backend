package com.example.slotify_backend.service.company;

import com.example.slotify_backend.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

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
                throw new UploadFailedException("Upload failed: " + response.body());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UploadFailedException("Upload was ", e);
        } catch (Exception e) {
            throw new UploadFailedException("Error uploading to Supabase", e);
        }

    }

    public void deletePictureByPublicUrl(String publicUrl) {
        try {
            String path = extractPathFromPublicUrl(publicUrl);

            String deleteUrl = supabaseUrl.trim()
                    + "/storage/v1/object/"
                    + supabaseBucket.trim()
                    + "/"
                    + path;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(deleteUrl))
                    .header("Authorization", "Bearer " + supabaseKey.trim())
                    .header("apikey", supabaseKey.trim())
                    .DELETE()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new UploadFailedException("Delete failed: " + response.body());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UploadFailedException("Upload was ", e);
        }catch (Exception e) {
            throw new UploadFailedException("Error deleting from Supabase", e);
        }
    }


    private String extractPathFromPublicUrl(String publicUrl) {
        String marker = "/storage/v1/object/public/" + supabaseBucket + "/";
        int index = publicUrl.indexOf(marker);

        if (index == -1) {
            throw new IllegalArgumentException("Invalid Supabase public URL");
        }

        return publicUrl.substring(index + marker.length());
    }


}
