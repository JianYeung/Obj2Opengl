package com.tcl.obj2opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView mGLView;
    private List<ObjRender> filters;
    private MyGLRender render;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        mGLView= (GLSurfaceView) findViewById(R.id.glsurfaceview);
        mGLView.setEGLContextClientVersion(2);
        List<Obj3D> model=ObjReader.readMultiObj(this,"assets/3dres/pikachu.obj");
        filters=new ArrayList<>();
        for (int i=0;i<model.size();i++){
            ObjRender f=new ObjRender(getResources());
            f.setObj3D(model.get(i));
            filters.add(f);
        }

       render = new MyGLRender();
        render.setRenders(filters);
        mGLView.setRenderer(render);
       /* mGLView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                for (ObjRender f:filters){
                    f.create();
                }
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
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
                GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                for (ObjRender f:filters){
                    Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
                    f.draw();
                }
            }
        });*/
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }
}
