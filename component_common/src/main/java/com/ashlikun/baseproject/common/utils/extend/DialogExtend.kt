package com.ashlikun.baseproject.common.utils.extend

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.ashlikun.baseproject.common.R

/**
 * 作者　　: 李坤
 * 创建时间: 2020/4/28　18:03
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */

fun MaterialDialog.positiveButtonX(
    @StringRes res: Int? = R.string.base_dialog_confirm,
    text: CharSequence? = null,
    click: DialogCallback? = null
): MaterialDialog {
    return positiveButton(res, text, click)
}

fun MaterialDialog.negativeButtonX(
    @StringRes res: Int? = R.string.base_dialog_cancel,
    text: CharSequence? = null,
    click: DialogCallback? = null
): MaterialDialog {
    return negativeButton(res, text, click)
}

inline fun MaterialDialog.titleX(
    @StringRes res: Int? = R.string.base_dialog_tips,
    text: String? = null
) = title(res, text)