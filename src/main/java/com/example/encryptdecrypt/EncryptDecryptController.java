package com.example.encryptdecrypt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class EncryptDecryptController {
    @Value("${encrypt.key}")
    private String key;

    @PostMapping("/encrypt")
    public void encrypt(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        process(response, file, Cipher.ENCRYPT_MODE);
    }

    @PostMapping("/decrypt")
    public void decrypt(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        process(response, file, Cipher.DECRYPT_MODE);
    }

    private void process(HttpServletResponse response, MultipartFile file, int mode) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File not attached");
        }

        response.setContentType("text/plain");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"");

        try (var writer = response.getOutputStream()) {
            var cipher = Cipher.getInstance("AES");
            cipher.init(mode, new SecretKeySpec(key.getBytes(), "AES"));
            writer.write(cipher.doFinal(file.getBytes()));
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
