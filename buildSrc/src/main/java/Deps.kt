import LibsVersion.Companion.appcompat_version
import LibsVersion.Companion.arouter_api_version
import LibsVersion.Companion.arouter_compiler_version
import LibsVersion.Companion.constraintlayout_version
import LibsVersion.Companion.coroutines_version
import LibsVersion.Companion.jetpack_version
import LibsVersion.Companion.kotlin_version
import LibsVersion.Companion.legacy_version
import LibsVersion.Companion.material_version
import LibsVersion.Companion.okhttp_version
import LibsVersion.Companion.recyclerview_version
import LibsVersion.Companion.retrofit_version

/**
 * Created by mq on 2021/8/22
 * mqcoder90@gmail.com
 */
object App {
    const val targetSdkVersion = 30
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 19
    const val appId = "org.ninetripods.mq.study"
    const val versionCode = 20210506
    const val versionName = "1.3.0"
}

object Deps {
    //Kotlin
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    //引入协程
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    //ktx扩展列表：https://developer.android.com/kotlin/ktx/extensions-list
    //Jetpack相关
    const val jetpack_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$jetpack_version"
    const val jetpack_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$jetpack_version"
    //将 Kotlin 协程与生命周期感知型组件一起使用: https://developer.android.com/topic/libraries/architecture/coroutines
    //LifecycleScope协程
    const val jetpack_lifecycle_runtime =  "androidx.lifecycle:lifecycle-runtime-ktx:$jetpack_version"
    //ProcessLifecycleOwner给整个 app进程 提供一个lifecycle
    const val jetpack_lifecycle_process = "androidx.lifecycle:lifecycle-process:$jetpack_version"
    //帮助实现Service的LifecycleOwner
    const val jetpack_lifecycle_service = "androidx.lifecycle:lifecycle-service:$jetpack_version"

    //AndroidX相关
    const val androidx_material = "com.google.android.material:material:$material_version"
    const val androidx_appcompat = "androidx.appcompat:appcompat:$appcompat_version"
    const val androidx_recyclerView = "androidx.recyclerview:recyclerview:$recyclerview_version"
    const val androidx_constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
    const val androidx_legacy = "androidx.legacy:legacy-support-v4:$legacy_version"

    //ARouter
    const val arouter_api = "com.alibaba:arouter-api:$arouter_api_version"
    const val arouter_compiler = "com.alibaba:arouter-compiler:$arouter_compiler_version"
    //okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttp_version"
    const val okhttp_log_interceptor = "com.squareup.okhttp3:logging-interceptor:$okhttp_version"
    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
}

internal class LibsVersion {

    companion object {
        const val okhttp_version = "4.9.0"
        const val retrofit_version = "2.9.0"
        const val legacy_version = "1.0.0"
        const val jetpack_version = "2.2.0"
        const val kotlin_version = "1.4.32"
        const val coroutines_version = "1.4.3"
        const val arouter_api_version = "1.5.1"
        const val arouter_compiler_version = "1.5.1"
        const val material_version = "1.2.1"
        const val appcompat_version = "1.3.0"
        const val recyclerview_version = "1.1.0"
        const val constraintlayout_version = "1.1.3"
    }
}