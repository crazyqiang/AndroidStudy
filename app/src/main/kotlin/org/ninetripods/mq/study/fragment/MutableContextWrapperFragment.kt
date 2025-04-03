package org.ninetripods.mq.study.fragment

import android.content.Context
import android.content.ContextWrapper
import android.content.MutableContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import java.util.Locale

/**
 * MutableContextWrapper示例
 */
class MutableContextWrapperFragment : BaseFragment() {
    private val mContainer1: FrameLayout by id(R.id.fl_container1)
    private val mContainer2: FrameLayout by id(R.id.fl_container2)

    override fun getLayoutId(): Int {
        return R.layout.layout_mutable_context_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logConfiguration() //log

        val context = requireContext()
        addChild(context, mContainer1)
//        val wrapper = MutableContextWrapper(context).apply {
//            val configuration = Configuration().apply {
//                fontScale = 1.0f
//            }
//            resources.updateConfiguration(configuration, resources.displayMetrics)
//        }
        val configuration = Configuration().apply {
            fontScale = 1.0f
        }
        val wrapper = ContextWrapper(context).createConfigurationContext(configuration)
        addChild(wrapper, mContainer2)
    }

    private fun addChild(context: Context, parentView: ViewGroup) {
        LayoutInflater.from(context).inflate(R.layout.layout_child_button, parentView)
    }

    /**
     * 小米11手机执行结果如下：
     *
     */
    private fun logConfiguration() {
        val sBuilder = StringBuilder()
        val cfg = resources.configuration
        sBuilder.append("densityDpi:" + cfg.densityDpi + "\n") //屏幕的像素密度（DPI），即每英寸像素数 一些常见的DPI级别： ldpi（120dpi）、mdpi（160dpi）、hdpi（240dpi）、xhdpi（320dpi）、xxhdpi（480dpi）、xxxhdpi（640dpi）
        sBuilder.append("fontScale:" + cfg.fontScale + "\n") //字体缩放因子
        sBuilder.append("hardKeyboardHidden:" + cfg.hardKeyboardHidden + "\n") //物理键盘是否隐藏
        sBuilder.append("keyboard:" + cfg.keyboard + "\n") //设备的键盘类型 如：KEYBOARD_NOKEYS (0)：无键盘、KEYBOARD_NOKEYS (1)、KEYBOARD_QWERTY (2)：物理QWERTY键盘、KEYBOARD_12KEY (3)：12键键盘（传统手机）
        sBuilder.append("keyboardHidden:" + cfg.keyboardHidden + "\n")//设备的键盘是否隐藏
        sBuilder.append("locale:" + cfg.locale + "\n") //当前系统的语言和地区 如：zh_CN（中国简体）、zh_TW（台湾繁体）、en_US（美国英语）、fr_FR（法国法语）
        sBuilder.append("mcc:" + cfg.mcc + "\n") //移动网络运营商的国家代码 如：460 → 中国、310 → 美国、440 → 日本
        sBuilder.append("mnc:" + cfg.mnc + "\n") //运营商代码 如：11 代表中国电信、01 代表中国联通、00 代表中国移动
        sBuilder.append("navigation:" + cfg.navigation + "\n") //设备的导航方式
        sBuilder.append("navigationHidden:" + cfg.navigationHidden + "\n") //导航方式是否隐藏
        sBuilder.append("orientation:" + cfg.orientation + "\n") //屏幕方向
        sBuilder.append("screenHeightDp:" + cfg.screenHeightDp + "\n") //屏幕宽度（单位：dp）
        sBuilder.append("screenWidthDp:" + cfg.screenWidthDp + "\n") //屏幕高度（单位：dp，设备独立像素）
        sBuilder.append("screenLayout:" + cfg.screenLayout + "\n") //屏幕的布局类型（大小、方向、密度）
        sBuilder.append("smallestScreenWidthDp:" + cfg.smallestScreenWidthDp + "\n") //设备支持的最小屏幕宽度（dp），用于平板适配 通常平板的smallestScreenWidthDp ≥ 600。
        sBuilder.append("touchscreen:" + cfg.touchscreen + "\n")
        sBuilder.append("uiMode:" + cfg.uiMode + "\n") //设备的 UI 模式，如夜间模式、车载模式、电视模式等
        log("Configuration: $sBuilder")
    }
}

