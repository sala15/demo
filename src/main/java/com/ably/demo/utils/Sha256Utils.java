package com.ably.demo.utils;

import net.bytebuddy.utility.RandomString;
import org.apache.catalina.util.StandardSessionIdGenerator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utils {
    private static Sha256Utils instance = new Sha256Utils();
    private RandomString rand;

    private Sha256Utils() {
        rand = new RandomString();
    }

    public static Sha256Utils getInstance() {
        return instance;
    }

    public String makeSessionId() throws NoSuchAlgorithmException {

        String originalString = rand.nextString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
