package com.ashlikun.baseproject.module.main.viewmodel

import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.utils.other.logge


class HomeViewModel : BaseViewModel() {

    override fun onCreate() {
        super.onCreate()
        "HomeViewModel onCreate".logge()
    }
}
