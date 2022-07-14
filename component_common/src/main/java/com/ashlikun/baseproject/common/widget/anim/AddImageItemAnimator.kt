package com.ashlikun.baseproject.common.widget.anim

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * 作者　　: 李坤
 * 创建时间: 2019/3/21　17:18
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：去除透明度动画
 */
class AddImageItemAnimator : DefaultItemAnimator() {

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        var boolean = super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
        newHolder?.itemView?.alpha = 1f
        return boolean
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        var boolean = super.animateAdd(holder)
        holder?.itemView?.alpha = 1f
        return boolean

    }
}