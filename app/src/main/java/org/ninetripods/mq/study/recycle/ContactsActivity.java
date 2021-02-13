package org.ninetripods.mq.study.recycle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.contacts_recycle.CustomItemDecoration;
import org.ninetripods.mq.study.recycle.contacts_recycle.itemAnimator.SlideInOutLeftItemAnimator;
import org.ninetripods.mq.study.util.adapter.ContactAdapter;
import org.ninetripods.mq.study.recycle.contacts_recycle.SideBar;
import org.ninetripods.mq.study.util.CommonUtil;
import org.ninetripods.mq.study.util.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends BaseActivity {

    private RecyclerView rl_recycle_view;
    private ContactAdapter mAdapter;
    private CustomItemDecoration decoration;
    private SideBar side_bar;
    List<ContactBean> nameList = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_recycle_view);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "通讯录", true, true);
        mAdapter = new ContactAdapter(this);
        rl_recycle_view = (RecyclerView) findViewById(R.id.rl_recycle_view);
        //侧边导航栏
        side_bar = (SideBar) findViewById(R.id.side_bar);
        layoutManager = new LinearLayoutManager(this);
        rl_recycle_view.setLayoutManager(layoutManager);
        rl_recycle_view.addItemDecoration(decoration = new CustomItemDecoration(this));
        rl_recycle_view.setItemAnimator(new SlideInOutLeftItemAnimator(rl_recycle_view));
        initDatas();
        rl_recycle_view.setAdapter(mAdapter);
    }

    @Override
    public void initEvents() {
        side_bar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || nameList.size() <= 0) return;
                for (int i = 0; i < nameList.size(); i++) {
                    if (tag.equals(nameList.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
//                        layoutManager.scrollToPosition(i);
                        return;
                    }
                }
            }
        });
    }

    private void initDatas() {
        String[] names = {"孙尚香", "安其拉", "白起", "不知火舞", "@小马快跑", "_德玛西亚之力_", "妲己", "狄仁杰", "典韦", "韩信",
                "老夫子", "刘邦", "刘禅", "鲁班七号", "墨子", "孙膑", "孙尚香", "孙悟空", "项羽", "亚瑟",
                "周瑜", "庄周", "蔡文姬", "甄姬", "廉颇", "程咬金", "后羿", "扁鹊", "钟无艳", "小乔", "王昭君", "虞姬",
                "李元芳", "张飞", "刘备", "牛魔", "张良", "兰陵王", "露娜", "貂蝉", "达摩", "曹操", "芈月", "荆轲", "高渐离",
                "钟馗", "花木兰", "关羽", "李白", "宫本武藏", "吕布", "嬴政", "娜可露露", "武则天", "赵云", "姜子牙",};
        for (String name : names) {
            ContactBean bean = new ContactBean();
            bean.setName(name);
            nameList.add(bean);
        }
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
        side_bar.setIndexStr(tagsStr);
        decoration.setDatas(nameList, tagsStr);
        mAdapter.addAll(nameList);
    }

    @Override
    public void add() {
        ContactBean bean = new ContactBean();
        bean.setName("安其拉666");
        nameList.add(bean);
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
        side_bar.setIndexStr(tagsStr);
        decoration.setDatas(nameList, tagsStr);
        //这里写死位置1 只是为了看动画效果
        mAdapter.add(bean, 1);
    }
}
