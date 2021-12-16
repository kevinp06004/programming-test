package com.kevin.aes;

import java.util.Base64;
import java.util.HashMap;
import java.util.Base64.Encoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AesController {

    // Input: {"plain_text": "Apple", "aes_key": "404D635166546A576E5A723475377721"}
    // Output: {"cipher_text": "C9E461E80EC3047944ACAE96A9896BC3"}
    @PostMapping(value = "/encrypt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, String> aesEncrypt(@RequestBody HashMap<String, String> req) {

        String plain_text = req.get("plain_text");
        String aes_key = req.get("aes_key");

        // checking request body
        if (plain_text == null || aes_key == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "plain_text or aes_key is empty");

        // encrypting
        HashMap<String, String> res = new HashMap<>();
        try {
            byte[] encryptedBytes = AES.encrypt(plain_text, aes_key);
            res.put("cipher_text", new String(Hex.encode(encryptedBytes)).toUpperCase());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return res;
    }

    // Input: {"cipher_text": "C9E461E80EC3047944ACAE96A9896BC3", "aes_key":"404D635166546A576E5A723475377721"}
    // Output: {"plain_text":"Apple", "base64":"QXBwbGU="}
    @PostMapping(value = "/decrypt", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, String> aesDecrypt(@RequestBody HashMap<String, String> req) {

        String cipher_text = req.get("cipher_text");
        String aes_key = req.get("aes_key");

        // checking request body
        if (cipher_text == null || aes_key == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cipher_text or aes_key is empty");

        // decrypting
        HashMap<String, String> res = new HashMap<>();
        try {
            byte[] decryptedBytes = AES.decrypt(cipher_text, aes_key);
            res.put("plain_text", new String(decryptedBytes));

            // covert byte[] to Base64 string
            Encoder base64Encoder = Base64.getEncoder();
            res.put("base64", base64Encoder.encodeToString(decryptedBytes));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return res;
    }

}
