package org.ninetripods.mq.study.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * 包含Fragment的通用Activity
 */
class CommonFragmentsActivity : BaseActivity() {

    companion object {
        const val KEY_CLZ_NAME = "key_clz_name"
        const val KEY_TITLE_NAME = "key_title_name"
        fun start(context: Context, clzName: String, name: String) {
            val intent = Intent(context, CommonFragmentsActivity::class.java).apply {
                putExtra(KEY_CLZ_NAME, clzName)
                putExtra(KEY_TITLE_NAME, name)
            }
            context.startActivity(intent)
        }
    }

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mTitleName = ""
    private var mClzName = ""

    override fun setContentView() {
        setContentView(R.layout.activity_xfermode_demo)
    }

    override fun initViews() {
        mClzName = intent.getStringExtra(KEY_CLZ_NAME) ?: ""
        mTitleName = intent.getStringExtra(KEY_TITLE_NAME) ?: ""
        initToolBar(mToolBar, mTitleName, true, false)
        val fragment = createFragmentByClassName(mClzName)
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_container, it)
                .commitAllowingStateLoss()
        }
    }

    private fun createFragmentByClassName(className: String): Fragment? {
        return try {
            val clazz = Class.forName(className) // 通过类名获取 Class
            clazz.newInstance() as? Fragment    // 反射创建实例并转换为 Fragment
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}