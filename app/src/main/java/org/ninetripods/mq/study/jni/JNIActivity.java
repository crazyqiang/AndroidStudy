package org.ninetripods.mq.study.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ninetripods.mq.study.R;

public class JNIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
    }
    static {
        //加载动态库.so文件，注意不用写lib前缀，系统会默认添加
        System.loadLibrary("native-lib");
    }

//    public static native void cryptFiles(String src,String dest);

//    public static native void cryptFile(String src, String dest);

//    public static native void decryptFile(String src, String dest);
}
