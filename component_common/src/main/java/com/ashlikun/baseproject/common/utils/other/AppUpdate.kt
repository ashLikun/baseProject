package com.ashlikun.baseproject.common.utils.other

import android.app.NotificationManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import com.ashlikun.baseproject.common.R
import com.ashlikun.baseproject.common.mode.ApiCommon
import com.ashlikun.baseproject.libcore.utils.extend.showToast
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.core.mvvm.launch
import com.ashlikun.customdialog.DialogProgress
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.okhttputils.http.download.DownloadTask
import com.ashlikun.okhttputils.http.download.DownloadTaskListenerAdapter
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.ApkUtils
import com.ashlikun.utils.other.DeviceUtil
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.NotificationUtil
import com.ashlikun.utils.ui.fActivity
import com.ashlikun.utils.ui.fCActivity
import com.ashlikun.utils.ui.modal.SuperToast
import java.io.File

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/7　9:56
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：app更新
 */
object AppUpdate {

    fun check(handle: HttpUiHandle = HttpUiHandle.getNoTips()) = fCActivity?.launch {
        ApiCommon.api.checkUpdata(handle).also {
            if (it.isSucceed) {
                if (!it.dataX!!.url.isNullOrEmpty()) {
                    val aa = AlertDialog.Builder(ActivityManager.foregroundActivity!!)
                        .setCancelable(false)
                        .setTitle(it.dataX!!.content)
                        .setMessage(it.dataX!!.updateInfo)
                    aa.setPositiveButton("升级") { dialoog, which ->
                        downloadApk(it.dataX.url, it.dataX.isForce == 1)
                    }
                    if (it.dataX!!.isForce == 0) {
                        aa.setNegativeButton("稍后再说", null)
                    } else if (it.dataX!!.isForce == 1) {
                        aa.setCancelable(false)
                    }
                    aa.show()
                } else {
                    if (handle.isErrorToastShow) {
                        SuperToast.showInfoMessage("已经是最新版本了")
                    }
                }
            } else {
                if (handle.isErrorToastShow) {
                    it.showToast()
                }
            }
        }
    }

    val not by lazy {
        if (DeviceUtil.deviceBrand.lowercase() == "xiaomi") {
            //小米会放到不重要的通知里面去
            NotificationUtil.createChannel("App Update",
                channelGroupName = AppUtils.appName,
                importance = NotificationManager.IMPORTANCE_HIGH)
        } else {
            NotificationUtil.createChannel("App Update",
                channelGroupName = AppUtils.appName,
                importance = NotificationManager.IMPORTANCE_DEFAULT)
        }
        val builder = NotificationCompat.Builder(AppUtils.app, "App Update")
            .setWhen(System.currentTimeMillis())//设置事件发生的时间。面板中的通知是按这个时间排序。
            .setSmallIcon(R.mipmap.app_logo) //这玩意在通知栏上显示一个logo
            .setTicker("App Update")
            .setContentTitle("App Updating")
            .setContentText("")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(true)
            .setAutoCancel(true) //点击不让消失
            .setSound(null) //关了通知默认提示音
            .setPriority(NotificationCompat.PRIORITY_MAX) //咱们通知很重要
            .setVibrate(null) //关了车震
            //这样通知只会在通知首次出现时打断用户（通过声音、振动或视觉提示），而之后更新则不会再打断用户。
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                    val n = builder.build()
//                    n.flags = n.flags or NotificationCompat.FLAG_NO_CLEAR //不让手动清除 通知栏常驻
        builder
    }

    /**
     * 强制的对话框
     */
    val progressDialog by lazy {
        DialogProgress(fActivity!!).apply {
            setCancelable(false)
            setOnShowListener {
                setTitleText("App Updating")
            }
        }
    }

    fun downloadApk(downloadUrl: String, isForce: Boolean = false) {
        if (isForce) {
            progressDialog.show()
        } else {
            NotificationUtil.show(123, not)
        }
        DownloadManager.get().addDownloadTask(
            DownloadTask(url = downloadUrl, listener =
            object : DownloadTaskListenerAdapter() {
                override fun onDownloadSuccess(downloadTask: DownloadTask, file: File) {
                    installApp(file)
                    if (!isForce) {
                        NotificationUtil.cancel(123)
                    } else {
                        if (progressDialog.isShowing) {
                            progressDialog.finish()
                        }
                    }
                }

                override fun onDownloading(downloadTask: DownloadTask, completedSize: Long, totalSize: Long, percent: Double) {
                    super.onDownloading(downloadTask, completedSize, totalSize, percent)
                    if (!isForce) {
                        not.setContentText(
                            "App Updating  ${FileUtils.autoFormetFileSize(completedSize.toDouble())}/${
                                FileUtils.autoFormetFileSize(
                                    totalSize.toDouble()
                                )
                            }"
                        )
                        NotificationUtil.show(123, not)
                    } else {

                        progressDialog.setProgress(percent.toInt())
                    }
                }

                override fun onError(downloadTask: DownloadTask, errorCode: Int) {
                    super.onError(downloadTask, errorCode)
                    SuperToast.showInfoMessage("Apk 文件下载失败")
                    if (!isForce) {
                        NotificationUtil.cancel(123)
                    } else {
                        if (progressDialog.isShowing) {
                            progressDialog.finish()
                        }
                        // 退出
                        ActivityManager.get().exitAllActivity()
                    }
                }
            })
        )
    }

    fun installApp(file: File) {
        ApkUtils.installApp(file)

//        val activity = ActivityManager.foregroundActivity
//        if (ApkUtils.canRequestPackageInstalls()) {
//            ApkUtils.installApp(file)
//        } else {
//            if (activity is ComponentActivity) {
//                activity.launchForActivityResult(ApkUtils.unknownAppInstall()) {
//                    ApkUtils.installApp(file)
//                }
//            }
//        }
    }
}