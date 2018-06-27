package org.ninetripods.mq.study.NestedScroll.util.adapter;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mq on 2018/2/8 下午5:02
 * mqcoder90@gmail.com
 */

public class ZJViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final AdapterView.OnItemClickListener onItemClickListener;

    public ZJViewHolder(View itemView, AdapterView.OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);

        if (itemView.getBackground() == null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = itemView.getContext().getTheme();
            int top = itemView.getPaddingTop();
            int bottom = itemView.getPaddingBottom();
            int left = itemView.getPaddingLeft();
            int right = itemView.getPaddingRight();
            if (theme.resolveAttribute(android.R.attr.selectableItemBackground,
                    typedValue, true)) {
                itemView.setBackgroundResource(typedValue.resourceId);
            }
            itemView.setPadding(left, top, right, bottom);
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = getAdapterPosition();
            if (position >= 0) {
                onItemClickListener.onItemClick(null, v, position, getItemId());
            }
        }
    }

    private View findViewById(int id) {
        return id == 0 ? itemView : itemView.findViewById(id);
    }


    public ZJViewHolder setText(int id, CharSequence sequence) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(sequence);
        }
        return this;
    }

    public ZJViewHolder setText(int id, CharSequence sequence, int color) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(color);
            ((TextView) view).setText(sequence);
        }
        return this;
    }

    public ZJViewHolder setHtmlText(int id, CharSequence sequence) {
        if (sequence == null) return this;
        View view = findViewById(id);
        if (view instanceof TextView) {
            view.setVisibility(View.VISIBLE);
            ((TextView) view).setText(Html.fromHtml((String) sequence));
        }
        return this;
    }

    public ZJViewHolder setViewVisible(int id, int state) {
        View view = findViewById(id);
        if (view != null) {
            view.setVisibility(state);
        }
        return this;
    }

    public TextView getTextView(int id) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            return (TextView) view;
        }
        return null;
    }

    public ZJViewHolder setText(int id, @StringRes int stringRes) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(stringRes);
        }
        return this;
    }

    public ZJViewHolder textColorId(int id, int colorId) {
        View view = findViewById(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
        return this;
    }

    public ZJViewHolder setImageResource(int id, int imageId) {
        View view = findViewById(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(imageId);
        }
        return this;
    }

//    public ZJViewHolder setImage(Context mContext, int resId, int defaultId, String imgUrl) {
//        View view = findViewById(resId);
//        if (view instanceof ImageView) {
//            view.setVisibility(View.VISIBLE);
//            ImageUtils.getInstance().with(mContext).load(imgUrl)
//                    .config(Bitmap.Config.RGB_565).placeholder(defaultId)
//                    .resize(200, 200).tag(mContext).into((ImageView) view);
//        }
//        return this;
//    }
}
