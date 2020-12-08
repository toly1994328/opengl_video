package com.toly1994.opengl_video.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VideoRender implements
        GLSurfaceView.Renderer, // OpenGL 渲染回调
        SurfaceTexture.OnFrameAvailableListener,
        MediaPlayer.OnVideoSizeChangedListener
{

    private final Context context; // 上下文
    private final File video; // 视频文件

    private MediaPlayer mediaPlayer; // 视频播放器

//    private VideoDrawer videoDrawer;
    private VideoDrawerPlus videoDrawer;

    private SurfaceTexture surfaceTexture; // 表面纹理

    private volatile boolean updateSurface; // 是否更新表面纹理

    private int textureId; // 纹理 id

    private int viewWidth, viewHeight, videoWidth, videoHeight;

    public VideoRender(Context context, File video) {
        this.context = context;
        this.video = video;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e("VideoRender", "onSurfaceCreated: " + Thread.currentThread().getName());
//        videoDrawer = new VideoDrawer(context);
        videoDrawer = new VideoDrawerPlus(context);
        initMediaPlayer();

        mediaPlayer.seekTo(1000 * 40);
        mediaPlayer.start();
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.e("VideoRender",
                "线程名: " + Thread.currentThread().getName() +
                        "-----onSurfaceChanged: (" + width + " , " + height + ")"
        );

        viewWidth = width;
        viewHeight = height;

        updateProjection();
        GLES30.glViewport(0, 0, viewWidth, viewHeight);
    }

    private float[] sTMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("VideoRender", "onDrawFrame: "+Thread.currentThread().getName());
        synchronized (this) {
            if (updateSurface) {
                surfaceTexture.updateTexImage();
                surfaceTexture.getTransformMatrix(sTMatrix);
                updateSurface = false;
            }
        }
        videoDrawer.draw(textureId, projectionMatrix, sTMatrix);
    }


    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, Uri.fromFile(video));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnVideoSizeChangedListener(this);

        int[] textures = new int[1];
        GLES30.glGenTextures(1, textures, 0);
        textureId = textures[0];
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);

        surfaceTexture = new SurfaceTexture(textureId);
        surfaceTexture.setOnFrameAvailableListener(this);

        Surface surface = new Surface(surfaceTexture);
        mediaPlayer.setSurface(surface);
        surface.release();

        try {
            mediaPlayer.prepare();
        } catch (IOException t) {
            Log.e("Prepare ERROR", "onSurfaceCreated: ");
        }
    }


    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        Log.e("VideoRender", "onFrameAvailable: "+Thread.currentThread().getName());
        updateSurface = true;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.e("VideoRender",
                "线程名: " + Thread.currentThread().getName() +
                        "-----onVideoSizeChanged: (" + width + " , " + height + ")"
        );
        videoWidth = width;
        videoHeight = height;
    }

    private void updateProjection() {
        float viewRatio = (float) viewWidth / viewHeight;
        float videoRatio = (float) videoWidth / videoHeight;
        //正交投影矩阵
        Matrix.orthoM(projectionMatrix, 0,
                - 1, 1, -1, 1,
                -1f, 1f);
    }
}
