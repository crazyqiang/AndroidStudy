package org.ninetripods.mq.study.jetpack.mvi

import org.ninetripods.mq.study.jetpack.mvi.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvi.base.ISingleUiState
import org.ninetripods.mq.study.jetpack.mvi.base.IUiState
import org.ninetripods.mq.study.jetpack.mvvm.model.RankModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class MViewModel : BaseViewModel<MviState, MviSingleUiState>() {
    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    override fun initUiState(): MviState {
        return MviState(BannerUiState.INIT, DetailUiState.INIT)
    }

    //请求Banner数据
    fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mWanRepo.requestWanData("") },
            successCallback = { data ->
                sendUiState {
                    copy(bannerUiState = BannerUiState.SUCCESS(data))
                }
            },
            failCallback = {}
        )
    }

    //请求List数据
    fun loadDetailData() {
        requestDataWithFlow(
            showLoading = false,
            request = { mWanRepo.requestRankData() },
            successCallback = { data ->
                sendUiState {
                    copy(detailUiState = DetailUiState.SUCCESS(data))
                }
            },
        )
    }

    fun showToast() {
        sendSingleUiState(MviSingleUiState("触发了一次性消费事件！"))
    }
}

/**
 * 定义UiState 将View层所有实体类相关的都包括在这里，可以有效避免模板代码(StateFlow只需要定义一个即可)
 */
data class MviState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState?) : IUiState
data class MviSingleUiState(val message: String) : ISingleUiState

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<WanModel>) : BannerUiState()
}

sealed class DetailUiState {
    object INIT : DetailUiState()
    data class SUCCESS(val detail: RankModel) : DetailUiState()
}