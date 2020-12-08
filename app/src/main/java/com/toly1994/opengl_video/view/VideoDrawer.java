package com.toly1994.opengl_video.view;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;


import com.toly1994.opengl_video.utils.BufferUtils;
import com.toly1994.opengl_video.utils.LoadUtils;

import java.nio.FloatBuffer;

public class VideoDrawer {

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureVertexBuffer;

    private final float[] vertexData = {
            1f, -1f, 0f,
            -1f, -1f, 0f,
            1f, 1f, 0f,
            -1f, 1f, 0f
    };

    private final float[] textureVertexData = {
            1f, 0f,
            0f, 0f,
            1f, 1f,
            0f, 1f
    };


    private final int aPositionLocation = 0;
    private final int aTextureCoordLocation = 1;
    private final int uMatrixLocation = 2;
    private final int uSTMMatrixLocation = 3;
    private final int uSTextureLocation = 4;

    private int programId;

    public VideoDrawer(Context context) {
        vertexBuffer = BufferUtils.getFloatBuffer(vertexData);
        textureVertexBuffer = BufferUtils.getFloatBuffer(textureVertexData);
//        programId = LoadUtils.initProgram(context, "video.vsh", "red_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "blue_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "negative_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "grey_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "nostalgic_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "year_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "mirror_video.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "fenjing.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "splite.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "mask_rect.fsh");
//        programId = LoadUtils.initProgram(context, "video.vsh", "ball_mask.fsh");
        programId = LoadUtils.initProgram(context, "video.vsh", "video_mask.fsh");
    }

    public void draw(int textureId, float[] projectionMatrix, float[] sTMatrix) {
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(programId);
        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        GLES30.glUniformMatrix4fv(uSTMMatrixLocation, 1, false, sTMatrix, 0);

        GLES30.glEnableVertexAttribArray(aPositionLocation);
        GLES30.glVertexAttribPointer(aPositionLocation, 3, GLES30.GL_FLOAT, false, 12, vertexBuffer);

        GLES30.glEnableVertexAttribArray(aTextureCoordLocation);
        GLES30.glVertexAttribPointer(aTextureCoordLocation, 2, GLES30.GL_FLOAT, false, 8, textureVertexBuffer);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        GLES30.glUniform1i(uSTextureLocation, 0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
    }
}
