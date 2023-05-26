package com.rongtuoyouxuan.chatlive.crtutil.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author sensetime on 16-6-22.
 */
public class ZipUtils {

    private static final String TAG = ZipUtils.class.getSimpleName();

    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     */
    public synchronized static int upZipFile(File zipFile, String folderPath) {

        OutputStream os;
        InputStream is;
        ZipFile zfile;
        try {
            zfile = new ZipFile(zipFile);
            if (zfile == null) {
                return 0;
            }
            Enumeration zList = zfile.entries();
            if (zList == null) {
                return 0;
            }
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    //PLog.d(TAG, "ze.getName() = " + ze.getName());
                    String dirstr = folderPath + ze.getName();
                    dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                    //PLog.d(TAG, "str = " + dirstr);
                    File f = new File(dirstr);
                    f.mkdir();
                    continue;
                }
                //PLog.d(TAG, "ze.getName() = " + ze.getName());

                os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.flush();
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (zfile != null) {
                zfile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    public synchronized static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            //PLog.d(TAG, "1ret = " + ret);
            if (!ret.exists())
                ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                //PLog.d(TAG, "substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            //PLog.d(TAG, "2ret = " + ret);
            return ret;
        }

        return ret;
    }
}
