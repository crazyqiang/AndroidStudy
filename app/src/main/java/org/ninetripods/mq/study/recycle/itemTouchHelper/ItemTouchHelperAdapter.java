package org.ninetripods.mq.study.recycle.itemTouchHelper;

/**
 * Created by MQ on 2017/5/30.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
