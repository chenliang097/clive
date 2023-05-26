package com.rongtuoyouxuan.chatlive.crtutil.util;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static final String TAG = "AESUtils";

    private String iv = "995d1b5ebbac3761";//虚拟的 iv (需更改)
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;
    private Cipher cipher;
    private byte[] mSeckey;

    public AESUtils(String seckey) {
        ivspec = new IvParameterSpec(iv.getBytes());
        //keyspec = new SecretKeySpec(hexToBytes(seckey), "AES");
        mSeckey = seckey.getBytes();
        keyspec = new SecretKeySpec(seckey.getBytes(), "AES");

        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public AESUtils(String aesKey, String iv) {
        try {
            ivspec = new IvParameterSpec(iv.getBytes());
            mSeckey = aesKey.getBytes();
            keyspec = new SecretKeySpec(aesKey.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }

        int len = data.length;
        String str = "";
        for (int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16)
                str = str + "0" + Integer.toHexString(data[i] & 0xFF);
            else
                str = str + Integer.toHexString(data[i] & 0xFF);
        }
        return str;
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            int len = str.length() / 2;
            byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    public byte[] encrypt(String text) throws Exception {
        if (text == null || text.length() == 0)
            throw new Exception("Empty string");

        byte[] encrypted;
        try {
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = text.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            encrypted = cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }

        return encrypted;
    }

    public byte[] decrypt(String code) throws Exception {
        if (code == null || code.length() == 0)
            throw new Exception("Empty string");

        byte[] decrypted = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = cipher.doFinal(hexToBytes(code));
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }

    public byte[] xorEncrypt(byte[] text) {
        byte[] result = new byte[text.length];
        byte[] keyArray = mSeckey;
        byte key;
        int size = text.length;
        for (int i = 0; i < size; i++) {
            key = keyArray[i % keyArray.length];
            result[i] = (byte) (text[i] ^ key);
        }
        return result;
    }
}