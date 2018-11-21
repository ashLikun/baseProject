package com.ashlikun.common.mode.javabean

import android.os.Parcel
import android.os.Parcelable

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/4　14:05
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：图片数据
 */
class ImageData : Parcelable {

    /**
     * 图片ID
     */
    var image_id: Int = 0
    /**
     * 图片地址
     */
    var img_url: String? = null
    /**
     * 缩略图地址
     */
    var thumb_img_url: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.image_id)
        dest.writeString(this.img_url)
        dest.writeString(this.thumb_img_url)
    }


    protected constructor(`in`: Parcel) {
        this.image_id = `in`.readInt()
        this.img_url = `in`.readString()
        this.thumb_img_url = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<ImageData> {
        override fun createFromParcel(parcel: Parcel): ImageData {
            return ImageData(parcel)
        }

        override fun newArray(size: Int): Array<ImageData?> {
            return arrayOfNulls(size)
        }
    }


}
