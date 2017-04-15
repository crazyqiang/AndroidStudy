package org.ninetripods.mq.study.designMode;

import android.view.View;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.designMode.Decorator.Cake;
import org.ninetripods.mq.study.designMode.Decorator.CandleDecorator;
import org.ninetripods.mq.study.designMode.Decorator.FruitDecorator;
import org.ninetripods.mq.study.util.MyLog;

public class DesignModeActivity extends BaseActivity {
    private final String TAG = DesignModeActivity.this.getClass().getSimpleName();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_design_mode);
    }

    public void decoratorMode(View view) {
        Cake cake = new Cake();
        MyLog.e(TAG, cake.getDescription() + "，总共花费" + cake.cost() + "元");
        FruitDecorator fruitDecorator = new FruitDecorator(cake);
        MyLog.e(TAG, fruitDecorator.getDescription() + "，总共花费" + fruitDecorator.cost() + "元");
        CandleDecorator candleDecorator = new CandleDecorator(fruitDecorator);
        MyLog.e(TAG, candleDecorator.getDescription() + "，总共花费" + candleDecorator.cost() + "元");
    }
}
