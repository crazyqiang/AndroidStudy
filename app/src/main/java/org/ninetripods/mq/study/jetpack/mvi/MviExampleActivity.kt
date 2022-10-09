package org.ninetripods.mq.study.jetpack.mvi

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvi.base.BaseMviActivity
import org.ninetripods.mq.study.kotlin.ktx.flowWithLifecycle2
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * MVI示例
 */
class MviExampleActivity : BaseMviActivity() {

    private val mTvContent: TextView by id(R.id.tv_content)
    private val mBtnQuest: Button by id(R.id.btn_request)
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mContentView: ViewGroup by id(R.id.cl_content_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan_android)
        initToolBar(mToolBar, "Jetpack MVI", true, true, BaseActivity.TYPE_BLOG)
        registerEvent()
        mBtnQuest.setOnClickListener {
            //请求数据
            mViewModel.dispatch(MviEvent.Banner)
            mViewModel.dispatch(MviEvent.Detail)
        }
    }

    private fun registerEvent() {
        mViewModel.viewState.flowWithLifecycle2(this,
            prop1 = MviState::bannerUiState) { state ->
            when (state) {
                is BannerUiState.NONE -> {}
                is BannerUiState.SUCCESS -> {
                    val list = state.models
                    val builder = StringBuilder()
                    for (index in list.indices) {
                        //每条数据进行折行显示
                        if (index != list.size - 1) {
                            builder.append(list[index])
                            builder.append("\n\n")
                        } else {
                            builder.append(list[index])
                        }
                    }
                    mTvContent.text = builder.toString()
                }
            }

        }

        mViewModel.viewState.flowWithLifecycle2(this,
            prop1 = MviState::detailUiState) { state ->
            when (state) {
                is DetailUiState.NONE -> {}
                is DetailUiState.SUCCESS -> {}
            }

        }
    }

}