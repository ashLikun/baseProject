package com.ashlikun.baseproject.module.other.view;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 作者　　: 李坤
 * 创建时间: 2020/8/26　14:12
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
class AES {
    private static final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";

    private static String ivParameter = "i4r$cnEQK#fjR$4u";

    private final int HASH_ITERATIONS = 10000;

    private IvParameterSpec IV;

    private final String KEY_GENERATION_ALG = "PBEWITHSHAANDTWOFISH-CBC";

    private final int KEY_LENGTH = 128;

    private char[] humanPassphrase = {
            'P', 'e', 'r', ' ', 'v', 'a', 'l', 'l', 'u', 'm',
            ' ', 'd', 'u', 'c', 'e', 's', ' ', 'L', 'a', 'b',
            'a', 'n', 't'};

    private byte[] iv = ivParameter.getBytes();

    private SecretKeyFactory keyfactory = null;

    private PBEKeySpec myKeyspec = new PBEKeySpec(this.humanPassphrase, this.salt, 10000, 128);

    String sKey = "d$ya*F8j52eGj^UZ";

    private byte[] salt = "I0g2Vq!8U5StM9NE".getBytes();

    private SecretKey sk = null;

    private SecretKeySpec skforAES = null;

    static {

    }

    public AES() {
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHSHAANDTWOFISH-CBC");
            this.keyfactory = secretKeyFactory;
            this.sk = secretKeyFactory.generateSecret(this.myKeyspec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.e("AESdemo", "no key factory support for PBEWITHSHAANDTWOFISH-CBC");
        } catch (InvalidKeySpecException invalidKeySpecException) {
            Log.e("AESdemo", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
        }
        try {
            this.skforAES = new SecretKeySpec(this.sKey.getBytes("ASCII"), "AES");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }
        this.IV = new IvParameterSpec(this.iv);
    }

    private byte[] addPadding(byte[] paramArrayOfByte) {
        int j = paramArrayOfByte.length;
        int i = 16;
        j = 16 - j % 16;
        if (j != 0) {
            i = j;
        }
        byte[] arrayOfByte = new byte[paramArrayOfByte.length + i];
        for (j = 0; j < paramArrayOfByte.length; j++) {
            arrayOfByte[j] = paramArrayOfByte[j];
        }
        for (j = paramArrayOfByte.length; j < paramArrayOfByte.length + i; j++) {
            arrayOfByte[j] = (byte) i;
        }
        return arrayOfByte;
    }

    public static String aesdecrypt(String paramString1, String paramString2) throws Exception {
        try {
            byte[] arrayOfByte = Base64.decode(paramString1, 0);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(paramString2.substring(paramString2.length() - 16).getBytes());
            return new String(decrypt2("AES/CBC/PKCS7Padding", new SecretKeySpec(paramString2.substring(0, 16).getBytes("ASCII"), "AES"), ivParameterSpec, arrayOfByte));
        } catch (Exception paramStrings1) {
            paramStrings1.printStackTrace();
            return "{code=1,msg=\"������������\"}";
        }
    }

    private static byte[] decrypt2(String paramString, SecretKey paramSecretKey, IvParameterSpec paramIvParameterSpec, byte[] paramArrayOfByte) {
        try {
            Cipher cipher = Cipher.getInstance(paramString);
            cipher.init(2, paramSecretKey, paramIvParameterSpec);
            return cipher.doFinal(paramArrayOfByte);
        } catch (NoSuchAlgorithmException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("no cipher getinstance support for ");
            stringBuilder.append(e);
            Log.e("AESdemo", stringBuilder.toString());
        } catch (NoSuchPaddingException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("no cipher getinstance support for padding ");
            stringBuilder.append(e);
            Log.e("AESdemo", stringBuilder.toString());
        } catch (InvalidKeyException e) {
            Log.e("AESdemo", "invalid key exception");
        } catch (InvalidAlgorithmParameterException e) {
            Log.e("AESdemo", "invalid algorithm parameter exception");
        } catch (IllegalBlockSizeException e) {
            Log.e("AESdemo", "illegal block size exception");
        } catch (BadPaddingException e) {
            Log.e("AESdemo", "bad padding exception");
            e.printStackTrace();
        }
        return null;
    }
}
