package com.online_market.utils;

import java.io.*;
import java.security.*;

/**
 * Class for calculating md5hex of string.
 * It is used to find user's gravatar
 *
 * @author Siarhei
 * @version 1.0
 */
public class MD5Util {
    public static String hex(byte[] array) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString();
    }

    public static String md5Hex(String message) {

        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

        return null;
    }

    public static boolean check(String message, String storedMessage){
        return storedMessage.equals(md5Hex(message));
    }
}