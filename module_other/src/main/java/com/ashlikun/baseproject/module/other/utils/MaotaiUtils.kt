package com.ashlikun.baseproject.module.other.utils

import com.ashlikun.utils.other.coroutines.taskLaunch

/**
 * 作者　　: 李坤
 * 创建时间: 2021/1/28　9:44
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object MaotaiUtils {
    fun start(): Unit {
        taskLaunch {
            while (true) {
                /**
                 * addressId{
                "addressId": 9520718,
                "lastTime": null,
                "selfFlag": 0,
                "source": 1,
                "xSource": 1,
                "creditFlag": 0,
                "vipFlag": 0,
                "coupons": [],
                "billType": "",
                "directFlag": "1",
                "productCode": "20845704",
                "quantity": 1,
                "expectDeliverTimeList": []
                }
                 */
                val map = mapOf<String, Any?>(
                        "addressId" to 9520718,
                        "lastTime" to null,
                        "selfFlag" to 0,
                        "source" to 1,
                        "xSource" to 1,
                        "creditFlag" to 0,
                        "vipFlag" to 0,
                        "billType" to "",
                        "directFlag" to "1",
                        "productCode" to "20845704",
                        "quantity" to "2",
                )
                // /api/orderinfo/create?accessToken=db4206dceaff4339c1c2b30db8c66be9&cache=false&channel=0$timestamp=1611797897217db4206dceaff4339c1c2b30db8c66be9
                //https://mall-oapi.chinafamilymart.com.cn/app/api/orderinfo/create?accessToken=db4206dceaff4339c1c2b30db8c66be9&signature=38398ca80247e7ab99056bfe3914fbdc&timestamp=1611797897217&channel=0&cache=false
                val accessToken = "db4206dceaff4339c1c2b30db8c66be9"
                val timestamp = System.currentTimeMillis()
                val signature = System.currentTimeMillis()
//                val result = HttpRequestParam.create(url = "https://mall-oapi.chinafamilymart.com.cn/app/api/orderinfo/create?accessToken=${accessToken}&signature=${signature}&timestamp=${timestamp}&channel=0&cache=false")
//                        .addHeader("x-tif-paasid", paasid)
//                        .addHeader("x-tif-signature", signature.toUpperCase())
//                        .addHeader("x-tif-timestamp", "$timestamp")
//                        .addHeader("x-tif-nonce", nonce)
//                        .setContentJson(GsonHelper.getBuilderNotNull().create().toJson(body))
//                        .syncExecute<String>(HttpUiHandle[this], String::class.java)


            }
        }
    }
}