package com.toly1994.opengl_video.utils;

import android.content.Context;
import android.opengl.GLES30;
import android.util.Log;

import java.io.InputStream;

public class LoadUtils {

    public static int initProgram(Context ctx, String vName, String fName) {
        String vsh = loadStringFromAssets(ctx, vName);
        String fsh = loadStringFromAssets(ctx, fName);
        return initProgram(vsh, fsh);
    }

    //从 assets 文件中读取资源字符串
    private static String loadStringFromAssets(Context ctx, String name) {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len;

        try (InputStream is = ctx.getAssets().open(name)) {
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    public static int initProgram(String vsh, String fsh) {
        int program = -1;
        // 加载 [顶点着色器] 和 [片段着色器]
        int vShader = loadShader(GLES30.GL_VERTEX_SHADER, vsh);
        int fShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fsh);

        // 创建 program
        program = GLES30.glCreateProgram();
        if (program == 0) {
            return -1;
        }

        // 将 [顶点着色器] 和 [片段着色器] 关联到 program 上
        GLES30.glAttachShader(program, vShader);
        GLES30.glAttachShader(program, fShader);

        // 链接 program
        GLES30.glLinkProgram(program);

        // 检查链接状态
        int[] linkedState = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkedState, 0);

        if (linkedState[0] == 0) {
            Log.e("ES30_ERROR", "链接 program错误: " + GLES30.glGetProgramInfoLog(program));
            GLES30.glDeleteProgram(program);
            return -1;
        }
        return program;
    }

    private static int loadShader(int type, String src) {
        //创建着色器
        int shader = GLES30.glCreateShader(type);
        //加载失败直接返回
        if (shader == 0) {
            return 0;
        }
        //加载着色器源代码
        GLES30.glShaderSource(shader, src);
        //编译着色器
        GLES30.glCompileShader(shader);

        //存放编译成功 shader 数量的数组
        int[] compiled = new int[1];
        //获取Shader的编译情况
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        //若编译失败则显示错误日志并删除此shader
        if (compiled[0] == 0) {
            Log.e("ES30_COMPILE_ERROR",
                    "无法编译着色器: " + GLES30.glGetShaderInfoLog(shader));
            GLES30.glDeleteShader(shader);
            shader = 0;
        }
        return shader;
    }
}
