package com.ashlikun.baseproject.module.main.view.fragment

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.module.main.databinding.MainFragmentHomeBinding
import com.ashlikun.baseproject.module.main.viewmodel.HomeViewModel
import com.ashlikun.core.mvvm.BaseMvvmFragment
import com.ashlikun.core.mvvm.IViewModel
import com.ashlikun.core.mvvm.launch
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.coroutines.DefaultScope
import com.ashlikun.utils.other.coroutines.MainDispatcher
import com.ashlikun.utils.other.coroutines.MainScopeX
import com.ashlikun.utils.other.coroutines.cancelX
import com.ashlikun.utils.other.coroutines.currentScope
import com.ashlikun.utils.other.coroutines.defaultCoroutineExceptionHandler
import com.ashlikun.utils.other.coroutines.launchX
import com.ashlikun.utils.other.coroutines.launchXCache
import com.ashlikun.utils.other.coroutines.taskLaunch
import com.ashlikun.utils.other.coroutines.withContextIO
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　21:17
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
@IViewModel(HomeViewModel::class)
@Route(path = RouterPath.FRAGMENT_HOME)
class HomeFragment : BaseMvvmFragment<HomeViewModel>() {
    override val binding by lazy {
        MainFragmentHomeBinding.inflate(layoutInflater)
    }
    private val scope = MainScope()
    private val scope2 = CoroutineScope(SupervisorJob() + MainDispatcher + defaultCoroutineExceptionHandler)
    val RESURL2 = listOf(
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4135477902,3355939884&fm=26&gp=0.jpg",
        "http://img1.cache.netease.com/catchpic/A/A0/A0153E1AEDA115EAE7061A0C7EBB69D2.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584594344314&di=4eb56ec22f47949d7c36069f55403e5c&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2Fe573f475a02c84bf35a44be7cff56307-0.jpg",
        "http://uploadfile.bizhizu.cn/up/03/50/95/0350955b21a20b6deceea4914b1cfeeb.jpg.source.jpg",
        "http://pic1.win4000.com/wallpaper/7/5860842b353da.jpg",
        "http://pic1.win4000.com/wallpaper/b/566a37b05aac3.jpg"
    )

    private fun changeUi(flag: Int) {
        val startTime = System.currentTimeMillis()
        scope2.launch {
            Log.e("HomeFragment", "changeUi($flag): launch time = ${System.currentTimeMillis() - startTime}")
            timeConsuming(100)
            binding.ceshi2Button.text = "${System.currentTimeMillis()}-From changeUi $flag"
        }
    }

    private fun task(delay: Int) {
        val startTime = System.currentTimeMillis()
        scope2.launch {
            Log.e("HomeFragment", "task: launch($delay) time = ${System.currentTimeMillis() - startTime}")
            timeConsuming(delay)
            binding.ceshi2Button.text = "${System.currentTimeMillis()}-From task($delay)"
        }
    }

    private suspend fun timeConsuming(times: Int) {
        withContextIO {
            val file = File(CacheUtils.appCachePath, "test.txt")
            if (!file.exists()) {
                file.createNewFile()
            }

            repeat(times * 100) {
                file.appendText("${System.currentTimeMillis()} - balabala - ${it} \n")
            }
        }
    }

    override fun initView() {
        binding.apply {
            ceshiButton.setOnClickListener {

                taskLaunch(cache = {
                    LogUtils.e(" 异常${it}")
                }) {
//                    currentScope {
//                        it.launchX(cache = {
//                            LogUtils.e("任务1 异常${it}")
//                        }) {
//                            delay(2000)
//                            throw RuntimeException()
//                        }
//                    }

                    //coroutineScope launch 的时候 必须 SupervisorJob() 解决异常捕获

//                    currentScope {  }
//                    coroutineScope {
//                        val j1 = asyncX {
//                            LogUtils.e("任务1 开始")
//                            delay(3000)
//                            LogUtils.e("任务1 结束")
//                        }
//                        val j2 = asyncX {
//                            LogUtils.e("任务2 开始")
//                            delay(4000)
//                            LogUtils.e("任务2 结束")
//                        }
//                        val j3 = asyncX {
//                            LogUtils.e("任务3 开始")
//                            delay(5000)
//                            LogUtils.e("任务3 结束")
//                        }
//                        listOf(j1, j2, j3).forEach { it.await() }
//                    }


                }

//                RouterJump.startLockImage(0, RESURL2.map { ImageData(it, it, 0) } as ArrayList, true)
                val mainScope = DefaultScope()
                mainScope.launch(CoroutineExceptionHandler { _, t ->
                    LogUtils.e("任务异常 ${it}")
                }) {
                    mainScope.launch(CoroutineExceptionHandler { _, t ->
                        LogUtils.e("任务 1 异常 ${it}")
                    }) {
                        LogUtils.e("任务 1 开始")
                        delay(4000)
                        LogUtils.e("任务 1 结束")
                    }
                    mainScope.launch(CoroutineExceptionHandler { _, t ->
                        LogUtils.e("任务 2  异常 ${it}")
                    }) {
                        LogUtils.e("任务 2 开始")
                        delay(4000)
                        LogUtils.e("任务 2 结束")
                    }
                    mainScope.launch(CoroutineExceptionHandler { _, t ->
                        LogUtils.e("任务 3 异常 ${it}")
                    }) {
                        LogUtils.e("任务 3 开始")
                        delay(4000)
                        LogUtils.e("任务 3 结束")
                    }

                    delay(5000)
                    LogUtils.e("qqqqqqqqqqqqqq2")

                }
                launch(delayTime = 3000) {
                    mainScope.cancelX(CancellationException("dddddddd"))
                }
//                //协程异常测试
//                LogUtils.e("协程异常测试")
//                val scope = DefaultScope()
//                scope.launchX(CoroutineExceptionHandler { _, _ ->
//                    LogUtils.e("aaaaaaaaaa 异常")
//                }) {
////                    launchSuspend(SupervisorJob(),cache = {
////                        LogUtils.e("FFFFF 异常")
////                    }) {
////                        delay(100)
////                        throw RuntimeException()
////                        LogUtils.e("FFFFFFFFF")
////                    }
//                    asyncSuspend(cache = {
//                        LogUtils.e("CCCCC 异常")
//                    }) {
//                        delay(100)
//                        throw RuntimeException()
//
//                    }
//                    val aa = SupervisorJob() + EmptyCoroutineContext
//
//                    scope.launchX(CoroutineExceptionHandler { _, _ ->
//                        LogUtils.e("bbbbbbbb 异常")
//                    }) {
//                        delay(100)
////                        throw RuntimeException()
//                        LogUtils.e("bbbbbbbb")
//                    }
//                }

//                task(2000)
//                task(5000)
//                task(1000)
//                task(500)
//
//                changeUi(0)
//
//                task(2000)
//                task(5000)
//                task(1000)
//                task(500)
//
//                changeUi(1)
            }
            ceshi2Button.setOnClickListener {
                RouterJump.startTest()
            }
        }

    }


    override fun clearData() {}


    override fun onRetryClick(data: ContextData) {}


    override fun onEmptyClick(data: ContextData) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
