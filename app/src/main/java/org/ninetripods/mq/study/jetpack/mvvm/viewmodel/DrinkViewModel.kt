package org.ninetripods.mq.study.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.jetpackstudy.mvvm.model.Drink
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.base.State
import org.ninetripods.mq.study.jetpack.mvvm.repo.DrinkRepository

class DrinkViewModel : BaseViewModel() {

    //LiveData
    val drinkLiveData = MutableLiveData<List<Drink>>()

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val drinkRepo = DrinkRepository()

    fun getDrinkInfo(drinkId: String = "") {
        launchRequest {
            val result = drinkRepo.requestDrinkData(drinkId)
            when (result.state) {
                State.Success -> drinkLiveData.postValue(result.data)
                State.Error -> errorLiveData.postValue(result.msg)
            }
        }
    }
}