package androidx.lifecycle

import java.lang.Exception

/**
 * https://github.com/JeremyLiao/LiveEventBus
 */
//TODO
class ExternalLiveData<T> : MutableLiveData<T>() {

    companion object {
        //通过androidx.lifecycle包名来避免反射获取LiveData.START_VERSION
        const val START_VERSION = LiveData.START_VERSION
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            // ignore
            return
        }
        try {
            val wrapper = ExternalLifecycleBoundObserver(owner, observer)
        } catch (e: Exception) {

        }
//        val wrapper: LifecycleBoundObserver = LifecycleBoundObserver(owner, observer)
//        val existing: ObserverWrapper? = mObservers.putIfAbsent(observer, wrapper)
//        require(!(existing != null && !existing.isAttachedTo(owner))) {
//            ("Cannot add the same observer"
//                    + " with different lifecycles")
//        }
//        if (existing != null) {
//            return
//        }
//        owner.lifecycle.addObserver(wrapper)
    }

    override fun getVersion(): Int {
        return super.getVersion()
    }

    internal inner class ExternalLifecycleBoundObserver(owner: LifecycleOwner, observer: Observer<in T>?)
        : LifecycleBoundObserver(owner, observer) {
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


}