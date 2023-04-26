package com.rongtuoyouxuan.qfcommon.player.exo.filter;

import android.content.res.Resources;
import android.opengl.GLES20;

import com.rongtuoyouxuan.qfcommon.player.exo.EFramebufferObject;
import com.rongtuoyouxuan.qfcommon.player.exo.EglUtil;

import java.util.HashMap;

/**
 * 
 * date:2022/8/8-16:59
 * des:
 */
public class GlFilter {
    public static final String DEFAULT_UNIFORM_SAMPLER = "sTexture";
    protected static final String DEFAULT_VERTEX_SHADER = "attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvoid main() {\ngl_Position = aPosition;\nvTextureCoord = aTextureCoord.xy;\n}\n";
    protected static final String DEFAULT_FRAGMENT_SHADER = "precision mediump float;\nvarying highp vec2 vTextureCoord;\nuniform lowp sampler2D sTexture;\nvoid main() {\ngl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    private static final float[] VERTICES_DATA = new float[]{-1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, 0.0F};
    private static final int FLOAT_SIZE_BYTES = 4;
    protected static final int VERTICES_DATA_POS_SIZE = 3;
    protected static final int VERTICES_DATA_UV_SIZE = 2;
    protected static final int VERTICES_DATA_STRIDE_BYTES = 20;
    protected static final int VERTICES_DATA_POS_OFFSET = 0;
    protected static final int VERTICES_DATA_UV_OFFSET = 12;
    private final String vertexShaderSource;
    private final String fragmentShaderSource;
    private int program;
    private int vertexShader;
    private int fragmentShader;
    private int vertexBufferName;
    private final HashMap<String, Integer> handleMap;

    public GlFilter() {
        this("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvoid main() {\ngl_Position = aPosition;\nvTextureCoord = aTextureCoord.xy;\n}\n", "precision mediump float;\nvarying highp vec2 vTextureCoord;\nuniform lowp sampler2D sTexture;\nvoid main() {\ngl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
    }

    public GlFilter(Resources res, int vertexShaderSourceResId, int fragmentShaderSourceResId) {
        this(res.getString(vertexShaderSourceResId), res.getString(fragmentShaderSourceResId));
    }

    public GlFilter(String vertexShaderSource, String fragmentShaderSource) {
        this.handleMap = new HashMap();
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
    }

    public void setup() {
        this.release();
        this.vertexShader = EglUtil.loadShader(this.vertexShaderSource, 35633);
        this.fragmentShader = EglUtil.loadShader(this.fragmentShaderSource, 35632);
        this.program = EglUtil.createProgram(this.vertexShader, this.fragmentShader);
        this.vertexBufferName = EglUtil.createBuffer(VERTICES_DATA);
    }

    public void setFrameSize(int width, int height) {
    }

    public void release() {
        GLES20.glDeleteProgram(this.program);
        this.program = 0;
        GLES20.glDeleteShader(this.vertexShader);
        this.vertexShader = 0;
        GLES20.glDeleteShader(this.fragmentShader);
        this.fragmentShader = 0;
        GLES20.glDeleteBuffers(1, new int[]{this.vertexBufferName}, 0);
        this.vertexBufferName = 0;
        this.handleMap.clear();
    }

    public void draw(int texName, EFramebufferObject fbo) {
        this.useProgram();
        GLES20.glBindBuffer(34962, this.vertexBufferName);
        GLES20.glEnableVertexAttribArray(this.getHandle("aPosition"));
        GLES20.glVertexAttribPointer(this.getHandle("aPosition"), 3, 5126, false, 20, 0);
        GLES20.glEnableVertexAttribArray(this.getHandle("aTextureCoord"));
        GLES20.glVertexAttribPointer(this.getHandle("aTextureCoord"), 2, 5126, false, 20, 12);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, texName);
        GLES20.glUniform1i(this.getHandle("sTexture"), 0);
        this.onDraw();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.getHandle("aPosition"));
        GLES20.glDisableVertexAttribArray(this.getHandle("aTextureCoord"));
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindBuffer(34962, 0);
    }

    protected void onDraw() {
    }

    protected final void useProgram() {
        GLES20.glUseProgram(this.program);
    }

    protected final int getVertexBufferName() {
        return this.vertexBufferName;
    }

    protected final int getHandle(String name) {
        Integer value = (Integer)this.handleMap.get(name);
        if (value != null) {
            return value;
        } else {
            int location = GLES20.glGetAttribLocation(this.program, name);
            if (location == -1) {
                location = GLES20.glGetUniformLocation(this.program, name);
            }

            if (location == -1) {
                throw new IllegalStateException("Could not get attrib or uniform location for " + name);
            } else {
                this.handleMap.put(name, location);
                return location;
            }
        }
    }
}