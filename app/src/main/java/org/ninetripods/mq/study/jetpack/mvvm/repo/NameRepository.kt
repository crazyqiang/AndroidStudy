package org.ninetripods.mq.study.jetpack.mvvm.repo

import com.example.jetpackstudy.mvvm.model.User


class NameRepository {
    /**
     * 模拟网络请求
     */
    fun loadDataFromRepo(listener: UserBack) {
        Thread {
            Thread.sleep(3000)
            val user = User("ViewModel Study")
            if (user == null) {
                listener.onFailed(User("empty data"))
            } else {
                listener.onSuccess(user)
            }
        }.start()
    }

    interface UserBack {
        fun onSuccess(user: User)
        fun onFailed(msg: User)
    }
}