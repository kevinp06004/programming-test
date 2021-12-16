package com.kevin.aes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AesController.class)
public class AesControllerTest {

        @Autowired
        private MockMvc mockMvc;

        private static final String ENCRYPT_PATH = "/encrypt";
        private static final String DECRYPT_PATH = "/decrypt";

        // test req body
        //encrypt
        @Test
        public void encryptShouldReturn400EmptyKey() throws Exception {
                this.mockMvc.perform(
                                post(ENCRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"plain_text\": \"Apple\"}"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void encryptShouldReturn400EmptyText() throws Exception {
                this.mockMvc.perform(
                                post(ENCRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"aes_key\": \"404D635166546A576E5A723475377721\"}"))
                                .andExpect(status().isBadRequest());
        }

        //decrypt
        @Test
        public void decryptShouldReturn400EmptyKey() throws Exception {
                this.mockMvc.perform(
                                post(DECRYPT_PATH)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"cipher_text\": \"C9E461E80EC3047944ACAE96A9896BC3\"}"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        public void decryptShouldReturn400EmptyText() throws Exception {
                this.mockMvc.perform(
                                post(DECRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"aes_key\": \"404D635166546A576E5A723475377721\"}"))
                                .andExpect(status().isBadRequest());
        }


        //test error handling
        //encrypt
        @Test
        public void encryptShouldReturn500InvalidKey() throws Exception {
                this.mockMvc.perform(
                                post(ENCRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"plain_text\": \"Apple\", \"aes_key\": \"wrongKey\"}"))
                                .andExpect(status().isInternalServerError());
        }

        //decrypt
        @Test
        public void decryptShouldReturn500InvalidKey() throws Exception {
                this.mockMvc.perform(
                                post(DECRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"cipher_text\": \"C9E461E80EC3047944ACAE96A9896BC3\", \"aes_key\": \"wrongKey\"}"))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        public void decryptShouldReturn500InvalidCipher() throws Exception {
                this.mockMvc.perform(
                                post(DECRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"cipher_text\": \"Apple\", \"aes_key\": \"404D635166546A576E5A723475377721\"}"))
                                .andExpect(status().isInternalServerError());
        }


        //test normal operation
        @Test
        public void encryptShouldReturnCipherText() throws Exception {
                this.mockMvc.perform(
                                post(ENCRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"plain_text\": \"Apple\", \"aes_key\": \"404D635166546A576E5A723475377721\"}"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{'cipher_text': 'C9E461E80EC3047944ACAE96A9896BC3'}"));
        }

        @Test
        public void decryptShouldReturnPlainTextAndBase64() throws Exception {
                this.mockMvc.perform(
                                post(DECRYPT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"cipher_text\": \"C9E461E80EC3047944ACAE96A9896BC3\", \"aes_key\": \"404D635166546A576E5A723475377721\"}"))
                                .andExpect(status().isOk())
                                .andExpect(content().json("{'plain_text': 'Apple', 'base64': 'QXBwbGU='}"));
        }
}
