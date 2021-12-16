package com.kevin.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Hex;

public class AES {

    private static Cipher initCipher(int mode, String key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
                
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(mode, secretKey);

        return cipher;
    }

    public static byte[] encrypt(String plain_text, String key)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {

        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plain_text.getBytes());
    }

    public static byte[] decrypt(String cipher_text, String key) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(Hex.decode(cipher_text));
    }

}
