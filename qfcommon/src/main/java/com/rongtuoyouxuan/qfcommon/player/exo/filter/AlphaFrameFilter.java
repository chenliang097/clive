package com.rongtuoyouxuan.qfcommon.player.exo.filter;

/**
 * 
 * date:2022/8/8-16:59
 * des:
 */
public class AlphaFrameFilter extends GlFilter {
    private static final String VERTEX_SHADER = "attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord2;\nvoid main() {\ngl_Position = aPosition;\nvTextureCoord = vec2(aTextureCoord.x*0.5+0.5, aTextureCoord.y);\nvTextureCoord2 = vec2(aTextureCoord.x*0.5, aTextureCoord.y);\n}\n";
    private static final String FRAGMENT_SHADER = "precision mediump float;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord2;\nuniform lowp sampler2D sTexture;\nvoid main() {\nvec4 color1 = texture2D(sTexture, vTextureCoord);\nvec4 color2 = texture2D(sTexture, vTextureCoord2);\ngl_FragColor = vec4(color1.rgb, color2.r);\n}\n";

    public AlphaFrameFilter() {
        super("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord2;\nvoid main() {\ngl_Position = aPosition;\nvTextureCoord = vec2(aTextureCoord.x*0.5+0.5, aTextureCoord.y);\nvTextureCoord2 = vec2(aTextureCoord.x*0.5, aTextureCoord.y);\n}\n", "precision mediump float;\nvarying highp vec2 vTextureCoord;\nvarying highp vec2 vTextureCoord2;\nuniform lowp sampler2D sTexture;\nvoid main() {\nvec4 color1 = texture2D(sTexture, vTextureCoord);\nvec4 color2 = texture2D(sTexture, vTextureCoord2);\ngl_FragColor = vec4(color1.rgb, color2.r);\n}\n");
    }
}
