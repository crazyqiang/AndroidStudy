package org.ninetripods.mq.study.anim

import android.view.animation.*
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
    private val mBtnStop: Button by id(R.id.btn_stop)
    private val mBtnAnimSet: Button by id(R.id.btn_anim_set)
    private val mToolBar: Toolbar by id(R.id.toolbar)

    override fun getLayoutId(): Int = R.layout.layout_activity_animation

    override fun initViews() {
        initToolBar(mToolBar, "补间动画", true, false, TYPE_BLOG)
    }

    override fun initEvents() {
        mBtnRotate.setOnClickListener { mTvTarget.startAnimation(loadRotationAnim()) }
        mBtnScale.setOnClickListener { mTvTarget.startAnimation(loadScaleAnimation()) }
        mBtnTranslate.setOnClickListener { mTvTarget.startAnimation(loadTranslateAnimation()) }
        mBtnAlpha.setOnClickListener { mTvTarget.startAnimation(loadAlphaAnimation()) }
        mBtnAnimSet.setOnClickListener { mTvTarget.startAnimation(loadAnimSet()) } //组合动画
        mBtnStop.setOnClickListener { mTvTarget.clearAnimation() }
    }


    /**
     * 旋转动画
     */
    private fun loadRotationAnim(): Animation {
        //方式1：代码动态生成
        val rotateAnim: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 2000
            interpolator = LinearInterpolator()
            fillAfter = true //动画结束后，是否停留在动画结束状态
            repeatCount = Animation.INFINITE //重复次数 INFINITE为无限重复
            startOffset = 0L //动画延迟开始时间 ms
            repeatMode =
                Animation.RESTART // RESTART：正序重新开始、REVERSE：倒序重新开始，默认是RESTART。注意：repeatCount(count)设置的count值必须>0或者是INFINITE才会生效
        }
        return rotateAnim
        // 方式2：通过XML创建
        //return AnimationUtils.loadAnimation(this, R.anim.view_rotate)
    }

    /**
     * 缩放动画
     */
    private fun loadScaleAnimation(): Animation {
        //方式1：代码动态生成
        return ScaleAnimation(
            1.0f, 0.5f, 1.0f, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
            fillAfter = true
        }
        // 方式2：通过XML创建
        //return AnimationUtils.loadAnimation(this, R.anim.view_scale)
    }

    /**
     * 平移动画
     */
    private fun loadTranslateAnimation(): Animation {
        //方式1：代码动态生成
        return TranslateAnimation(
            Animation.ABSOLUTE, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.ABSOLUTE, 0f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
            duration = 2000
            fillAfter = true
            interpolator = LinearInterpolator()
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        // 方式2：通过XML创建
        //return AnimationUtils.loadAnimation(this, R.anim.view_translate)
    }

    /**
     * 透明度动画
     */
    private fun loadAlphaAnimation(): Animation {
        //方式1：代码动态生成
        return AlphaAnimation(1.0f, 0f).apply {
            duration = 1000
            fillAfter = true
            interpolator = LinearInterpolator()
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        // 方式2：通过XML创建
        //return AnimationUtils.loadAnimation(this, R.anim.view_alpha)
    }

    /**
     * 动画组合AnimationSet
     * - duration, repeatMode, fillBefore, fillAfter:当在AnimationSet对象上设置这些属性时，将被下推到所有子动画。
     * - repeatCount, fillEnabled:这些属性在AnimationSet中被忽略。
     * - startOffset, shareInterpolator: 这些属性应用于AnimationSet本身
     */
    private fun loadAnimSet(): Animation {
        //方式1：代码动态生成
        val rotateAnim = loadRotationAnim()
        val alphaAnim = loadAlphaAnimation()
        val translateAnim = loadTranslateAnimation()
        val scaleAnim = loadScaleAnimation()

        /**
         * shareInterpolator: 如果想让集合中的所有动画都使用与AnimationSet中
         * 设置的一样的插值器，则传true；反之，如果集合中每个动画都使用自己的插值器，则传false.
         */
        val animSet = AnimationSet(true).apply {
            duration = 3000
            interpolator = LinearInterpolator()
            fillAfter = true
            repeatMode = Animation.REVERSE
            startOffset = 100 //延迟执行动画，应用于AnimationSet本身
        }
        //animSet.cancel() //取消动画
        //animSet.reset() //重置动画
        animSet.addAnimation(rotateAnim)
        animSet.addAnimation(alphaAnim)
        animSet.addAnimation(translateAnim)
        animSet.addAnimation(scaleAnim)
        return animSet
        // 方式2：通过XML创建
        // return AnimationUtils.loadAnimation(this, R.anim.view_animation_set)
    }


}