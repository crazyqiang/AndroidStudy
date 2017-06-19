package org.ninetripods.mq.study.util.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MQ on 2017/6/19.
 */

public class QQPointBean implements Parcelable {
    public int redNum;

    public QQPointBean(int redNum) {
        this.redNum = redNum;
    }

    protected QQPointBean(Parcel in) {
        redNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(redNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QQPointBean> CREATOR = new Creator<QQPointBean>() {
        @Override
        public QQPointBean createFromParcel(Parcel in) {
            return new QQPointBean(in);
        }

        @Override
        public QQPointBean[] newArray(int size) {
            return new QQPointBean[size];
        }
    };
}
