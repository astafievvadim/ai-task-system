package com.astafiev.api_service.util;

public class TaskCryptUtil {

    public static String encrypt(String key, String text){

        //Just a filler for now;

        return "ENCRYPTED-"+text;

    }

    public String decrypt(String key, String encryptedText){

        //Again, just a filler for now;

        return encryptedText.substring(9);

    }



}
