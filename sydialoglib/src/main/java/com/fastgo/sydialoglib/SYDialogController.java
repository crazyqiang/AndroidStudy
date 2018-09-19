package com.fastgo.sydialoglib;

import android.app.FragmentManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fastgo.driver.dialog.sydialoglib.R;

import java.lang.ref.WeakReference;

/**
 * Created by mq on 2018/9/1 上午10:58
 * mqcoder90@gmail.com
 */

public class SYDialogController {

    private int layoutRes;
    private int dialogWidth;
    private int dialogHeight;
    private float dimAmount = 0.2f;
    private int gravity = Gravity.CENTER;
    private boolean isCancelableOutside = true;
    private boolean cancelable;
    private int animRes;
    private View dialogView;
    private IDialog.OnClickListener mPositiveButtonListener;
    private IDialog.OnClickListener mNegativeButtonListener;
    private WeakReference<IDialog> mDialog;
    private String titleStr;//默认标题
    private String contentStr;//默认内容
    private String positiveStr;//右边按钮文字
    private String negativeStr;//左边按钮文字
    private boolean showBtnLeft, showBtnRight;


    private Button btn_ok, btn_cancel;

    SYDialogController(IDialog dialog) {
        mDialog = new WeakReference<>(dialog);
    }

    int getAnimRes() {
        return animRes;
    }

    int getLayoutRes() {
        return layoutRes;
    }

    void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    int getDialogWidth() {
        return dialogWidth;
    }

    int getDialogHeight() {
        return dialogHeight;
    }

    float getDimAmount() {
        return dimAmount;
    }

    public int getGravity() {
        return gravity;
    }

    boolean isCancelableOutside() {
        return isCancelableOutside;
    }

    boolean isCancelable() {
        return cancelable;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    View getDialogView() {
        return dialogView;
    }

    public void setChildView(View view) {
        setDialogView(view);
        dealDefaultDialog(mPositiveButtonListener, mNegativeButtonListener, titleStr,
                contentStr, showBtnLeft, negativeStr, showBtnRight, positiveStr);
    }

    void dealDefaultDialog(IDialog.OnClickListener positiveBtnListener, IDialog.OnClickListener negativeBtnListener, String titleStr, String contentStr,
                           boolean showBtnLeft, String negativeStr, boolean showBtnRight, String positiveStr) {
        if (dialogView == null) return;
        this.mNegativeButtonListener = negativeBtnListener;
        this.mPositiveButtonListener = positiveBtnListener;
        btn_ok = (Button) dialogView.findViewById(R.id.btn_ok);
        btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        if (btn_ok != null && !TextUtils.isEmpty(positiveStr)) {
            btn_ok.setVisibility(showBtnRight ? View.VISIBLE : View.GONE);
            btn_ok.setText(positiveStr);
            btn_ok.setOnClickListener(mButtonHandler);
        }
        if (btn_cancel != null) {
            btn_cancel.setVisibility(showBtnLeft ? View.VISIBLE : View.GONE);
            btn_cancel.setText(negativeStr);
            btn_cancel.setOnClickListener(mButtonHandler);
        }
        TextView tv_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        TextView tv_content = (TextView) dialogView.findViewById(R.id.dialog_content);
        if (tv_title != null) {
            tv_title.setVisibility(TextUtils.isEmpty(titleStr) ? View.GONE : View.VISIBLE);
            tv_title.setText(titleStr);
        }
        if (tv_content != null) {
            tv_content.setVisibility(TextUtils.isEmpty(contentStr) ? View.GONE : View.VISIBLE);
            tv_content.setText(contentStr);
        }

    }

    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == btn_cancel) {
                if (mDialog.get() == null) return;
                if (mNegativeButtonListener != null) {
                    mNegativeButtonListener.onClick(mDialog.get());
                }
            } else if (view == btn_ok) {
                if (mDialog.get() == null) return;
                if (mPositiveButtonListener != null) {
                    mPositiveButtonListener.onClick(mDialog.get());
                }
            }
        }
    };

    public static class SYParams {
        FragmentManager fragmentManager;
        int layoutRes;
        int dialogWidth;
        int dialogHeight;
        float dimAmount = 0.2f;
        public int gravity = Gravity.CENTER;
        boolean isCancelableOutside = false;
        boolean cancelable = false;
        View dialogView;
        Context context;
        IDialog.OnClickListener positiveBtnListener;
        IDialog.OnClickListener negativeBtnListener;
        String titleStr;//默认标题
        String contentStr;//默认内容
        String positiveStr;//右边按钮文字
        String negativeStr;//左边按钮文字
        boolean showBtnLeft, showBtnRight;
        int animRes;//Dialog动画style

        void apply(SYDialogController controller) {
            controller.dimAmount = dimAmount;
            controller.gravity = gravity;
            controller.isCancelableOutside = isCancelableOutside;
            controller.cancelable = cancelable;
            controller.animRes = animRes;
            controller.titleStr = titleStr;
            controller.contentStr = contentStr;
            controller.positiveStr = positiveStr;
            controller.negativeStr = negativeStr;
            controller.showBtnLeft = showBtnLeft;
            controller.showBtnRight = showBtnRight;
            controller.mPositiveButtonListener = positiveBtnListener;
            controller.mNegativeButtonListener = negativeBtnListener;
            if (layoutRes > 0) {
                controller.setLayoutRes(layoutRes);
            } else if (dialogView != null) {
                controller.dialogView = dialogView;
            } else {
                throw new IllegalArgumentException("Dialog View can't be null");
            }
            if (dialogWidth > 0) {
                controller.dialogWidth = dialogWidth;
            }
            if (dialogHeight > 0) {
                controller.dialogHeight = dialogHeight;
            }
        }

    }

}
