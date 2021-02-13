package org.ninetripods.mq.study.propertyAnimator;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class ViewPropertyAnimatorActivity extends BaseActivity {


    private ImageView iv_img;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_view_property_animator);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "ViewPropertyAnimator", true);
        iv_img = (ImageView) findViewById(R.id.iv_text_ifg);
    }

    @Override
    public void initEvents() {
        iv_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_ifg:
                //1、多个ObjectAnimator
//                ObjectAnimator animX = ObjectAnimator.ofFloat(iv_img, "x", 500f);
//                ObjectAnimator animY = ObjectAnimator.ofFloat(iv_img, "y", 500f);
//                AnimatorSet animSetXY = new AnimatorSet();
//                animSetXY.playTogether(animX, animY);
//                animSetXY.setDuration(2000);
//                animSetXY.start();
                //2、ObjectAnimator+PropertyValuesHolder组合
//                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 500f);
//                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 500f);
//                ObjectAnimator.ofPropertyValuesHolder(iv_img, pvhX, pvhY).setDuration(2000).start();
                //3、ViewPropertyAnimator
                iv_img.animate().x(500f).y(500f).setDuration(2000).start();
                break;
        }
    }
}
