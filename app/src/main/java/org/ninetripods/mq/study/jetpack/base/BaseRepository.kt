package org.ninetripods.mq.study.jetpack.base

open class BaseRepository {

    suspend fun <T : Any> executeRequest(
        block: suspend () -> BaseData<T>
    ): BaseData<T> {
        val baseData = block.invoke()

        if (baseData.errorCode == 0) {
            if (baseData.data != null) {
                //有数据
                baseData.state = State.Success
            } else {
                //空数据
                baseData.state = State.Empty
            }
        } else {
            //错误
            baseData.state = State.Error
        }
        return baseData
    }
}