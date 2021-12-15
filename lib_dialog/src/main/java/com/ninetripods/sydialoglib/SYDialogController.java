package com.ninetripods.sydialoglib;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.fastgo.driver.dialog.sydialoglib.R;
import com.ninetripods.sydialoglib.manager.ScreenUtil;

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

    private void dealDefaultDialog(IDialog.OnClickListener positiveBtnListener, IDialog.OnClickListener negativeBtnListener, final String titleStr, final String contentStr,
                                   boolean showBtnLeft, String negativeStr, boolean showBtnRight, String positiveStr) {
        if (dialogView == null) return;
        this.mNegativeButtonListener = negativeBtnListener;
        this.mPositiveButtonListener = positiveBtnListener;
        btn_ok = (Button) dialogView.findViewById(R.id.btn_ok);
        btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        if (showBtnRight && showBtnLeft) {
            //左右两个按钮都存在
            if (btn_ok != null) {
                btn_ok.setVisibility(View.VISIBLE);
                btn_ok.setText(Html.fromHtml(TextUtils.isEmpty(positiveStr) ? "确定" : positiveStr));
                btn_ok.setOnClickListener(mButtonHandler);
            }
            if (btn_cancel != null) {
                btn_cancel.setVisibility(View.VISIBLE);
                btn_cancel.setText(Html.fromHtml(TextUtils.isEmpty(negativeStr) ? "取消" : negativeStr));
                btn_cancel.setOnClickListener(mButtonHandler);
            }
        } else if (showBtnRight) {
            //只有右边的按钮
            if (btn_ok != null) {
                btn_ok.setVisibility(View.VISIBLE);
                btn_ok.setBackgroundResource(R.drawable.lib_ui_selector_btn_border_bg);
                btn_ok.setText(Html.fromHtml(TextUtils.isEmpty(positiveStr) ? "确定" : positiveStr));
                btn_ok.setOnClickListener(mButtonHandler);
            }
        } else if (showBtnLeft) {
            //只有左边的按钮
            if (btn_cancel != null) {
                btn_cancel.setVisibility(View.VISIBLE);
                btn_cancel.setBackgroundResource(R.drawable.lib_ui_selector_btn_border_bg);
                btn_cancel.setText(Html.fromHtml(TextUtils.isEmpty(negativeStr) ? "取消" : negativeStr));
                btn_cancel.setOnClickListener(mButtonHandler);
            }
        }

        TextView tv_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        final TextView tv_content = (TextView) dialogView.findViewById(R.id.dialog_content);

        if (tv_title != null) {
            tv_title.setVisibility(TextUtils.isEmpty(titleStr) ? View.GONE : View.VISIBLE);
            tv_title.setText(Html.fromHtml(!TextUtils.isEmpty(titleStr) ? titleStr : "Title"));
            if (TextUtils.isEmpty(contentStr) && mDialog.get() != null && mDialog.get().getContext() != null) {
                tv_title.setMinHeight(ScreenUtil.dp2px(mDialog.get().getContext(), 100));
                tv_title.setGravity(Gravity.CENTER);
                tv_title.setPadding(0, 10, 0, 0);
            }
        }
        if (tv_content != null) {
            tv_content.setVisibility(TextUtils.isEmpty(contentStr) ? View.GONE : View.VISIBLE);
            tv_content.setText(contentStr);
            tv_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {

                    int lineCount = tv_content.getLineCount();
                    if (lineCount >= 3) {
                        //超过三行居左显示
                        tv_content.setGravity(Gravity.START);
                    } else {
                        //默认居中
                        tv_content.setGravity(Gravity.CENTER_HORIZONTAL);
                        if (TextUtils.isEmpty(titleStr)) {
                            tv_content.setPadding(0, 50, 0, 50);
                        }
                    }

                    if (TextUtils.isEmpty(titleStr)) {
                        //没有title，只有content
                        tv_content.setTextSize(18);
                        if (mDialog.get() == null || mDialog.get().getContext() == null || mDialog.get().getContext().getResources() == null)
                            return true;
                        tv_content.setTextColor(mDialog.get().getContext().getResources().getColor(R.color.c333333));
                    }
                    return true;
                }
            });

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
        float dimAmount = 0.4f;
        public int gravity = Gravity.CENTER;
        boolean isCancelableOutside = true;
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
        int animRes = R.style.translate_style;//Dialog动画style

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
