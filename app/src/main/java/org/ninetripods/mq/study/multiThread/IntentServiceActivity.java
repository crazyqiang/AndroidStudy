package org.ninetripods.mq.study.multiThread;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.CommonWebviewActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.multiThread.intentService.MyIntentService;
import org.ninetripods.mq.study.multiThread.intentService.UpdateUIReceiver;
import org.ninetripods.mq.study.popup.popupWindow.PopWindow;
import org.ninetripods.mq.study.util.CommonUtil;
import org.ninetripods.mq.study.util.Constant;
import org.ninetripods.mq.study.util.DpUtil;

public class IntentServiceActivity extends BaseActivity implements PopWindow.ViewInterface {


    private TextView tv_start_one, tv_start_two;
    private PopWindow popupWindow;
    private ProgressBar pb_one, pb_two;
    private TextView tv_progress_one, tv_progress_two;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_itent_service);
    }

    @Override
    public void initViews() {
        receiver.register(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "IntentService", true, true, 2);
        tv_start_one = (TextView) findViewById(R.id.tv_start_one);
        tv_start_two = (TextView) findViewById(R.id.tv_start_two);
    }

    @Override
    public void initEvents() {
        tv_start_one.setOnClickListener(this);
        tv_start_two.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(IntentServiceActivity.this, MyIntentService.class);
        switch (v.getId()) {
            case R.id.tv_start_one:
                intent.setAction(MyIntentService.ACTION_ONE);
                startService(intent);
                break;
            case R.id.tv_start_two:
                intent.setAction(MyIntentService.ACTION_TWO);
                startService(intent);
                break;
        }
        showPopupWindow();
    }

    @Override
    protected void onDestroy() {
        receiver.unRegister(this);
        super.onDestroy();
    }

    //BroadcastReceiver用来更新UI
    private UpdateUIReceiver receiver = new UpdateUIReceiver() {
        @Override
        public void UpdateUI(int type, int progress) {
            switch (type) {
                case 0:
                    //更新第一个进度
                    if (tv_progress_one == null || pb_one == null) return;
                    tv_progress_one.setText(String.valueOf(progress + "%"));
                    pb_one.setProgress(progress);
                    break;
                case 1:
                    //更新第二个进度
                    if (tv_progress_two == null || pb_two == null) return;
                    tv_progress_two.setText(String.valueOf(progress + "%"));
                    pb_two.setProgress(progress);
                    break;
            }
        }
    };

    public void showPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.intent_service, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new PopWindow.Builder(this)
                .setView(R.layout.intent_service)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.7f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setChildrenView(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.TOP, 0, (int) DpUtil.dp2px(this, 150));
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.intent_service:
                tv_progress_one = (TextView) view.findViewById(R.id.tv_progress_one);
                tv_progress_two = (TextView) view.findViewById(R.id.tv_progress_two);
                pb_one = (ProgressBar) view.findViewById(R.id.pb_one);
                pb_one.setProgress(0);
                pb_two = (ProgressBar) view.findViewById(R.id.pb_two);
                pb_two.setProgress(0);
                break;
        }
    }

    @Override
    public void openWebview() {
        CommonWebviewActivity.webviewEntrance(this, Constant.INTENT_SERVICE_BLOG);
    }
}
