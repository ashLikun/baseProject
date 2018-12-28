package com.ashlikun.common.mode.javabean

import android.os.Parcel
import android.os.Parcelable
import com.ashlikun.gson.StringNullAdapter
import com.google.gson.annotations.SerializedName

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/4　14:05
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：图片数据
 */
class ImageData() : Parcelable {
    /**
     * 图片ID
     */
    var id: Int = 0
    /**
     * 图片地址
     */
    var image: String = StringNullAdapter.NULL
    /**
     * 缩略图地址
     */
    @SerializedName("thumb_image")
    var thumbImage: String = StringNullAdapter.NULL

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        image = parcel.readString()
        thumbImage = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(thumbImage)
    }

    override fun describeContents(): Int {
        return 0
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


