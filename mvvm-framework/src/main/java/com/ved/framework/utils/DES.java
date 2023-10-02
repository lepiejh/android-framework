package com.ved.framework.utils;

import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class DES {

    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};


    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        if (TextUtils.isEmpty(encryptString)){
            return encryptString;
        }
        encryptKey = encryptKey.substring(0, 24);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        if (TextUtils.isEmpty(decryptString)){
            return decryptString;
        }
        decryptKey = decryptKey.substring(0, 24);
        byte[] byteMi = Base64.decode(decryptString, Base64.DEFAULT);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }
}
