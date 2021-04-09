package com.ashlikun.baseproject.module.other.view

/**
 * 作者　　: 李坤
 * 创建时间: 2021/4/7　15:47
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
fun main(args: Array<String>) {
    // 创建指定大小的数组
    val aaa = Array(2) { i -> {} }
    for (index in 0..1) {
        aaa[index] = fun() {
            println(index)
        }
    }
    aaa[0]()
    aaa[1]()
}