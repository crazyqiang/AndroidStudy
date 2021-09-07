package org.ninetripods.mq.study.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.jetpackstudy.mvvm.model.Drink
import org.ninetripods.mq.study.jetpack.repo.DrinkRepository
import org.ninetripods.mq.study.jetpack.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.base.Constants
import org.ninetripods.mq.study.jetpack.base.State

class DrinkViewModel : BaseViewModel() {

    val drinkLiveData = MutableLiveData<List<Drink>>()
    private val drinkRepo = DrinkRepository()

    fun getDrinkInfo() {
        launch(
            {
                val result = drinkRepo.requestDrinkData("")
                when (result?.state) {
                    State.Success -> {
                        drinkLiveData.postValue(result.data)
                    }
                    State.Empty -> {
                        emptyOrErrorLiveData.postValue(Constants.StateEmpty)
                    }
                    State.Error -> {
                        emptyOrErrorLiveData.postValue(Constants.StateError)
                    }
                }
            }
        )
    }
}