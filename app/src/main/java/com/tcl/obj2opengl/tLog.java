package com.tcl.obj2opengl;

import android.util.Log;

/**
 * 项目名：   Obj2Opengl
 * 包名：     com.tcl.obj2opengl
 * 文件名：   tLog
 * 创建者：   root
 * 创建时间： 17-4-27 下午3:48
 * 描述：     TODO
 */

public class tLog {
    private static boolean isDebug = true;
    public static void setDebug(boolean debug){
        if (isDebug == debug){
            return;
        }
        isDebug = debug;
    }
    public static final void v(String tag, String msg){
        if (isDebug){
            Log.v(tag,msg);
        }
    }
    public static final void i(String tag, String msg){
        if (isDebug){
            Log.i(tag,msg);
        }
    }
    public static final void d(String tag, String msg){
        if (isDebug){
            Log.d(tag,msg);
        }
    }
    public static final void e(String tag, String msg){
        if (isDebug){
            Log.e(tag,msg);
        }
    }

}
