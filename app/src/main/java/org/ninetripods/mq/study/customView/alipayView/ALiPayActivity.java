package org.ninetripods.mq.study.customView.alipayView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class ALiPayActivity extends BaseActivity {
    private AlipayView alipay_view;
    private Button btn_start_pay, btn_end_pay;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_ali_pay);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "支付宝支付", true);
        alipay_view = (AlipayView) findViewById(R.id.alipay_view);
        alipay_view.setState(AlipayView.State.IDLE);
        btn_start_pay = (Button) findViewById(R.id.btn_start_pay);
        btn_end_pay = (Button) findViewById(R.id.btn_end_pay);
    }

    @Override
    public void initEvents() {
        btn_start_pay.setOnClickListener(this);
        btn_end_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_pay:
                alipay_view.setOverPay(false);
                alipay_view.setState(AlipayView.State.PROGRESS);
                break;
            case R.id.btn_end_pay:
                alipay_view.setOverPay(true);
                break;
        }
    }
}
