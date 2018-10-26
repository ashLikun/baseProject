package com.ashlikun.common.mode.javabean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/4　14:05
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：图片数据
 */
public class ImageData implements Parcelable{

    /**
     * 图片ID
     */
    public int image_id;
    /**
     * 图片地址
     */
    public String img_url;
    /**
     * 缩略图地址
     */
    public String thumb_img_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.image_id);
        dest.writeString(this.img_url);
        dest.writeString(this.thumb_img_url);
    }

    public ImageData() {
    }

    protected ImageData(Parcel in) {
        this.image_id = in.readInt();
        this.img_url = in.readString();
        this.thumb_img_url = in.readString();
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel source) {
            return new ImageData(source);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };
}
