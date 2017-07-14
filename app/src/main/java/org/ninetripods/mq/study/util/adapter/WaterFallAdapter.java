package org.ninetripods.mq.study.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.water_fall.ImgUtils.ImageFetcher;
import org.ninetripods.mq.study.util.Constant;
import org.ninetripods.mq.study.util.DpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MQ on 2017/6/23.
 */

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.MyHolder> {
    private Context mContext;
    private int DATA_SIZE = Constant.imageUrls.length;
    private List<Integer> hList;
    private ImageFetcher mImageFetcher;

    public WaterFallAdapter(Context mContext, ImageFetcher mImageFetcher) {
        this.mContext = mContext;
        this.mImageFetcher = mImageFetcher;
        hList = new ArrayList<>();
        for (int i = 0; i < DATA_SIZE; i++) {
            int height = new Random().nextInt(200) + 300;//[100,500)的随机数
            hList.add(height);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        return new MyHolder(imageView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        int width = DpUtil.getScreenSizeWidth(mContext) / 3;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, hList.get(position));
        holder.imageView.setLayoutParams(params);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageFetcher.loadImage(Constant.imageUrls[position], holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick(holder.getAdapterPosition(), v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DATA_SIZE;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
