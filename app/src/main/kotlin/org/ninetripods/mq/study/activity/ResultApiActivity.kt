package org.ninetripods.mq.study.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * Activity Result API使用示例
 * Created by mq on 2023/5/11
 */
class ResultApiActivity : AppCompatActivity() {
    private val mTvResultApi: TextView by id(R.id.tv_get_result)
    private val mTvStartActForResult: TextView by id(R.id.tv_start_activity_for_result)
    private val mTvPermission: TextView by id(R.id.tv_start_permission)
    private val mTvCustomContract: TextView by id(R.id.custom_contract)

    /**
     * 位于 ComponentActivity 或 Fragment 中时，Activity Result API 会提供 registerForActivityResult() API，用于注册结果回调。
     *
     * registerForActivityResult() 接受 ActivityResultContract 和 ActivityResultCallback 作为参数，
     * 并返回 ActivityResultLauncher，用来启动另一个activity，但是此时还没有启动，需要调用ActivityResultLauncher#launch()进行启动。
     *
     * registerForActivityResult(ActivityResultContract contract, ActivityResultCallback callback)
     * 1、ActivityResultContract<I,O>：泛型<I,O>定义生成结果所需的输入、输出类型，可为拍照、请求权限等基本intent操作提供默认协定，还可以创建自定义协定。
     * 2、ActivityResultCallback<O>：单一方法接口，带有onActivityResult(O result)方法，接收ActivityResultContract<I,O>中定义的输出类型O对象。
     *
     * registerForActivityResult()方法执行后，返回ActivityResultLauncher类型。
     * ActivityResultLauncher：启动 input 并处理返回值
     */
    private var resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val code = result.resultCode //返回码 如：Activity.RESULT_OK、Activity.RESULT_CANCELED
        val intent = result.data
        log("resultCode:$code,data:${intent?.getStringExtra(ResultApi2Activity.KEY_TRANSFER)}")
    }

    private var permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        for ((string, isGrant) in permissions) {
            log("$string 权限申请状态:$isGrant")
            /**
             * 如执行结果：
             * 1、android.permission.CAMERA 权限申请状态:true
             * 2、android.permission.WRITE_EXTERNAL_STORAGE 权限申请状态:true
             */
        }
    }

    private val mLauncher =
        registerForActivityResult(CustomContract()) { result -> log("return result:$result") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_api)
        mTvPermission.setOnClickListener {
            /**
             * 权限申请
             */
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
        }
        mTvResultApi.setOnClickListener {
            /**
             * 请求另一个Activity页面数据新方式
             */
            resultLauncher.launch(Intent(this, ResultApi2Activity::class.java))
        }
        /**
         * startActivityForResult()老方式
         */
        mTvStartActForResult.setOnClickListener {
            startActivityForResult(Intent(this, ResultApi2Activity::class.java), 1000)
        }
        /**
         * 自定义Contract
         */
        mTvCustomContract.setOnClickListener {
            mLauncher.launch(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1000) {
            //...处理返回的数据...
            val value = data?.getStringExtra(ResultApi2Activity.KEY_TRANSFER)
            log("return is $value")
        }
    }

}

/**
 * 自定义ActivityResultContract
 */
class CustomContract : ActivityResultContract<Void, String>() {
    companion object {
        const val DEFAULT_VALUE = "default_value"
    }

    /**
     * 创建Intent
     * @param context Context
     * @param input 当前类的第一个泛型参数
     * @return
     */
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context, ResultApi2Activity::class.java)
    }

    /**
     * 解析结果，类似于Activity#onActivityResult
     * @param resultCode 返回码 [Activity.setResult] 的 resultCode
     * @param intent [Activity.setResult] 的 intent
     * @return
     */
    override fun parseResult(resultCode: Int, intent: Intent?): String {
        if (resultCode != Activity.RESULT_OK || intent == null) return DEFAULT_VALUE
        return intent.getStringExtra(ResultApi2Activity.KEY_TRANSFER) ?: DEFAULT_VALUE
    }

    /**
     * 直接获取同步结果，可以用于中间请求拦截判断使用，如ActivityResultContracts.RequestPermission中的该方法重写
     */
    override fun getSynchronousResult(context: Context, input: Void?): SynchronousResult<String>? {
        return super.getSynchronousResult(context, input)
    }

}