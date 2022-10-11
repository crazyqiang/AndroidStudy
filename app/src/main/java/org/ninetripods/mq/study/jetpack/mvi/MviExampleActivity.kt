package org.ninetripods.mq.study.jetpack.mvi

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvi.base.BaseMviActivity
import org.ninetripods.mq.study.jetpack.mvi.base.LoadUiState
import org.ninetripods.mq.study.jetpack.mvi.widget.RankAdapter
import org.ninetripods.mq.study.kotlin.ktx.flowWithLifecycle2
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * MVI示例
 */
class MviExampleActivity : BaseMviActivity() {

    private val mBtnQuest: Button by id(R.id.btn_request)
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mContentView: ViewGroup by id(R.id.cl_content_view)
    private val mViewPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mRvRank: RecyclerView by id(R.id.rv_view)

    private val mViewModel: MViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_wan_android_mvi
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack MVI", true, true, BaseActivity.TYPE_BLOG)
        mRvRank.layoutManager = GridLayoutManager(this, 2)
    }

    override fun initEvents() {
        registerEvent()
        mBtnQuest.setOnClickListener {
            mViewModel.loadBannerData()
            mViewModel.loadDetailData()
        }
    }

    private fun registerEvent() {
        /**
         * 一次性消费事件
         */
        mViewModel.loadUiStateFlow.flowWithLifecycle2(this) { state ->
            when (state) {
                is LoadUiState.Error -> mStatusViewUtil.showErrorView(state.msg)
                is LoadUiState.ShowMainView -> mStatusViewUtil.showMainView()
                is LoadUiState.Loading -> mStatusViewUtil.showLoadingView(state.isShow)
            }
        }
        mViewModel.uiStateFlow.flowWithLifecycle2(this, prop1 = MviState::bannerUiState) { state ->
            when (state) {
                is BannerUiState.INIT -> {}
                is BannerUiState.SUCCESS -> {
                    mViewPager2.visibility = View.VISIBLE
                    mBtnQuest.visibility = View.GONE
                    val imgs = mutableListOf<String>()
                    for (model in state.models) {
                        imgs.add(model.imagePath)
                    }
                    mViewPager2.setIndicatorShow(true).setModels(imgs).start()
                }
            }

        }

        mViewModel.uiStateFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED,
            prop1 = MviState::detailUiState) { state ->
            when (state) {
                is DetailUiState.INIT -> {}
                is DetailUiState.SUCCESS -> {
                    mRvRank.visibility = View.VISIBLE
                    val list = state.detail.datas
                    mRvRank.adapter = RankAdapter().apply { setModels(list) }
                }
            }

        }
    }

    override fun retryRequest() {
        //点击屏幕重试
        mViewModel.loadBannerData()
        mViewModel.loadDetailData()
    }

    /**
     * 展示Loading、Empty、Error视图等
     */
    override fun getStatusOwnerView(): View? {
        return mContentView
    }

}