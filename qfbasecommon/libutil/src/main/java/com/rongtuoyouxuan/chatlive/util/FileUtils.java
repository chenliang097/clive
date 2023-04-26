package com.rongtuoyouxuan.chatlive.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASnow
 * @date 2019/1/14
 * 文件   ChatLive
 * 描述   操作本地文件
 */
public class FileUtils {

    /**
     * 创建文本文件
     *
     * @param path     文件地址
     * @param fileName 文件名字
     * @param text     文本文件中的内容
     */
    public static void writetoFile(String path, String fileName, String text) {
        String needWriteMessage = text;
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            FileWriter filerWriter = new FileWriter(file, false);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的文件
     *
     * @param path     文件路径
     * @param fileName 文件名字
     */
    public static void delFile(String path, String fileName) {// 删除日志文件
        File file = new File(path, fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void delFile(String fileNamePath) {// 删除日志文件
        if (TextUtils.isEmpty(fileNamePath)) return;
        Log.e("delFile", "delFile: 删除订单");
        File file = new File(fileNamePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件路径和名字
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 用递归方法，获取文件夹下的所有文件
     *
     * @param path 文件夹路径
     * @return
     */
    public static List<String> getAllFiles(String path) {
        List<String> filePathList = new ArrayList<String>();
        File rootFile = new File(path);
        if (rootFile.exists()) {
            File files[] = rootFile.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        getAllFiles(f.getPath());
                    } else {
                        filePathList.add(f.getPath());
                    }
                }
            }
        }
        return filePathList;

    }

    /**
     * 读取本地Json文件
     *
     * @param path Json文件路径
     * @return
     */
    public static String getStringFromSD(final String path) {
        StringBuilder stringBuilder = null;
        File file = new File(path);
        if (!file.exists()) {
            return null;
        } else {
            try {
                FileInputStream f = new FileInputStream(path);
                InputStreamReader inputStreamReader = new InputStreamReader(f, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 读取本地Json文件
     *
     * @param path Json文件路径
     * @return
     */
    public static String getFileName(final String path) {
        String fileName = "";
        File file = new File(path);
        if (!file.exists()) {
            return null;
        } else {
            fileName = file.getName();
        }
        return fileName;
    }

    public static void writeToFile(InputStream stream, File descPath, ICallFileResult callBack) {
        FileChannel outChannel;
        ReadableByteChannel inChannel;
        try {
            outChannel = new FileOutputStream(descPath).getChannel();
            inChannel = Channels.newChannel(stream);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            inChannel.close();
            outChannel.close();
            callBack.onSuccess();
        } catch (IOException ioException) {
            Log.d(">>writeToFile>>>", ioException.getMessage());
            callBack.onFail();
        }
    }

    public static void writeToFile(InputStream stream, File descPath, ICallProgressFileResult callBack) {
        FileChannel outChannel;
        ReadableByteChannel inChannel;
        try {
            outChannel = new FileOutputStream(descPath).getChannel();
            inChannel = Channels.newChannel(stream);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }

            inChannel.close();
            outChannel.close();
            callBack.onSuccess();
        } catch (IOException ioException) {
            callBack.onFail();
        }
    }
}