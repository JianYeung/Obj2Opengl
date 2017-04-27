package com.tcl.obj2opengl;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.io.IOException;

/**
 * 项目名：   Obj2Opengl
 * 包名：     com.tcl.obj2opengl
 * 文件名：   ObjRender
 * 创建者：   root
 * 创建时间： 17-4-27 下午4:07
 * 描述：     TODO
 */

public class ObjRender extends BasicRender {

    private static final String TAG = ObjRender.class.getSimpleName();

    private Obj3D obj;
    private int mHPosition;
    private int mHCoord;
    private int mHNormal;
    private int mHTexture;
    private int textureId = 1;
    private int mHKa;
    private int mHKd;
    private int mHKs;
    private int mHMatrix;

    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    public ObjRender(Resources mRes) {
        super(mRes);
        tLog.i(TAG,"Res: "+ mRes);
    }

    @Override
    protected void create() {
        tLog.i(TAG,"create()");
        super.create();
    }

    @Override
    protected void setSize(int width, int height) {
        tLog.i(TAG,"setSize()");
        super.setSize(width, height);
    }

    public void setObj3D(Obj3D obj){
        tLog.i(TAG,"obj = "+obj);
        this.obj=obj;
    }

    @Override
    protected void onCreate() {
        tLog.i(TAG,"onCreate()");
        createProgramByAssetsFile("3dres/obj2.vert","3dres/obj2.frag");
        mHPosition= GLES20.glGetAttribLocation(mProgram, "vPosition");
        mHNormal= GLES20.glGetAttribLocation(mProgram,"vNormal");
        mHTexture=GLES20.glGetUniformLocation(mProgram,"vTexture");
        mHCoord=GLES20.glGetAttribLocation(mProgram,"vCoord");
        mHKa=GLES20.glGetUniformLocation(mProgram,"vKa");
        mHKd=GLES20.glGetUniformLocation(mProgram,"vKd");
        mHKs=GLES20.glGetUniformLocation(mProgram,"vKs");
        mHMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");

        if(obj!=null&&obj.mtl!=null){
            try {
                tLog.i(TAG,"texture-->"+"3dres/"+obj.mtl.map_Kd);
                textureId=createTexture(BitmapFactory.decodeStream(mRes.getAssets().open("3dres/"+obj.mtl.map_Kd)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSizeChanged(int width, int height) {
        tLog.i(TAG,"onSizeChanged()");
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        Matrix.setLookAtM(mViewMatrix, 0, 5.0f, 5.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    protected void draw() {
        tLog.i(TAG,"draw()");
        onClear();
        onUseProgram();
        onSetExpandData();
        onBindTexture();
        onDraw();
    }

    @Override
    protected void initBuffer() {
        tLog.i(TAG,"initBuffer()");
        super.initBuffer();
    }

    @Override
    protected void onClear() {
        tLog.i(TAG,"onClear()");
        super.onClear();
    }

    @Override
    protected void onUseProgram() {
        tLog.i(TAG,"onUseProgram()");
        super.onUseProgram();
    }

    @Override
    protected void onBindTexture() {
        tLog.i(TAG,"onBindTexture()");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0+textureId);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        GLES20.glUniform1i(mHTexture,textureId);
    }

    @Override
    protected void onSetExpandData() {
        tLog.i(TAG,"onSetExpandData()");
        GLES20.glUniformMatrix4fv(mHMatrix,1,false,mMVPMatrix,0);
        if(obj!=null&&obj.mtl!=null){
            GLES20.glUniform3fv(mHKa,1,obj.mtl.Ka,0);
            GLES20.glUniform3fv(mHKd,1,obj.mtl.Kd,0);
            GLES20.glUniform3fv(mHKs,1,obj.mtl.Ks,0);
        }
    }

    @Override
    protected void onDraw() {
        tLog.i(TAG,"onDraw()");
        GLES20.glEnableVertexAttribArray(mHPosition);
        GLES20.glVertexAttribPointer(mHPosition,3, GLES20.GL_FLOAT, false,0,obj.vert);
        GLES20.glEnableVertexAttribArray(mHNormal);
        GLES20.glVertexAttribPointer(mHNormal,3, GLES20.GL_FLOAT, false, 0,obj.vertNormals);
        GLES20.glEnableVertexAttribArray(mHCoord);
        GLES20.glVertexAttribPointer(mHCoord,2,GLES20.GL_FLOAT,false,0,obj.vertTextureCoords);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,obj.vertCount);
        GLES20.glDisableVertexAttribArray(mHNormal);
        GLES20.glDisableVertexAttribArray(mHCoord);
        GLES20.glDisableVertexAttribArray(mHPosition);
    }

}