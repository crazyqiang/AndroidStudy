package org.ninetripods.mq.study.jetpack.mvvm.repo

import com.example.jetpackstudy.mvvm.model.Drink
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseData
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseRepository
import org.ninetripods.mq.study.jetpack.mvvm.base.http.RetrofitManager
import org.ninetripods.mq.study.jetpack.mvvm.base.http.api.DrinkService

class DrinkRepository : BaseRepository() {

    suspend fun requestDrinkData(drinkId: String): BaseData<List<Drink>> {
        val service = RetrofitManager.getService(DrinkService::class.java)
        return executeRequest {
            service.getBanner()
        }
    }
}