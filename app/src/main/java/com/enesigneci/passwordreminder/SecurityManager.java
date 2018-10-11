package com.enesigneci.passwordreminder;

import android.util.Base64;

import java.nio.charset.StandardCharsets;


public class SecurityManager
{

    /**
     * @param message the message to be encoded
     *
     * @return the enooded from of the message
     */
    public static String toBase64(String message) {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.NO_WRAP);
        return base64;
    }

    /**
     * @param message the encoded message
     *
     * @return the decoded message
     */
    public static String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.NO_WRAP);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }
}