package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DeviceUtil
import com.ashlikun.utils.other.StringUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLEncoder


/**
 * 作者　　: 李坤
 * 创建时间: 2016/8/3 17:19
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：添加一些公共的参数 拦截器
 *
 */
class DefaultInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //        //获取请求
        val oldRequest = chain.request()

        //
        //        // 添加新的参数
        //
        //        //添加真实帐号与密码
        //
        //        String Telphone = "";
        //        String SuccessID = "";
        //        String telphoneSystem = "0";
        //        String urlStr = oldRequest.url().url().toString();
        //        if (urlStr != null && urlStr.contains(HttpManager.BASE_URL)) {
        //            if (UserData.getUserData() != null && UserData.getUserData().isLogin()) {
        //                try {
        //                    Telphone = AESEncrypt.getInstance().encrypt(UserData.userData.getMobile());
        //                    SuccessID = AESEncrypt.getInstance().encrypt(UserData.userData.getPassword());
        //
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //                //添加默认帐号与密码
        //            } else {
        //                try {
        //                    Telphone = AESEncrypt.getInstance().encrypt(Global.DEFAULT_PHONE);
        //                    SuccessID = AESEncrypt.getInstance().encrypt(Global.DEFAULT_PASSWORD);
        //
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //
        //            if (Telphone == null) Telphone = "";
        //            if (SuccessID == null) Telphone = "";
        //
        //            if ("GET".equals(oldRequest.method())) {
        //                HttpUrl url = oldRequest.url().newBuilder()
        //                        .addEncodedQueryParameter("Head_Telphone", Telphone)
        //                        .addEncodedQueryParameter("Head_SuccessID", SuccessID)
        //                        .addEncodedQueryParameter("Head_TelphoneSystem", telphoneSystem)
        //                        .build();
        //                // 新的请求
        //                Request newRequest = oldRequest.newBuilder()
        //                        .url(url)
        //                        .build();
        //                return chain.proceed(newRequest);
        //            }
        //
        //            RequestBody requestBody = oldRequest.body();
        //
        //            if (oldRequest.body() instanceof FormBody) {
        //                FormBody.Builder newFormBody = new FormBody.Builder();
        //                FormBody oidFormBody = (FormBody) oldRequest.body();
        //
        //
        //                for (int i = 0; i < oidFormBody.size(); i++) {
        //                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
        //                }
        //                newFormBody.addEncoded("Head_Telphone", Telphone);
        //                newFormBody.addEncoded("Head_SuccessID", SuccessID);
        //                newFormBody.addEncoded("Head_TelphoneSystem", telphoneSystem);
        //                requestBody = newFormBody.build();
        //            } else if (oldRequest.body() instanceof MultipartBody) {
        //
        //                MultipartBody multipartBody = (MultipartBody) oldRequest.body();
        //                MultipartBody.Builder newMultipartBody = new MultipartBody.Builder(multipartBody.boundary());
        //                for (int i = 0; i < multipartBody.size(); i++) {
        //                    newMultipartBody.addPart(multipartBody.part(i));
        //                }
        //                newMultipartBody.addFormDataPart("Head_Telphone", Telphone);
        //                newMultipartBody.addFormDataPart("Head_SuccessID", SuccessID);
        //                newMultipartBody.addFormDataPart("Head_TelphoneSystem", telphoneSystem);
        //                newMultipartBody.setType(multipartBody.type());
        //                requestBody = newMultipartBody.build();
        //            } else if (oldRequest.body() instanceof RequestBody) {
        //                requestBody = new FormBody.Builder()
        //                        .add("Head_Telphone", Telphone)
        //                        .add("Head_SuccessID", SuccessID)
        //                        .add("Head_TelphoneSystem", telphoneSystem)
        //                        .build();
        //            }
        //            // 新的请求
        //            Request newRequest = oldRequest.newBuilder()
        //                    .method(oldRequest.method(), requestBody)
        //                    .build();
        //            return chain.proceed(newRequest);
        //请求头不能包含中文
        val newRequest = oldRequest.newBuilder()
                .addHeader("userid", RouterManage.login()?.getUserId())
                .addHeader("token", RouterManage.login()?.getToken())
                .addHeader("os", "android  ${DeviceUtil.getSystemVersion()}")
                .addHeader("osVersion", URLEncoder.encode(StringUtils.dataFilter(DeviceUtil.getSystemModel(), DeviceUtil.getDeviceBrand()), "utf-8"))
                .addHeader("devid", DeviceUtil.getDeviceId())
                .addHeader("appVersion", URLEncoder.encode(AppUtils.getVersionName().trim { it <= ' ' }, "utf-8"))
                .build()
        return chain.proceed(newRequest)
    }
    /**
     * 替换某个参数
     *
     *    var request: Request.Builder? = null
    var oldResponse = chain.proceed(chain.request())
    //解析数据
    val httpResponse = HttpResponse()
    httpResponse.setOnGsonErrorData(HttpUtils.getResponseColneBody(oldResponse))
    if (httpResponse.isTokenError()) {
    //token 失效，重新获取
    if (updateToken()) {
    try {
    request = HttpUtils.setRequestParams(chain.request(), "token", RouterManage.login()?.getToken())
    } catch (e: InterruptedException) {
    e.printStackTrace()
    }
    }
    }
    if (request != null) {
    return chain.proceed(request.build())
    }
    return oldResponse
     */
}
