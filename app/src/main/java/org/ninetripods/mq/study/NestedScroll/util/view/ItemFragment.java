package org.ninetripods.mq.study.NestedScroll.util.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.contacts_recycle.CustomItemDecoration;
import org.ninetripods.mq.study.recycle.contacts_recycle.SideBar;
import org.ninetripods.mq.study.util.CommonUtil;
import org.ninetripods.mq.study.util.adapter.ContactAdapter;
import org.ninetripods.mq.study.util.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private ListView list_view;
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    private RecyclerView recycle_view;
    List<ContactBean> nameList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private CustomItemDecoration decoration;
    private ContactAdapter mAdapter;
    private SideBar side_bar;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        list_view = (ListView) view.findViewById(R.id.list_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                getActivity(), android.R.layout.simple_list_item_1, data);
//        list_view.setAdapter(adapter);
        mAdapter = new ContactAdapter(getActivity());
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recycle_view.setLayoutManager(layoutManager);
//        recycle_view.addItemDecoration(decoration = new CustomItemDecoration(getActivity()));
        initDatas();
        recycle_view.setAdapter(mAdapter);
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
//        side_bar.setIndexStr(tagsStr);
//        decoration.setDatas(nameList, tagsStr);
        mAdapter.addAll(nameList);
    }
}
