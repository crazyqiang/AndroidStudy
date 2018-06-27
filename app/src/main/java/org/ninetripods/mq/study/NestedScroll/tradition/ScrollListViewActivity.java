package org.ninetripods.mq.study.NestedScroll.tradition;

import android.widget.ArrayAdapter;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.NestedScroll.util.view.CustomListView;
import org.ninetripods.mq.study.R;

public class ScrollListViewActivity extends BaseActivity {

    private CustomListView lv_listView;
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_scroll);
    }

    @Override
    public void initViews() {
        lv_listView = (CustomListView) findViewById(R.id.lv_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, data);
        lv_listView.setAdapter(adapter);
    }

    @Override
    public void initEvents() {

    }


}
