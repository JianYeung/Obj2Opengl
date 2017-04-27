package com.tcl.obj2opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView glSurfaceView;
    private MyGLRender glrender;
    private List<ObjRender> renders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tLog.i(TAG,"onCreate()");
        glSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        glrender = new MyGLRender();
        glSurfaceView.setEGLContextClientVersion(2);

        List<Obj3D> model=ObjReader.readMultiObj(this,"assets/3dres/pikachu.obj");
        renders = new ArrayList<ObjRender>();
        for (int i=0;i<model.size();i++){
            ObjRender r=new ObjRender(getResources());
            r.setObj3D(model.get(i));
            renders.add(r);
        }

        tLog.i(TAG,"Obj num = "+ renders.size());

        glrender.setRenders(renders);
        glSurfaceView.setRenderer(glrender);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
