package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.gson.GsonHelper
import com.ashlikun.gson.fromJson
import com.ashlikun.mulittypegson.MultiTypeGsonBuilder
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.okhttputils.http.OkHttpManage
import com.ashlikun.okhttputils.http.request.HttpRequest
import com.ashlikun.okhttputils.http.response.IHttpResponse
import com.ashlikun.okhttputils.retrofit.HttpServiceMethod
import com.ashlikun.okhttputils.retrofit.Parse
import com.ashlikun.utils.other.store.getStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 作者　　: 李坤
 * 创建时间: 2021/6/15　20:16
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
/**
 * 从本地存储直接加载成Json 对象
 * 内部空判断
 */
inline fun <reified T> String.getStoreFromJson() =
    this.getStore().let { if (it.isNullOrEmpty()) null else it }?.fromJson<T>(T::class.java)

inline fun <reified T> String.getStoreFromJsonList() =
    this.getStore().let { if (it.isNullOrEmpty()) null else it }?.fromJson<MutableList<T>>(object : TypeToken<List<T>>() {}.type)

/**
 * 简化Parse
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Parse(MultiTypeResult.parseType)
annotation class ParseMulti

/**
 * 多样式的gson
 */
inline fun MultiTypeGsonBuilder.autoRegister(cls: Type, typeName: String = "type") =
    this.registerTypeElementName(typeName)
        .registerTargetParentClass(cls)
        .autoRegisterType()
        .build().create()

/**
 * 实现这个接口自动实现全局多样式
 * 泛型就是主体数据
 */
interface MultiTypeResult<D> : IHttpResponse {
    companion object {
        const val parseType = "MultiTypeResult"
    }

    override fun <T> parseData(gson: Gson, json: String, type: Type, response: Response?): T {
        return GsonHelper.getMultiTypeNotNull()
            .autoRegister(HttpUtils.getType(this::class.java))
            .fromJson(json, type)
    }
}

/**
 * 获取解析数据的Gson,用于多样式
 */
fun HttpRequest.parseGson(it: HttpServiceMethod<*>): HttpRequest {
    val gson = when (it.parseType) {
        MultiTypeResult.parseType -> GsonHelper.getMultiTypeNotNull()
            .autoRegister(getArgType(it.resultType))
        else -> okHttpManage.parseGson
    }
    return parseGson(gson)
}

/**
 * 获取泛型里面的类型
 */
fun getArgType(type: Type): Type {
    return if (type is ParameterizedType && type.actualTypeArguments.isNotEmpty()) {
        getArgType(type.actualTypeArguments[0])
    } else {
        type
    }
}

object GsonUtils {

}