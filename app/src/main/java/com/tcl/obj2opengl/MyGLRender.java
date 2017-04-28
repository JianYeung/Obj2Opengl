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
 * 描述：     渲染类
 */

public class MyGLRender implements GLSurfaceView.Renderer {

    private static final String TAG = MyGLRender.class.getSimpleName();

    private List<ObjRender> filters;


    public void setRenders(List<ObjRender> filters) {
        tLog.i(TAG,"setRenders()");
        this.filters = filters;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        tLog.i(TAG,"onSurfaceCreated()");
        for (ObjRender f:filters){
            f.create();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        tLog.i(TAG,"onSurfaceChanged()");
        for (ObjRender f:filters){
            f.onSizeChanged(width, height);
            float[] matrix= {
                    1,0,0,0,
                    0,1,0,0,
                    0,0,1,0,
                    0,0,0,1
            };
            Matrix.translateM(matrix,0,0,-0.3f,0);
            Matrix.scaleM(matrix,0,0.008f,0.008f*width/height,0.008f);
            f.setMatrix(matrix);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        tLog.i(TAG,"onDrawFrame()");
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (ObjRender f:filters){
            Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
            f.draw();
        }
    }
}
