//plugins {
//    `kotlin-dsl`
//}
//
//repositories {
//    // The org.jetbrains.kotlin.jvm plugin requires a repository
//    // where to download the Kotlin compiler dependencies from.
//    mavenCentral()
//    gradlePluginPortal()
//}
//
//dependencies {
//    implementation(kotlin("stdlib-jdk8"))
//    compileOnly(gradleApi())
//    compileOnly(localGroovy())
//    //版本不能太高 否则会跟项目的as版本冲突
//    compileOnly("com.android.tools.build:gradle:4.1.3")
//}