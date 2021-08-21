import LibsVersion.Companion.appcompat_version
import LibsVersion.Companion.arouter_api_version
import LibsVersion.Companion.arouter_compiler_version
import LibsVersion.Companion.constraintlayout_version
import LibsVersion.Companion.kotlin_version
import LibsVersion.Companion.material_version
import LibsVersion.Companion.recyclerview_version

/**
 * Created by mq on 2021/8/22
 * mqcoder90@gmail.com
 */
object App {
    const val targetSdkVersion = 30
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.0"
    const val minSdkVersion = 19
    const val appId = "org.ninetripods.mq.study"
    const val versionCode = 20210506
    const val versionName = "1.3.0"
}

object Deps {
    //Kotlin
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

    //AndroidX相关
    const val androidx_material = "com.google.android.material:material:$material_version"
    const val androidx_appcompat = "androidx.appcompat:appcompat:$appcompat_version"
    const val androidx_recyclerView = "androidx.recyclerview:recyclerview:$recyclerview_version"
    const val androidx_constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintlayout_version"

    //ARouter
    const val arouter_api = "com.alibaba:arouter-api:$arouter_api_version"
    const val arouter_compiler = "com.alibaba:arouter-compiler:$arouter_compiler_version"
}

internal class LibsVersion {

    companion object {
        const val kotlin_version = "1.4.32"
        const val arouter_api_version = "1.5.1"
        const val arouter_compiler_version = "1.5.1"
        const val material_version = "1.2.1"
        const val appcompat_version = "1.3.0"
        const val recyclerview_version = "1.1.0"
        const val constraintlayout_version = "1.1.3"
    }
}