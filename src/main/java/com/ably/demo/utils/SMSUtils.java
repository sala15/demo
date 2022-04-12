package com.ably.demo.utils;

import com.ably.demo.errors.SMSException;

import java.util.Random;

public class SMSUtils {
    private static SMSUtils instance = new SMSUtils();
    private Random rand;

    private SMSUtils() {
        rand = new Random();
    }

    public static SMSUtils getInstance() {
        return instance;
    }

    public String sendSMSAuthCode(String phoneNumber) throws SMSException {
        String authCode = String.format("%06d", rand.nextInt(999999));

        //TODO SMS 발송

        return authCode;
    }
}
