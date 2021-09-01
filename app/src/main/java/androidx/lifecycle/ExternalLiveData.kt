package androidx.lifecycle

import java.lang.Exception

/**
 * fork from: https://github.com/JeremyLiao/LiveEventBus
 */
open class ExternalLiveData<T> : MutableLiveData<T>() {

    companion object {
        //通过androidx.lifecycle包名来避免反射获取LiveData.START_VERSION
        const val START_VERSION = LiveData.START_VERSION
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // ignore
            return
        }
        try {
            val wrapper = ExternalLifecycleBoundObserver(owner, observer)
            val existing =
                callMethodPutIfAbsent(observer, wrapper) as? LiveData<*>.LifecycleBoundObserver
            require(!(existing != null && !existing.isAttachedTo(owner))) {
                ("Cannot add the same observer" + " with different lifecycles")
            }
            if (existing != null) return
            owner.lifecycle.addObserver(wrapper)
        } catch (e: Exception) {
            //ignore
        }
    }

    //继承父类并将修饰符改为public，可以对外暴露
    public override fun getVersion(): Int {
        return super.getVersion()
    }

    internal inner class ExternalLifecycleBoundObserver(
        owner: LifecycleOwner,
        observer: Observer<in T>?
    ) : LifecycleBoundObserver(owner, observer) {
        override fun shouldBeActive(): Boolean {
            return mOwner.lifecycle.currentState.isAtLeast(observerActiveLevel())
        }
    }

    /**
     * @return Lifecycle.State
     */
    protected open fun observerActiveLevel(): Lifecycle.State {
        return Lifecycle.State.STARTED
    }

    //反射获取LiveData.mObservers
    private val fieldObservers: Any
        get() {
            val fieldObservers = LiveData::class.java.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            return fieldObservers
        }

    /**
     * 反射调用LiveData的putIfAbsent方法
     */
    private fun callMethodPutIfAbsent(observer: Any, wrapper: Any): Any? {
        val mObservers = fieldObservers.javaClass
        val putIfAbsent =
            mObservers.getDeclaredMethod("putIfAbsent", Any::class.java, Any::class.java)
        putIfAbsent.isAccessible = true
        return putIfAbsent.invoke(mObservers, observer, wrapper)
    }


}