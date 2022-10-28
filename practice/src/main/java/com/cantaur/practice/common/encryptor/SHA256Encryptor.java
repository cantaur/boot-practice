package com.cantaur.practice.common.encryptor;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Encryptor {

    public static String encrypt(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(str.getBytes());
        return Hex.encodeHexString(digest);
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        System.out.println(SHA256Encryptor.encrypt("aaaa!234"));
    }
}
