package org.ninetripods.mq.study.util.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.ColorGenerator;
import org.ninetripods.mq.study.util.TextDrawable;
import org.ninetripods.mq.study.util.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017/5/8.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyRecycleHolder> {

    private List<ContactBean> contactBeanList;
    private Context mContext;
    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder().round();

    public ContactAdapter(Context context) {
        this.mContext = context;
        contactBeanList = new ArrayList<>();
    }

    public void addAll(List<ContactBean> beans) {
        if (contactBeanList.size() > 0) {
            contactBeanList.clear();
        }
        contactBeanList.addAll(beans);
        notifyDataSetChanged();
    }

    public void add(ContactBean bean, int position) {
        contactBeanList.add(position, bean);
        notifyItemInserted(position);
    }

    public void add(ContactBean bean) {
        contactBeanList.add(bean);
        notifyItemChanged(contactBeanList.size() - 1);
    }

    @Override
    public MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_layout, parent, false);
        return new MyRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecycleHolder holder, int position) {
        if (contactBeanList == null || contactBeanList.size() == 0 || contactBeanList.size() <= position)
            return;
        ContactBean bean = contactBeanList.get(position);
        if (bean != null) {
            holder.tv_name.setText(bean.getName());
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(bean.getName().charAt(0)), mColorGenerator.getColor(bean.getName()));
            holder.iv_img.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return contactBeanList.size();
    }

    public static class MyRecycleHolder extends RecyclerView.ViewHolder {
        public final TextView tv_name;
        public final ImageView iv_img;

        public MyRecycleHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
