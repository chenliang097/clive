package com.rongtuoyouxuan.chatlive.crtutil.util;


import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5FileUtils {
    private static final String TAG = "MD5FileUtils";

    public static boolean checkMD5(String md5, File updateFile) {
        if (TextUtils.isEmpty(md5) || updateFile == null || !updateFile.isFile() || !updateFile.exists()) {
            Logger.t(TAG).e("MD5FileUtils string empty or updateFile null");
            return false;
        }

        String calculatedDigest = calculateMD5(updateFile);
        if (calculatedDigest == null) {
            Logger.t(TAG).e("calculatedDigest null");
            return false;
        }

        Logger.t(TAG).d("Calculated digest: " + calculatedDigest);
        Logger.t(TAG).d("Provided digest: " + md5);

        boolean isSame = calculatedDigest.equalsIgnoreCase(md5);
        Logger.t(TAG).d("MD5 isSame: " + isSame);
        return isSame;
    }

    public static String calculateMD5(File updateFile) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Logger.t(TAG).e("Exception while getting digest", e);
            return null;
        }

        InputStream is;
        try {
            is = new FileInputStream(updateFile);
        } catch (FileNotFoundException e) {
            Logger.t(TAG).e("Exception while getting FileInputStream", e);
            return null;
        }

        byte[] buffer = new byte[8192];
        int read;
        try {
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            // Fill to 32 chars
            output = String.format("%32s", output).replace(' ', '0');
            return output;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Logger.t(TAG).e("Exception on closing MD5 input stream", e);
                return null;
            }
        }
    }

    /**
     * 使用获取字节的数目。
     * @param str
     * @return
     */
    public static int getMD5StringLength(String str){
        int length = 0;
        if (!TextUtils.isEmpty(str)){
            byte[] data = str.getBytes();
            length = data.length;
        }
        return length;
    }
}