package org.ninetripods.mq.study.util.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.interf.MyOnclickListener;

import java.util.List;

/**
 * Created by MQ on 2017/4/8.
 */

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> list;
    private MyOnclickListener myItemClickListener;

    public PopupAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setOnItemClickListener(MyOnclickListener listener) {
        this.myItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.choice_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView choice_text;

        public MyViewHolder(final View itemView) {
            super(itemView);
            choice_text = (TextView) itemView.findViewById(R.id.choice_text);
        }
    }
}
