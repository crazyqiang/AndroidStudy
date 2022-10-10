package org.ninetripods.mq.study.jetpack.mvi

import org.ninetripods.mq.study.jetpack.mvi.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvi.base.IEvent
import org.ninetripods.mq.study.jetpack.mvi.base.ISingleUiState
import org.ninetripods.mq.study.jetpack.mvi.base.IUiState
import org.ninetripods.mq.study.jetpack.mvvm.model.RankModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class MViewModel : BaseViewModel<MviState, MviEvent, MviSingleState>() {

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    override fun initUiState(): MviState {
        return MviState(BannerUiState.INIT, DetailUiState.INIT)
    }

    override fun dispatch(event: MviEvent) {
        when (event) {
            is MviEvent.Banner -> loadBannerData()
            is MviEvent.Detail -> loadDetailData()
            //toast是一次性消费事件 使用Channel进行发送
            is MviEvent.Toast ->
                sendSingleUiState(MviSingleState(singleUiState = HomeSingleUiState.ShowToast("我是Toast,一次性消费事件！")))
        }
    }

    //请求Banner数据
    private fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mWanRepo.requestWanData("") },
            successCallback = { data -> sendState { copy(bannerUiState = BannerUiState.SUCCESS(data)) } },
            failCallback = {}
        )
    }

    //请求List数据
    private fun loadDetailData() {
        requestDataWithFlow(
            showLoading = false,
            request = { mWanRepo.requestRankData() },
            successCallback = { data -> sendState { copy(detailUiState = DetailUiState.SUCCESS(data)) } },
        )
    }
}

data class MviState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState?) : IUiState
data class MviSingleState(val singleUiState: HomeSingleUiState) : ISingleUiState

sealed class MviEvent : IEvent {
    object Banner : MviEvent()
    object Detail : MviEvent()
    object Toast : MviEvent()
}

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