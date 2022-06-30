package com.ashlikun.baseproject.common.utils.other

import android.app.NotificationManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import com.ashlikun.baseproject.common.R
import com.ashlikun.baseproject.common.mode.ApiCommon
import com.ashlikun.baseproject.libcore.utils.extend.showToast
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.core.mvvm.launch
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.okhttputils.http.download.DownloadTask
import com.ashlikun.okhttputils.http.download.DownloadTaskListenerAdapter
import com.ashlikun.utils.other.ApkUtils
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.NotificationUtil
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
                        downloadApk(it.dataX!!.url)
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

    fun downloadApk(downloadUrl: String) {
        DownloadManager.get().addDownloadTask(
            DownloadTask(url = downloadUrl, listener = object : DownloadTaskListenerAdapter() {
                val not by lazy {
                    NotificationUtil.createBuilder(
                        icon = R.mipmap.app_logo,
                        title = "App更新",
                        msg = "",
                        channelName = "默认通知",
                        importance = NotificationManager.IMPORTANCE_DEFAULT
                    ).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                }

                override fun onDownloadSuccess(downloadTask: DownloadTask, file: File) {
                    installApp(file)
                    NotificationUtil.cancel(123)
                }

                override fun onDownloading(
                    downloadTask: DownloadTask,
                    completedSize: Long,
                    totalSize: Long,
                    percent: Double
                ) {
                    super.onDownloading(downloadTask, completedSize, totalSize, percent)
                    not.setContentText(
                        "下载中  ${FileUtils.autoFormetFileSize(completedSize.toDouble())}/${
                            FileUtils.autoFormetFileSize(
                                totalSize.toDouble()
                            )
                        }"
                    )
                    NotificationUtil.show(123, not)
                }

                override fun onError(downloadTask: DownloadTask, errorCode: Int) {
                    super.onError(downloadTask, errorCode)
                    SuperToast.showInfoMessage("文件下载失败")
                    NotificationUtil.cancel(123)
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