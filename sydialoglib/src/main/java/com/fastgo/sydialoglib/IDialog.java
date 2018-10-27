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

    interface OnDismissListener {
        /**
         * This method will be invoked when the dialog is dismissed.
         *
         * @param dialog the dialog that was dismissed will be passed into the
         *               method
         */
        void onDismiss(IDialog dialog);
    }
}
