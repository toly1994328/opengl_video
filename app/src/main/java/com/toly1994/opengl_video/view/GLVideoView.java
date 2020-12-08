package com.toly1994.opengl_video.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.io.File;

public class GLVideoView extends GLSurfaceView{

    VideoRender render;

    public GLVideoView(Context context) {
        super(context);
        String videoPath = "/data/data/com.toly1994.opengl_video/cache/sh.mp4";
        File video=  new File(videoPath);
        setEGLContextClientVersion(3);//设置OpenGL ES 3.0 context
        render = new VideoRender(getContext(),video);
        setRenderer(render);//设置渲染器
    }

}
