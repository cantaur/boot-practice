package com.cantaur.practice.common.encryptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AES256Encryptor {

    public static final String GENERATE_KEY = "560$%C3EEEBA5#$F6ACASHIEREST0901";

    private static final String INSTANCE = "AES/CBC/PKCS5Padding";

    public static String encrypt(String str)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(str, generateKeys());
    }

    public static String encrypt(String str, AES256Keys keys)
            throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] key256Data = keys.getSecretKey().getBytes(CharEncoding.UTF_8);
        byte[] key128Data = keys.getIv().getBytes(CharEncoding.UTF_8);

        Cipher aesCipher = Cipher.getInstance(INSTANCE);
        aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key256Data, "AES"),
                new IvParameterSpec(key128Data));

        byte[] encrypted = aesCipher.doFinal(str.getBytes());
        byte[] base64Encoded = Base64.encodeBase64(encrypted);

        return new String(base64Encoded, CharEncoding.UTF_8);
    }

    public static String decrypt(String str)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return decrypt(str, generateKeys());
    }

    public static String decrypt(String str, AES256Keys keys)
            throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        byte[] key256Data = keys.getSecretKey().getBytes(CharEncoding.UTF_8);
        byte[] key128Data = keys.getIv().getBytes(CharEncoding.UTF_8);

        Cipher aesCipher = Cipher.getInstance(INSTANCE);
        aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key256Data, "AES"),
                new IvParameterSpec(key128Data));

        byte[] base64Decoded = Base64.decodeBase64(str.getBytes(CharEncoding.UTF_8));
        byte[] decrypted = aesCipher.doFinal(base64Decoded);

        return new String(decrypted, CharEncoding.UTF_8);
    }

    public static AES256Keys generateKeys() throws NoSuchAlgorithmException {
        return generateKeys(GENERATE_KEY.substring(0, 15));
    }

    public static AES256Keys generateKeys(String seed) throws NoSuchAlgorithmException {
        StringBuilder encryptData = new StringBuilder();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(GENERATE_KEY.getBytes());

        byte[] digest = sha.digest();
        for (int i = 0; i < digest.length; i++) {
            encryptData.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        return new AES256Keys(encryptData.toString().substring(5, 37),
                encryptData.toString().substring(5, 21));
    }

    @Data
    @AllArgsConstructor
    public static class AES256Keys {

        private String secretKey;

        private String iv;

    }

    public static void main(String args[]) throws Exception {
        System.out.println(AES256Encryptor.encrypt("01012341234"));
    }

}