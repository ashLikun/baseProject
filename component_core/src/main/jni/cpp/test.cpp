//
// Created by likun on 2020/6/28.
//

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <jni.h>

using namespace std;

#ifdef __cplusplus
extern "C" {
#endif
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "JNI", __VA_ARGS__)
static const char *className = "com/ashlikun/baseproject/libcore/utils.jni/JNIDemo1";
JNIEXPORT void JNICALL
Java_com_ashlikun_baseproject_libcore_utils_jni_JniDemo1_nativeMethod(JNIEnv *env, jobject clazz) {
    LOGD("native: say hello ###");
}

#ifdef __cplusplus
}
#endif
