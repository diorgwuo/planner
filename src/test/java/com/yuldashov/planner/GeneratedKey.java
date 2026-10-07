package com.yuldashov.planner;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;

public class GeneratedKey {
    public static void main(String[] agrs) {
        SecretKey key = Jwts.SIG.HS512.key().build();
        byte[] bytes = key.getEncoded();
        String secretKey = Encoders.BASE64.encode(bytes);
        System.out.println(secretKey);
    }
}
