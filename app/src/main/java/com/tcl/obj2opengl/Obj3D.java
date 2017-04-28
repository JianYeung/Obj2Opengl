package com.tcl.obj2opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * 项目名：   Obj2Opengl
 * 包名：     com.tcl.obj2opengl
 * 文件名：   Obj3D
 * 创建者：   root
 * 创建时间： 17-4-27 下午3:09
 * 描述：     TODO
 */

public class Obj3D {

    private static final String TAG = Obj3D.class.getSimpleName();

    public int vertCount;
    public FloatBuffer vert;
    public FloatBuffer vertNormals;
    public FloatBuffer vertTextureCoords;

    public MtlInfo mtl;

    private ArrayList<Float> tempVert;
    private ArrayList<Float> tempVertNorl;
    public ArrayList<Float> tempVertTexture;

    public int textureSMode;
    public int textureTMode;

    public void addVert(float d){
        tLog.i(TAG,"addVert()");
        if(tempVert==null){
            tempVert=new ArrayList<>();
        }
        tempVert.add(d);
    }

    public void addVertTexture(float d){
        tLog.i(TAG,"addVertTexture()");
        if(tempVertTexture==null){
            tempVertTexture=new ArrayList<>();
        }
        tempVertTexture.add(d);
    }

    public void addVertNorl(float d){
        tLog.i(TAG,"addVertNorl()");
        if(tempVertNorl==null){
            tempVertNorl=new ArrayList<>();
        }
        tempVertNorl.add(d);
    }

    public void dataLock(){
        tLog.i(TAG,"dataLock()");
        if(tempVert!=null){
            setVert(tempVert);
            tempVert.clear();
            tempVert=null;
        }
        if(tempVertTexture!=null){
            setVertTexture(tempVertTexture);
            tempVertTexture.clear();
            tempVertTexture=null;
        }
        if(tempVertNorl!=null){
            setVertNorl(tempVertNorl);
            tempVertNorl.clear();
            tempVertNorl=null;
        }
    }

    public void setVert(ArrayList<Float> data){
        tLog.i(TAG,"setVert()");
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vert=buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vert.put(data.get(i));
        }
        vert.position(0);
        vertCount=size/3;
    }

    public void setVertNorl(ArrayList<Float> data){
        tLog.i(TAG,"setVertNorl()");
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertNormals=buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vertNormals.put(data.get(i));
        }
        vertNormals.position(0);
    }

    public void setVertTexture(ArrayList<Float> data){
        tLog.i(TAG,"setVertTexture()");
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertTextureCoords=buffer.asFloatBuffer();
        for (int i=0;i<size;){
            vertTextureCoords.put(data.get(i));
            i++;
            vertTextureCoords.put(data.get(i));
            i++;
        }
        vertTextureCoords.position(0);
    }

}
