package com.ashlikun.baseproject.module.other.view

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.adapter.recyclerview.common.CommonAdapter
import com.ashlikun.baseproject.common.utils.extend.setNegativeButtonX
import com.ashlikun.baseproject.common.utils.extend.setPositiveButtonX
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.other.LogConfig
import com.ashlikun.baseproject.module.other.databinding.OtherActivityTestLogBinding
import com.ashlikun.baseproject.module.other.databinding.OtherItemLogBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.supertoobar.TextAction
import com.ashlikun.utils.other.ClipboardUtils
import kotlinx.coroutines.Job

/**
 * @author　　: 李坤
 * 创建时间: 2022.5.23 13:32
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：测试日志
 */


@Route(path = RouterPath.TEST_LOG)
class TestLogActivity : BaseActivity() {
    override val binding by lazy {
        OtherActivityTestLogBinding.inflate(layoutInflater)
    }
    val text = StringBuffer()
    val adapter by lazy {
        CommonAdapter(requireContext, LogConfig.TestLogService.get().logs,
            binding = OtherItemLogBinding::class.java,
            onItemClick = {
                scrollJob?.cancel()
                scrollJob = null
                isCanScrollTop = false
                AlertDialog.Builder(this)
                    .setMessage(it.getAll())
                    .setPositiveButtonX ()
                    .setNegativeButtonX("复制") { dialog ->
                        ClipboardUtils.copyText(it.getAll())
                    }
                    .setOnDismissListener {
                        scrollJob?.cancel()
                        scrollJob = launch(delayTime = 10000) {
                            isCanScrollTop = true
                        }
                    }
                    .show()
            },
            convert = {
                binding<OtherItemLogBinding> {
                    textView.text = "${it.type}-->${it.content}"
                }
            })
    }
    var scrollJob: Job? = null
    var isCanScrollTop = true

    override fun initView() {
        toolbar?.setBack(this)
        LogConfig.TestLogService.get().call.observeX(this) {
            adapter.notifyDataSetChanged()
            if (isCanScrollTop && !adapter.isEmpty) {
                binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        }

        binding.apply {
            toolbar.addAction(TextAction(toolbar, "筛选").addClickListener { index, action ->
                LogConfig.showDialog()
            }.set())
            refreshLayout.setOnRefreshListener {
                LogConfig.TestLogService.get().clear()
                refreshLayout.isRefreshing = false
            }
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> {
                            scrollJob?.cancel()
                            scrollJob = launch(delayTime = 5000) {
                                isCanScrollTop = true
                            }
                        }
                        RecyclerView.SCROLL_STATE_DRAGGING -> {
                            isCanScrollTop = false
                            scrollJob?.cancel()
                        }
                    }
                }
            })
            recyclerView.layoutManager = LinearLayoutManager(this@TestLogActivity)
            recyclerView.adapter = adapter
            if (!adapter.isEmpty) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        }

    }

}

