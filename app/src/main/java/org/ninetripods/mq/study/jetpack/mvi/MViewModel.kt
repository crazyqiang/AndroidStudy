package org.ninetripods.mq.study.jetpack.mvi

import org.ninetripods.mq.study.jetpack.mvi.base.*
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class MViewModel : BaseViewModel<MviState, MviEvent, EmptyEffect>() {

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    override fun initState(): MviState {
        return MviState(BannerUiState.NONE, DetailUiState.NONE)
    }

    override fun dispatch(event: MviEvent) {
        when (event) {
            is MviEvent.Banner -> loadBannerData()
            is MviEvent.Detail -> loadDetailData()
        }
    }

    private fun loadBannerData() {
        requestDataWithFlow(
            showLoading = true,
            request = { mWanRepo.requestWanData("") },
            sucFunction = { sendState { copy(bannerUiState = BannerUiState.SUCCESS(it)) } },
        )
    }

    private fun loadDetailData() {}


}

sealed class MviEvent : IEvent {
    object Banner : MviEvent()
    object Detail : MviEvent()
}

data class MviState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState?) : IState
data class MviEffect(val singleEffect: SingleEffect) : IEffect

sealed class SingleEffect {

}

sealed class BannerUiState {
    object NONE : BannerUiState()
    data class SUCCESS(val models: List<WanModel>) : BannerUiState()
}

sealed class DetailUiState {
    object NONE : DetailUiState()
    data class SUCCESS(val details: List<WanModel>) : DetailUiState()
}