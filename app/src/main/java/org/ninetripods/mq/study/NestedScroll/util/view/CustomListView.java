package org.ninetripods.mq.study.NestedScroll.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by mq on 2018/5/10 下午2:39
 * mqcoder90@gmail.com
 */

public class CustomListView extends ListView {

    public CustomListView(Context context) {
        this(context, null);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

}
