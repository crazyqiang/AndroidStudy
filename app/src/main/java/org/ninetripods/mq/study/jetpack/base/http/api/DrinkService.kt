package org.ninetripods.mq.study.jetpack.base.http.api

import org.ninetripods.mq.study.jetpack.base.BaseData
import org.ninetripods.mq.study.jetpack.mvvm.model.RankModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import retrofit2.http.GET

interface DrinkService {

    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<WanModel>>

    @GET("coin/rank/1/json")
    suspend fun getRankList(): BaseData<RankModel>
}