package com.tcl.obj2opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 项目名：   Obj2Opengl
 * 包名：     com.tcl.obj2opengl
 * 文件名：   MyGLRender
 * 创建者：   root
 * 创建时间： 17-4-27 下午3:03
 * 描述：     TODO
 */

public class MyGLRender implements GLSurfaceView.Renderer {

    private static final String TAG = MyGLRender.class.getSimpleName();

    private List<ObjRender> renders;

    public void setRenders(List<ObjRender> renders) {
        this.renders = renders;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        tLog.i(TAG,"onSurfaceCreated()");
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1);
        //开启深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        renders.get(0).create();
       /* for (ObjRender r:renders){
            r.create();
        }*/
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        tLog.i(TAG,"onSurfaceChanged()");
        renders.get(0).setSize(width,height);
        /*for (ObjRender r:renders){
            r.setSize(width,height);
        }*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        tLog.i(TAG,"onDrawFrame()");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        renders.get(0).draw();
        /*for (ObjRender r:renders){
            r.draw();
        }*/
    }
}
