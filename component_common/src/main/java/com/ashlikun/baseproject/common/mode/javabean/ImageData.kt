package com.ashlikun.baseproject.common.mode.javabean

import android.os.Parcel
import android.os.Parcelable
import com.ashlikun.baseproject.common.adapter.banner.IBannerData
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
class ImageData(image: String = StringNullAdapter.NULL,
                thumbImage: String = StringNullAdapter.NULL,
                id: Int = 0) : Parcelable, IBannerData {
    override fun getImageUrl() = image

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

    init {
        this.image = image
        this.id = id
        this.thumbImage = thumbImage
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        image = parcel.readString()?:""
        thumbImage = parcel.readString()?:""
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
