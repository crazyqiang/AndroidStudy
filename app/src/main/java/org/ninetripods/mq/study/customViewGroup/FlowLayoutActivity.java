package org.ninetripods.mq.study.customViewGroup;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.customViewGroup.flowLayout.FlowLayout;

public class FlowLayoutActivity extends BaseActivity {

    //TODO ViewGroup滑动 ViewDragHelper TextView文字过多时的显示

    private FlowLayout flow_layout;
    String[] names = {"戏命师.晋", "孙尚香", "安其拉",  "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
            "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
            "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
            "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
            "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "白起","吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",

            "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
            "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
            "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "白起","吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",
            "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
            "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
            "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "白起","吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙"};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_flow_layout);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "流式布局", true);
        flow_layout = (FlowLayout) findViewById(R.id.flow_layout);
        initDatas();
    }

    @Override
    public void initEvents() {
    }


    private void initDatas() {
        for (String name : names) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_flow_layout, flow_layout, false);
            tv.setBackgroundResource(R.drawable.popup_corner);
            tv.setText(name);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FlowLayoutActivity.this, "" + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
            flow_layout.addView(tv);
        }
    }
}
