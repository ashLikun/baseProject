package com.ashlikun.libcore.utils.http;

import com.ashlikun.libcore.javabean.UserData;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 作者　　: 李坤
 * 创建时间: 2016/8/3 17:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：添加一些公共的参数 拦截器
 */
public class MarvelSigningInterceptor implements Interceptor {


    public MarvelSigningInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        //获取请求
        Request oldRequest = chain.request();

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

        //   新的请求
        String token = "";
        if (UserData.getUserData() == null) {
            UserData.getDbUserData();
        }
        if (UserData.getUserData() != null) {
            token = UserData.getUserData().getToken();
        }
        Request newRequest = oldRequest.newBuilder()
                .addHeader("token", token)
                .build();
        return chain.proceed(newRequest);
    }
}
