package org.ninetripods.mq.study.recycle.itemTouchHelper;

import android.support.v7.widget.RecyclerView;

/**
 * Created by MQ on 2017/5/30.
 */

public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
