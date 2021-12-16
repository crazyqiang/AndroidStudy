package com.ninetripods.sydialoglib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ninetripods.sydialoglib.R;

/**
 * Dialog页面内Loading 包含Loading、Empty、Error三种
 * <p>
 * Created by mq on 2020/6/14 下午11:08
 * mqcoder90@gmail.com
 */
public class DialogLoadingView extends FrameLayout {
    @LayoutRes
    private int mLoadingLayoutId;
    @LayoutRes
    private int mEmptyLayoutId;
    @LayoutRes
    private int mErrorLayoutId;
    @ColorInt
    private int mBackgroundColorId;

    private ViewStub mVbLoading;
    private ViewStub mVbEmpty;
    private ViewStub mVbError;
    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private String mEmptyTextStr;
    private String mErrorTextStr;

    private OnRetryListener mRetryListener;

    public interface OnRetryListener {
        void onRetry();
    }

    public void setRetryListener(OnRetryListener retryListener) {
        this.mRetryListener = retryListener;
    }

    public DialogLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public DialogLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DialogLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DialogLoadingView);
        mLoadingLayoutId = ta.getResourceId(
                R.styleable.DialogLoadingView_loadingLayoutId, R.layout.lib_dialog_default_loading);
        mEmptyLayoutId = ta.getResourceId(
                R.styleable.DialogLoadingView_emptyLayoutId, R.layout.lib_dialog_default_empty);
        mErrorLayoutId = ta.getResourceId(
                R.styleable.DialogLoadingView_emptyLayoutId, R.layout.lib_dialog_default_error);
        mBackgroundColorId = ta.getColor(
                R.styleable.DialogLoadingView_backgroundColorId, getResources().getColor(R.color.white));
        mEmptyTextStr = ta.getString(R.styleable.DialogLoadingView_emptyTextStr);
        mErrorTextStr = ta.getString(R.styleable.DialogLoadingView_errorTextStr);
        ta.recycle();

        inflate(context, R.layout.lib_dialog_layout_loading_view, this);
        mVbLoading = findViewById(R.id.vb_loading);
        mVbEmpty = findViewById(R.id.vb_empty);
        mVbError = findViewById(R.id.vb_error);

        setBackgroundColor(mBackgroundColorId);
        showLoading();
    }

    /**
     * 加载中
     */
    public void showLoading() {
        if (mLoadingView == null) {
            mVbLoading.setLayoutResource(mLoadingLayoutId);
            mLoadingView = mVbLoading.inflate();
            mLoadingView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
        setVisibility(VISIBLE);
        mLoadingView.setVisibility(VISIBLE);
        hideEmptyView();
        hideErrorView();
    }

    /**
     * 展示错误页面
     */
    public void showError() {
        if (mErrorView == null) {
            mVbError.setLayoutResource(mErrorLayoutId);
            mErrorView = mVbError.inflate();
            TextView textError = mErrorView.findViewById(R.id.dialog_error_tv_id);
            if (textError != null && !TextUtils.isEmpty(mErrorTextStr)) {
                textError.setText(mErrorTextStr);
            }
            mErrorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    if (mRetryListener != null) {
                        mRetryListener.onRetry();
                    }
                }
            });
        }
        setVisibility(VISIBLE);
        mErrorView.setVisibility(VISIBLE);
        hideLoadingView();
        hideEmptyView();
    }

    /**
     * 展示空视图
     */
    public void showEmpty() {
        if (mEmptyView == null) {
            mVbEmpty.setLayoutResource(mEmptyLayoutId);
            mEmptyView = mVbEmpty.inflate();
            TextView textView = mEmptyView.findViewById(R.id.dialog_empty_tv_id);
            if (textView != null && !TextUtils.isEmpty(mEmptyTextStr)) {
                textView.setText(mEmptyTextStr);
            }
        }
        setVisibility(VISIBLE);
        mEmptyView.setVisibility(VISIBLE);
        hideLoadingView();
        hideErrorView();
    }

    public void hide() {
        setVisibility(GONE);
    }

    private void hideLoadingView() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(GONE);
        }
    }
}
