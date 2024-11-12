package com.ashlikun.baseproject.module.other.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ashlikun.baseproject.module.other.databinding.OtherActivityDownloadBinding
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.okhttputils.http.download.DownloadTask
import com.ashlikun.okhttputils.http.download.DownloadTaskListener
import com.ashlikun.utils.other.LogUtils
import java.io.File

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:22
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：下载的案例类
 */

class DownLoadActivity : AppCompatActivity(), View.OnClickListener, DownloadTaskListener {

    val binding by lazy {
        OtherActivityDownloadBinding.inflate(layoutInflater)
    }
    internal val downloadManager by lazy { DownloadManager.get() }

    private val url_360 =
        "http://msoftdl.360.cn/mobilesafe/shouji360/360safesis/360StrongBox_1.0.9.1008.apk"

    private val url_qq =
        "http://221.228.67.156/dd.myapp.com/16891/62B928C30FE677EDEEA9C504486444E9" + ".apk?mkey=5736f6098218f3cf&f=1b58&c=0&fsname=com.tencent.mobileqq_6.3.3_358.apk&p=.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.run {
            //----------第一组下载----------------
            button.setOnClickListener(this@DownLoadActivity)
            buttonpause.setOnClickListener(this@DownLoadActivity)
            buttoncancel.setOnClickListener(this@DownLoadActivity)
            buttonresume.setOnClickListener(this@DownLoadActivity)
            //-------------第二组下载--------------
            button1.setOnClickListener(this@DownLoadActivity)
            buttonpause1.setOnClickListener(this@DownLoadActivity)
            buttoncancel1.setOnClickListener(this@DownLoadActivity)
            buttonresume1.setOnClickListener(this@DownLoadActivity)
        }
    }

    override fun onClick(v: View) {
        binding.run {
            when {
                button === v -> {
                    download360()
                }

                buttoncancel === v -> {
                    downloadManager.cancel(URL_360_ID)
                }

                buttonpause === v -> {
                    downloadManager.pause(URL_360_ID)
                }

                buttonresume === v -> {
                    downloadManager.resume(URL_360_ID)
                }
            }

            //-----------------第二组下载
            when {
                button1 === v -> {
                    downloadQQ()
                }

                buttoncancel1 === v -> {
                    downloadManager.cancel(URL_QQ_ID)
                }

                buttonpause1 === v -> {
                    downloadManager.pause(URL_QQ_ID)
                }

                buttonresume1 === v -> {
                    downloadManager.resume(URL_QQ_ID)
                }

                else -> {

                }
            }
        }

    }

    private fun download360() {
        downloadManager.addDownloadTask(
            DownloadTask(
                id = URL_360_ID, url = url_360, listener = this
            )
        )
    }

    private fun downloadQQ() {
        downloadManager.addDownloadTask(
            DownloadTask(
                id = URL_QQ_ID, url = url_qq, listener = this
            )
        )
    }

    //=========================================================================
    override fun onDownloading(
        downloadTask: DownloadTask,
        completedSize: Long,
        totalSize: Long,
        percent: Double
    ) {
        LogUtils.i(
            "onDownloading completedSize=" + completedSize + " ,totalSize=" + totalSize + " ,percent=" +
                    percent
        )
        if (downloadTask.id == URL_360_ID) {
            binding.progressBar.progress = percent.toInt()
            binding.tvStatus.text = "正在下载..." + percent.toInt() + "%"
        } else {
            binding.progressBar1.progress = percent.toInt()
            binding.tvStatus1.text = "正在下载..." + percent.toInt() + "%"
        }

    }

    override fun onPause(
        downloadTask: DownloadTask,
        completedSize: Long,
        totalSize: Long,
        percent: Double
    ) {
        LogUtils.i("onPause=$completedSize ,totalSize=$totalSize ,percent=$percent")
        if (downloadTask.id == URL_360_ID) {
            binding.tvStatus.text = "下载已暂停,已下载：" + percent.toInt() + "%"
        } else {
            binding.tvStatus1.text = "下载已暂停,已下载：" + percent.toInt() + "%"
        }
    }

    override fun onCancel(downloadTask: DownloadTask) {
        LogUtils.i("onCancel")
        if (downloadTask.id == URL_360_ID) {
            binding.tvStatus.text = "下载已取消"
            binding.progressBar.progress = 0
        } else {
            binding.tvStatus1.text = "下载已取消"
            binding.progressBar1.progress = 0
        }
    }

    override fun onDownloadSuccess(downloadTask: DownloadTask, file: File) {
        LogUtils.i("onDownloadSuccess file=" + file.absolutePath)
        if (downloadTask.id == URL_360_ID) {
            binding.tvStatus.text = "下载完成 path：" + file.absolutePath
        } else {
            binding.tvStatus1.text = "下载完成 path：" + file.absolutePath
        }


    }

    override fun onError(downloadTask: DownloadTask, errorCode: Int) {
        LogUtils.i("onError errorCode=$errorCode")
        if (downloadTask.id == url_360) {
            binding.tvStatus.text = "下载失败errorCode=$errorCode"
        } else {
            binding.tvStatus1.text = "下载失败errorCode=$errorCode"
        }
    }

    companion object {

        private val URL_360_ID = "url_360"
        private val URL_QQ_ID = "url_qq"

        fun go(context: Context) {
            val intent = Intent(context, DownLoadActivity::class.java)
            context.startActivity(intent)
        }
    }

}
