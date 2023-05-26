package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter;

import android.opengl.GLES20;

/**
 * 
 * date:2022/8/8-17:00
 * des:
 */
public class GlPreviewFilter extends GlFilter {
    public static final int GL_TEXTURE_EXTERNAL_OES = 36197;
    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nuniform float uCRatio;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvoid main() {\nvec4 scaledPos = aPosition;\nscaledPos.x = scaledPos.x * uCRatio;\ngl_Position = uMVPMatrix * scaledPos;\nvTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
    private final int texTarget;

    public GlPreviewFilter(int texTarget) {
        super("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nuniform float uCRatio;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvoid main() {\nvec4 scaledPos = aPosition;\nscaledPos.x = scaledPos.x * uCRatio;\ngl_Position = uMVPMatrix * scaledPos;\nvTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", createFragmentShaderSourceOESIfNeed(texTarget));
        this.texTarget = texTarget;
    }

    private static String createFragmentShaderSourceOESIfNeed(int texTarget) {
        return texTarget == 36197 ? "#extension GL_OES_EGL_image_external : require\n" + "precision mediump float;\nvarying highp vec2 vTextureCoord;\nuniform lowp sampler2D sTexture;\nvoid main() {\ngl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n".replace("sampler2D", "samplerExternalOES") : "precision mediump float;\nvarying highp vec2 vTextureCoord;\nuniform lowp sampler2D sTexture;\nvoid main() {\ngl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    }

    public void draw(int texName, float[] mvpMatrix, float[] stMatrix, float aspectRatio) {
        this.useProgram();
        GLES20.glUniformMatrix4fv(this.getHandle("uMVPMatrix"), 1, false, mvpMatrix, 0);
        GLES20.glUniformMatrix4fv(this.getHandle("uSTMatrix"), 1, false, stMatrix, 0);
        GLES20.glUniform1f(this.getHandle("uCRatio"), aspectRatio);
        GLES20.glBindBuffer(34962, this.getVertexBufferName());
        GLES20.glEnableVertexAttribArray(this.getHandle("aPosition"));
        GLES20.glVertexAttribPointer(this.getHandle("aPosition"), 3, 5126, false, 20, 0);
        GLES20.glEnableVertexAttribArray(this.getHandle("aTextureCoord"));
        GLES20.glVertexAttribPointer(this.getHandle("aTextureCoord"), 2, 5126, false, 20, 12);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(this.texTarget, texName);
        GLES20.glUniform1i(this.getHandle("sTexture"), 0);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.getHandle("aPosition"));
        GLES20.glDisableVertexAttribArray(this.getHandle("aTextureCoord"));
        GLES20.glBindBuffer(34962, 0);
        GLES20.glBindTexture(3553, 0);
    }
}
