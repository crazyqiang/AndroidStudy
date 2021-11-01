package org.ninetripods.mq.study.jetpack.mvvm.repo

import org.ninetripods.mq.study.jetpack.mvvm.base.BaseData
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseRepository
import org.ninetripods.mq.study.jetpack.mvvm.base.http.RetrofitUtil
import org.ninetripods.mq.study.jetpack.mvvm.base.http.api.DrinkService
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel

class WanRepository : BaseRepository() {

    suspend fun requestWanData(drinkId: String): BaseData<List<WanModel>> {
        val service = RetrofitUtil.getService(DrinkService::class.java)
        return executeRequest {
            service.getBanner()
        }
    }
}