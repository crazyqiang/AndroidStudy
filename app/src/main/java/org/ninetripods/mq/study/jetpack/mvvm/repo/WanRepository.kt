package org.ninetripods.mq.study.jetpack.mvvm.repo

import org.ninetripods.mq.study.jetpack.base.BaseData
import org.ninetripods.mq.study.jetpack.base.BaseRepository
import org.ninetripods.mq.study.jetpack.base.http.RetrofitUtil
import org.ninetripods.mq.study.jetpack.base.http.api.DrinkService
import org.ninetripods.mq.study.jetpack.mvvm.model.RankModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel

class WanRepository : BaseRepository() {
    val service = RetrofitUtil.getService(DrinkService::class.java)

    suspend fun requestWanData(drinkId: String): BaseData<List<WanModel>> {
        return executeRequest { service.getBanner() }
    }

    suspend fun requestRankData(): BaseData<RankModel> {
        return executeRequest { service.getRankList() }
    }
}