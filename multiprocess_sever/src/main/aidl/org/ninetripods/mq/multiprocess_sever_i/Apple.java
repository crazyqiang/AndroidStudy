package org.ninetripods.mq.multiprocess_sever_i;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MQ on 2017/3/15.
 */

public class Apple implements Parcelable {
    private String name;
    private float price;

    public String getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(String noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    private String noticeInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Apple(String name, float price, String noticeInfo) {
        this.name = name;
        this.price = price;
        this.noticeInfo = noticeInfo;
    }

    protected Apple(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        noticeInfo = in.readString();
    }

    public static final Creator<Apple> CREATOR = new Creator<Apple>() {
        @Override
        public Apple createFromParcel(Parcel in) {
            return new Apple(in);
        }

        @Override
        public Apple[] newArray(int size) {
            return new Apple[size];
        }
    };

    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        price = dest.readInt();
        noticeInfo = dest.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(noticeInfo);
    }
}
