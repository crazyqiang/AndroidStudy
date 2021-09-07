package org.ninetripods.mq.study.jetpack.repo

import com.example.jetpackstudy.mvvm.model.Drink
import org.ninetripods.mq.study.jetpack.base.BaseData
import org.ninetripods.mq.study.jetpack.base.BaseRepository

class DrinkRepository : BaseRepository() {

    suspend fun requestDrinkData(drinkId: String): BaseData<List<Drink>>? {
//        val service = RetrofitManager.initRetrofit()
//            .getService(DrinkService::class.java)
//        return executeRequest { service.getBanner() }
        return null
    }
}