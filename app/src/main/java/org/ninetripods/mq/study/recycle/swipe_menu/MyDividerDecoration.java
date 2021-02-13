package org.ninetripods.mq.study.recycle.swipe_menu;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by MQ on 2017/6/11.
 */

public class MyDividerDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, 2);
    }
}
