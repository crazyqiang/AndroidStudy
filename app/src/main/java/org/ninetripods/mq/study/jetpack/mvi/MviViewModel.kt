package org.ninetripods.mq.study.jetpack.mvi

import org.ninetripods.mq.study.jetpack.mvi.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvi.base.ISingleUiState
import org.ninetripods.mq.study.jetpack.mvi.base.IUiState
import org.ninetripods.mq.study.jetpack.mvvm.model.RankModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class MViewModel : BaseViewModel<MviState, MviSingleState>() {

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
            successCallback = { data -> sendState { copy(bannerUiState = BannerUiState.SUCCESS(data)) } },
            failCallback = {}
        )
    }

    //请求List数据
    fun loadDetailData() {
        requestDataWithFlow(
            showLoading = false,
            request = { mWanRepo.requestRankData() },
            successCallback = { data -> sendState { copy(detailUiState = DetailUiState.SUCCESS(data)) } },
        )
    }
}

data class MviState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState?) : IUiState
data class MviSingleState(val singleUiState: HomeSingleUiState) : ISingleUiState

sealed class HomeSingleUiState {
    data class ShowToast(val message: String) : HomeSingleUiState()
}

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<WanModel>) : BannerUiState()
}

sealed class DetailUiState {
    object INIT : DetailUiState()
    data class SUCCESS(val detail: RankModel) : DetailUiState()
}