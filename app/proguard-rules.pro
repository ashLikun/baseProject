#-keep class com.ll.ui.* 保证包名下的类不被混淆 但是子包名下的类还是会被混淆
#-keep calss com.ll.ui.** 保证包名下的类以及子类都不会被混淆
#以上两种只是保证类名不会被混淆，但是类的内容还是会被混淆
#-keep class com.ll.ui.*{*;} 这样就保证了类以及类的内容不会被混淆
#在此基础上 java的规则也是可以使用，extends，implement
#-keep class * extends Activity 继承了activity的类是不会被混淆的
#保证内部类不会被混淆 使用$这个符号
#-keepclassmembers class com.ll.ui.baseactivity$innerclass{ public *; }这样就保证了baseActivity的内部类innerclass的所有public方法不会被混淆
#再者希望类的全部方法不会被混淆 可以使用如下方法<init>保证类的构造器不会被混淆
#-keep class com.ll.ui.activity { public <init>; }
#当然public 还可以换成是private protect等修饰
#<fields> 匹配所有域不会被混淆
#<methods> 匹配所有函数方法不会被混淆
#这些后面还可以加入参数
#-keep class com.ll.ui.activity { public <init>(com.android.Activity); }表示activity作为参数的构造函数不会被混淆
#-keep 保证类和类的成员都不会被混淆
#-keepclassmembers 保证成员不会被混淆但是类是会被混淆
#-keepclasswithmembers 如果类拥有了某成员 保证类和成员不会被混淆

#--------------------------------------------基本指令 start------------------------------------------------#
#根据https://www.zhihu.com/question/46649910,进行修改,解决问题:warning: Ignoring InnerClasses attribute for an anonymous inner class
-keepattributes EnclosingMethod

#指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #预校验
-dontpreverify

 #混淆时是否记录日志
-verbose


 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#--------------------------------------------基本指令 end------------------------------------------------#

#--------------------------------------------默认保留区 start--------------------------------------------------#
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep interface android.support.** { *; }
-dontwarn android.support.**
-dontwarn android.os.**
#继承Binder类不混淆
-keep public class * extends android.os.Binder

#保持 native 方法不被混淆
-verbose
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#自定义View不混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class **.R$* {*;}

-keepclassmembers class * {
    void *(**On*Event);
}



#枚举不被混淆
-keepclassmembers enum * {*;}
# Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#排除所有注解类
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }
-keepattributes SourceFile,LineNumberTable

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

#--------------------------------------------默认保留区 end--------------------------------------------------#


# 保持混淆时类的实名及行号(——————— 调试时打开 ———————)
#-keepattributes SourceFile,LineNumberTable


#---------------------------------webview------------------------------------
#保护注解
-keepattributes *Annotation*

# webview和js
-keepattributes *JavascriptInterface*

-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------

#-----------------------------------------定制区-------------------------------------------#
#-----------------------------------------1:实体类 start-------------------------------------------#

-keep class com.ashlikun.common.mode.javabean.** { *; }
-keep class com.ashlikun.yoohfit.libcore.javabean.** { *; }
-keep class com.ashlikun.yoohfit.module.login.mode.javabean.** { *; }
-keep class com.ashlikun.yoohfit.module.main.mode.javabean.** { *; }
-keep class com.ashlikun.yoohfit.module.other.mode.javabean.** { *; }


#-----------------------------------------1:实体类 end-------------------------------------------#


#-----------------------------------------2:第三方库 start-------------------------------------------#
#okhttp
-keep class okhttp3.** {*; }
-keep class okio.**{*;}
-dontwarn com.squareup.okhttp3.**
-dontwarn okio.**
-keepattributes Signature
-keepattributes *Annotation*
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#retrofit
-dontwarn rx.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-keep class sun.misc.Unsafe { *; }
-dontwarn java.lang.invoke.*

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

#  RxJava:
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
#alibaba的路由模式
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# 使用 @Autowired 的字段不混淆
-keepnames class * {
    @com.alibaba.android.arouter.facade.annotation.Autowired <fields>;
}
# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider
#EventBus3.0
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.ashlikun.eventbus.Subscribe <methods>;
}
-keep enum com.ashlikun.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
#jar包
#-libraryjars log4j-1.2.17.jar
#-dontwarn org.apache.log4j.**
#-keep class  org.apache.log4j.** { *;}

#高德地图
#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#搜索
-keep   class com.amap.api.services.**{*;}
#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

#友盟统计
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.superrtc.** {*;}
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.**
#EasyPay
-dontwarn com.ashlikun.easypay.**
#微信
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

#-----------------------------------------2:第三方库 end-------------------------------------------#


#-----------------------------------------3:与js互相调用的类 start-------------------------------------------#
#-keepclasseswithmembers class com.demo.login.bean.ui.MainActivity$JSInterface {
#      <methods>;
#}
#-----------------------------------------3:与js互相调用的类 end-------------------------------------------#


#-----------------------------------------4:反射相关的类和方法 start-------------------------------------------#
#保证CommonAdapter的footerSize和headerSize字段不被混肴
#某一变量不混淆
-keepclasseswithmembers class com.ashlikun.adapter.recyclerview.BaseAdapter {
    private int footerSize;
    private int  headerSize;
}
#-keep public class * extends com.ashlikun.adapter.recyclerview.BaseAdapter {*;}
#-----------------------------------------4:反射相关的类和方法 end-------------------------------------------#



#-----------------------------------------5:警告处理 start-------------------------------------------#
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.lang.model.element.Element
#-----------------------------------------5:警告处理 end-------------------------------------------#