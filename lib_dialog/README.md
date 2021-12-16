
**NOTE**: dialog库已迁移到maven仓库，引入方式：
```
implementation 'io.github.mqcodedev:lib_dialog:1.3.0'
```
别忘了在根目录下的build.gradle中声明:
```
    repositories {
        mavenCentral()
        //......其他......
    }
```
现在新建项目时默认会自动引入。

**老版本使用Jcenter引入方式**：
~~implementation 'com.ninetripods:lib-dialog:1.1.0'~~
**未来Jcenter不允许更新版本，墙裂建议使用maven方式引入**

- **Version 1.3.0**
1、dialog中的依赖迁移至AndroidX，如果你的项目还没迁移到AndroidX，那么直接使用该版本会报错，请确保你的项目已经迁移至AndroidX
2、Dialog使用中传入的Activity必须为FragmentActivity及其子类
3、修复dialog库在Android11系统全屏弹窗展示不全问题
- **Version 1.2.0**(maven)
 1、从该版本开始，dialog库迁移至maven仓库，Jcenter仓库不再维护~
- **Version 1.1.0** (Jcenter)
1、优化dialog默认布局
2、修复Activity横竖屏切换导致空指针问题
3、优化DialogFragment在Activity onSaveInstanceState()之后调用导致的问题

更详细移步：[Android基于DialogFragment封装一个通用的Dialog](https://blog.csdn.net/u013700502/article/details/82777402)