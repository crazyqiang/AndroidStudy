package org.ninetripods.mq.study.NestedScroll.util.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ninetripods.mq.study.R;

/**
 * Created by mq on 2018/6/8 上午11:42
 * mqcoder90@gmail.com
 */

public class MyPagerAdapter extends PagerAdapter {

    private Context mContext;

    public MyPagerAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 返回页卡的数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return 10;
    }

    /**
     * View是否来自对象
     *
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 创建一个页卡
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_viewpager_content,
                container, false);
        container.addView(itemView);
        return itemView;
    }

    /**
     * 销毁一个页卡
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View itemView = (View) object;
        container.removeView(itemView);
    }
}
