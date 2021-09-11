package org.ninetripods.mq.study.jetpack.mvvm.base.http.api

import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseData
import retrofit2.http.GET

interface DrinkService {

    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<WanModel>>
}