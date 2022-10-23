package org.ninetripods.mq.study.anim

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * Created by mq on 2022/10/23 下午9:46
 * mqcoder90@gmail.com
 */
class AnimationActivity : BaseActivity() {
    private val mTvTarget: ImageView by id(R.id.iv_target)
    private val mBtnRotate: Button by id(R.id.btn_rotate)
    private val mBtnTranslate: Button by id(R.id.btn_translate)
    private val mBtnAlpha: Button by id(R.id.btn_alpha)
    private val mBtnScale: Button by id(R.id.btn_scale)
    private val mToolBar: Toolbar by id(R.id.toolbar)

    override fun getLayoutId(): Int = R.layout.layout_activity_animation

    override fun initViews() {
        initToolBar(mToolBar, "补间动画", true, false, TYPE_BLOG)
        mTvTarget.animation = loadRotationAnim()
    }


    /**
     * 旋转动画
     */
    private fun loadRotationAnim(): Animation {
        //方式1：代码动态生成
//        val animation: Animation = RotateAnimation(
//                0f,
//                360f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f
//        )
//        animation.duration = 2000
//        animation.repeatCount = Animation.INFINITE
//        animation.interpolator = LinearInterpolator()
//        animation.fillAfter = true
//        return animation
        return AnimationUtils.loadAnimation(this, R.anim.view_rotate)
    }


}