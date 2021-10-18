package org.ninetripods.mq.study.jetpack.livedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts

class LiveDataFragment private constructor() : Fragment() {

    lateinit var mTvObserveView: TextView
    lateinit var mTvObserveTransform: TextView

    //数据观察者 数据改变时在onChange()中进行刷新
    private val changeObserver = Observer<String> { value ->
        value?.let {
            Log.e(KConsts.LIVE_DATA, "observer:$value")
            mTvObserveView.text = value
        }
    }

    private val changeObserverTransform = Observer<String> { value ->
        value?.let {
            Log.e(KConsts.LIVE_DATA, "transform observer:$value")
            mTvObserveTransform.text = value
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTvObserveView = view.findViewById(R.id.tv_observe)
        mTvObserveTransform = view.findViewById(R.id.tv_observe1)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //通过LiveData.observe注册观察者，监听数据变化
        //LiveDataInstance.INSTANCE.observe(viewLifecycleOwner, changeObserverTransform)
        //LiveDataInstance.INSTANCE2.observe(viewLifecycleOwner, changeObserverTransform)

        //1、Transformations.map()改变数据源
//        val transformMapLiveData =
//            Transformations.map(LiveDataInstance.INSTANCE) { "Transform:$it" }
//        //观察者监听的时候传入了LifecycleOwner 用以监听生命周期变化
//        transformMapLiveData.observe(viewLifecycleOwner, changeObserverTransform)

        /**
         * val switchmapLiveData1 = Transformations.switchMap(LiveDataInstance.INSTANCE,
        object : Function<String, LiveData<String>> {
        override fun apply(input: String?): LiveData<String> {
        return LiveDataInstance.INSTANCE2
        }
        }
        )
         */
        //2、Transformations.switchMap()切换数据源
        val switchMapLiveData =
            Transformations.switchMap(LiveDataInstance.SWITCH) { isSwitchOpen ->
                if (isSwitchOpen) {
                    LiveDataInstance.INSTANCE2
                } else {
                    LiveDataInstance.INSTANCE
                }
            }
        switchMapLiveData.observe(viewLifecycleOwner, changeObserverTransform)
    }

    override fun onDestroy() {
        super.onDestroy()
        LiveDataInstance.INSTANCE.removeObserver(changeObserver)
    }

    companion object {
        fun newInstance() = LiveDataFragment()
    }
}