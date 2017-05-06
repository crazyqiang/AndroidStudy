package org.ninetripods.mq.multiprocess_sever_i;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MQ on 2017/3/21.
 */

public class MultiProcess implements Parcelable {


    private String info;

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public MultiProcess(String info) {
        this.info = info;
    }

    protected MultiProcess(Parcel in) {
        info = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(info);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MultiProcess> CREATOR = new Creator<MultiProcess>() {
        @Override
        public MultiProcess createFromParcel(Parcel in) {
            return new MultiProcess(in);
        }

        @Override
        public MultiProcess[] newArray(int size) {
            return new MultiProcess[size];
        }
    };

    @Override
    public String toString() {
        return info;
    }
}
