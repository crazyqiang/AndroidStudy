package com.fastgo.sydialoglib;

import android.view.View;

/**
 * Created by mq on 2018/9/2 下午6:01
 * mqcoder90@gmail.com
 */

public interface IDialog {

    void dismiss();

    interface OnBuildListener {
        void onBuildChildView(IDialog dialog, View view, int layoutRes);
    }

    interface OnClickListener {
        void onClick(IDialog dialog);
    }
}
