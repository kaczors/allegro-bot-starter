package com.github.kaczors.allegro.confitura;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaTest {

    @Test
    public void test() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = "password";

        md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] sha256 = md.digest();

        System.out.println(Base64.encode(sha256));
    }

}
