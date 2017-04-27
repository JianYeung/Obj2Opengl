package com.tcl.obj2opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView glSurfaceView;
    private MyGLRender glrender;
    private List<ObjRender> renders;

    float[] matrix= {
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        tLog.i(TAG,"onCreate()");
        glSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        //glrender = new MyGLRender();
        glSurfaceView.setEGLContextClientVersion(2);

        List<Obj3D> model=ObjReader.readMultiObj(this,"assets/3dres/pikachu.obj");
        renders = new ArrayList<>();
        for (int i=0;i<model.size();i++){
            ObjRender r=new ObjRender(getResources());
            r.setObj3D(model.get(i));
            renders.add(r);
        }
/*
        tLog.i(TAG,"Obj num = "+ renders.size());

        glrender.setRenders(renders);*/
        glSurfaceView.setRenderer(new GLSurfaceView.Renderer(){

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                tLog.i(TAG,"onSurfaceCreated()");
                GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                for (ObjRender r:renders){
                    tLog.i(TAG,"objrender = "+r+" create()");
                    r.create();
                }
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                tLog.i(TAG,"onSurfaceChanged()");
                for (ObjRender r:renders){
                    tLog.i(TAG,"objrender = "+r+" setSize()");
                    r.setSize(width,height);
                   /* Matrix.translateM(matrix,0,0,-0.3f,0);
                    Matrix.scaleM(matrix,0,0.008f,0.008f*width/height,0.008f);
                    r.setMatrix(matrix);*/
                }
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                tLog.i(TAG,"onDrawFrame()");
                for (ObjRender r:renders){
                    tLog.i(TAG,"objrender = "+r+" draw()");
                    Matrix.rotateM(matrix,0,0.3f,0,1,0);
                    r.draw();
                }
            }
        });
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    @Override
    protected void onResume() {
        tLog.i(TAG,"onResume()");
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        tLog.i(TAG,"onPause()");
        super.onPause();
        glSurfaceView.onPause();
    }
}
