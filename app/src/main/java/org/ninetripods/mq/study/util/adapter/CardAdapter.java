package org.ninetripods.mq.study.util.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;

/**
 * Created by MQ on 2017/4/24.
 */

public class CardAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.card_layout, container, false);
        container.addView(view);
        TextView textView = (TextView) view.findViewById(R.id.tv_text);
        textView.setText("HelloWorld");
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
