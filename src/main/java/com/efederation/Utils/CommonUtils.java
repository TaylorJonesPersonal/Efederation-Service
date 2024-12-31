package com.efederation.Utils;

import java.util.Base64;

public class CommonUtils {

    public static String getBase64Image(byte[] imageData) {
        if(imageData != null) {
            return Base64.getEncoder().encodeToString(imageData);
        }
        return "";
    }
}
