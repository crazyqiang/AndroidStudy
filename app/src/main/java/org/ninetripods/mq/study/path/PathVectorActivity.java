package org.ninetripods.mq.study.path;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class PathVectorActivity extends BaseActivity {
    private ImageView iv_anim;
    private Button btn_start;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_path_vector);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "AnimatedVectorDrawable", true);
        iv_anim = (ImageView) findViewById(R.id.iv_anim);
//        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.icon_vector_anim);
//        if (animatedVectorDrawableCompat != null) {
//            iv_anim.setImageDrawable(animatedVectorDrawableCompat);
//        }
        btn_start = (Button) findViewById(R.id.btn_start);
    }

    @Override
    public void initEvents() {
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                Drawable drawable = iv_anim.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
                break;
        }
    }
}
