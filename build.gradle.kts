// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    apply(from = "./config.gradle.kts")
}

plugins {
    //kotlin和ksp 版本需要和 config.gradle.kts一致
    // 7.0 kotlin/Metadata 被去除，导致混淆后反射无法获取接口方法参数名称，retrofit就不能代理，注意，等7.1+就可以了
    // 7.4.2 总是在这个项目莫名报 Unresolved reference *** ，都是一些扩展方法
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    // id("com.android.library") version "8.0.2" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    // 滴滴开发助手插件,更新三方库老瞎几把报类冲突
    // id("io.github.didi.dokit:dokitx-plugin") version extra["dokit_version"] as String apply false
}


//下面代码放在app列出所有包含有so文件的库信息  执行assembleDebug
//tasks.whenTaskAdded { task ->
//    if (task.name == 'mergeDebugNativeLibs') {
//        task.doFirst {
//            println("------------------- find so files start -------------------")
//
//            // 使用非递归栈遍历代替递归方法
//            def stack = new Stack<File>()
//            inputs.files.files.each { stack.push(it) }
//
//            while (!stack.isEmpty()) {
//                def file = stack.pop()
//                if (file.isDirectory()) {
//                    file.listFiles()?.each { stack.push(it) }
//                } else if (file.path.endsWith(".so")) {
//                    println "find so file: $file.absolutePath"
//                }
//            }
//
//            println("------------------- find so files end -------------------")
//        }
//    }
//}
