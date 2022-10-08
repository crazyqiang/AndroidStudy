package org.ninetripods.mq.study.jetpack.mvi

import org.ninetripods.mq.study.jetpack.mvi.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvi.base.EmptyEffect
import org.ninetripods.mq.study.jetpack.mvi.base.IEvent
import org.ninetripods.mq.study.jetpack.mvi.base.IState
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel

data class ModelA(
    val title: String = "",
    var name: String,
)

class MViewModel : BaseViewModel<MViewModel.State, MViewModel.Event, EmptyEffect>() {

    override fun initState(): State {
        return State(BannerUiState.NONE)
    }

    override fun dispatch(event: Event) {
        when(event){
            Event.Banner -> TODO()
            Event.Detail -> TODO()
        }
    }

    sealed class Event : IEvent {
        object Banner : Event()
        object Detail : Event()
    }

    data class State(val bannerUiState: BannerUiState) : IState

    sealed class BannerUiState {
        object NONE : BannerUiState()
        data class SUCCESS(val models: List<WanModel>) : BannerUiState()
    }

}