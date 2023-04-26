package com.rongtuoyouxuan.qfcommon.player.exo;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 *
 * date:2022/8/8-17:04
 * des:
 */
public class EglUtil {
    public static final int NO_TEXTURE = -1;
    private static final int FLOAT_SIZE_BYTES = 4;

    public EglUtil() {
    }

    public static int loadShader(String strSource, int iType) {
        int[] compiled = new int[1];
        int iShader = GLES20.glCreateShader(iType);
        GLES20.glShaderSource(iShader, strSource);
        GLES20.glCompileShader(iShader);
        GLES20.glGetShaderiv(iShader, 35713, compiled, 0);
        if (compiled[0] == 0) {
            Log.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(iShader));
            return 0;
        } else {
            return iShader;
        }
    }

    public static int createProgram(int vertexShader, int pixelShader) throws GLException {
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            throw new RuntimeException("Could not create program");
        } else {
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, pixelShader);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
            if (linkStatus[0] != 1) {
                GLES20.glDeleteProgram(program);
                throw new RuntimeException("Could not link program");
            } else {
                return program;
            }
        }
    }

    public static void setupSampler(int target, int mag, int min) {
        GLES20.glTexParameterf(target, 10240, (float)mag);
        GLES20.glTexParameterf(target, 10241, (float)min);
        GLES20.glTexParameteri(target, 10242, 33071);
        GLES20.glTexParameteri(target, 10243, 33071);
    }

    public static int createBuffer(float[] data) {
        return createBuffer(toFloatBuffer(data));
    }

    public static int createBuffer(FloatBuffer data) {
        int[] buffers = new int[1];
        GLES20.glGenBuffers(buffers.length, buffers, 0);
        updateBufferData(buffers[0], data);
        return buffers[0];
    }

    public static FloatBuffer toFloatBuffer(float[] data) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(data.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(data).position(0);
        return buffer;
    }

    public static void updateBufferData(int bufferName, FloatBuffer data) {
        GLES20.glBindBuffer(34962, bufferName);
        GLES20.glBufferData(34962, data.capacity() * 4, data, 35044);
        GLES20.glBindBuffer(34962, 0);
    }

    public static int loadTexture(Bitmap img, int usedTexId, boolean recycle) {
        int[] textures = new int[1];
        if (usedTexId == -1) {
            GLES20.glGenTextures(1, textures, 0);
            GLES20.glBindTexture(3553, textures[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0F);
            GLES20.glTexParameterf(3553, 10241, 9729.0F);
            GLES20.glTexParameterf(3553, 10242, 33071.0F);
            GLES20.glTexParameterf(3553, 10243, 33071.0F);
            GLUtils.texImage2D(3553, 0, img, 0);
        } else {
            GLES20.glBindTexture(3553, usedTexId);
            GLUtils.texSubImage2D(3553, 0, 0, 0, img);
            textures[0] = usedTexId;
        }

        if (recycle) {
            img.recycle();
        }

        return textures[0];
    }
}
