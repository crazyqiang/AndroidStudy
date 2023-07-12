package org.ninetripods.mq.study.util.sp

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreferences委托代理
 * Created by mq on 2023/6/5
 * @param context Context
 * @param spName SP存入的XML名字
 * @param defaultValue 默认值
 * @param key 存取数据时对应的key
 */
class SharedPreferencesDelegate<T>(
    private val context: Context,
    private val spName: String,
    private val defaultValue: T,
    private val key: String? = null,
) : ReadWriteProperty<Any?, T> {

    private val sp: SharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val finalKey = key ?: property.name
        return when (defaultValue) {
            is Int -> sp.getInt(finalKey, defaultValue)
            is Long -> sp.getLong(finalKey, defaultValue)
            is Float -> sp.getFloat(finalKey, defaultValue)
            is Boolean -> sp.getBoolean(finalKey, defaultValue)
            is String -> sp.getString(finalKey, defaultValue)
            is Set<*> -> sp.getStringSet(finalKey, defaultValue as? Set<String>)
            else -> throw IllegalStateException("Unsupported type")
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val finalKey = key ?: property.name
        with(sp.edit()) {
            when (value) {
                is Int -> putInt(finalKey, value)
                is Long -> putLong(finalKey, value)
                is Float -> putFloat(finalKey, value)
                is Boolean -> putBoolean(finalKey, value)
                is String -> putString(finalKey, value)
                is Set<*> -> putStringSet(finalKey, value.map { it.toString() }.toHashSet())
                else -> throw IllegalStateException("Unsupported type")
            }
            apply()
        }
    }
}

class CommonPreferences(context: Context) {
    companion object {
        /**
         * 通过传入不同的SP文件名来存储到不同的XML中
         */
        const val FIR_SP_NAME = "FIR_SP_NAME" //文件名1
        const val SEC_SP_NAME = "SEC_SP_NAME"//文件名2
    }

    var isShow by SharedPreferencesDelegate(context, FIR_SP_NAME, false, "key_is_show")

    //这里没有用key值，则会默认使用属性名来当做key值
    var name by SharedPreferencesDelegate(context, FIR_SP_NAME, "")

    var age by SharedPreferencesDelegate(context, SEC_SP_NAME, 0, "key_age")

    //这里没有用key值，则会默认使用属性名来当做key值
    var cost by SharedPreferencesDelegate(context, SEC_SP_NAME, 0.0f)

    var setString by SharedPreferencesDelegate(context, SEC_SP_NAME, setOf("1", "2", "3"))
}
