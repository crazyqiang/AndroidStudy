package org.ninetripods.mq.study.kotlin.flow.fragment

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.flow.FlowViewModel
import org.ninetripods.mq.study.kotlin.flow.PersonModel
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import java.io.*
import java.nio.charset.StandardCharsets

/**
 * Flow典型使用场景
 */
class FlowCaseFragment : BaseFragment() {

    private lateinit var mFlowModel: FlowViewModel
    private val mTvCombineResult: TextView by id(R.id.tv_combine_result)
    private val mBtnSex: Button by id(R.id.btn_sex)
    private val mBtnGrade: Button by id(R.id.btn_grade)

    private val mTvZipResult: TextView by id(R.id.tv_zip_result)
    private val mBtnZip: Button by id(R.id.btn_zip)


    override fun getLayoutId(): Int = R.layout.activity_flow_case

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFlowModel = ViewModelProvider(this).get(FlowViewModel::class.java)

        // 1、通过Flow获取本地文件信息
        getFileInfo()

        //2、多个Flow不能放到一个lifecycleScope.launch里去collect{}，因为进入collect{}相当于一个死循环，下一行代码永远不会执行；
        //如果就想写到一个lifecycleScope.launch{}里去，可以在内部再开启launch{}子协程去执行。
        lifecycleScope.launch {
            launch {
                mFlowModel.caseFlow1
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        log("caseFlow:$it")
                    }
            }

            launch {
                mFlowModel.caseFlow2
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        log("caseFlow1:$it")
                    }
            }
        }
        mFlowModel.requestCaseValue1()
        mFlowModel.requestCaseValue2()

        //3、combine操作符
        lifecycleScope.launch {
            mFlowModel.combineFlow.collect { student ->
                mTvCombineResult.text = student.info()
            }
        }

        mBtnSex.setOnClickListener {
            val randomSex = arrayOf(1, 2)
            mFlowModel.sexFlow.value = randomSex.random()
        }

        mBtnGrade.setOnClickListener {
            val randomGrade = arrayOf("一", "二", "三", "四", "五", "六")
            mFlowModel.gradeFlow.value = randomGrade.random()
        }

        //4、zip组合多个接口请求的数据
        mBtnZip.setOnClickListener {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val electricFlow = mFlowModel.requestElectricCost()
                    val waterFlow = mFlowModel.requestWaterCost()
                    val internetFlow = mFlowModel.requestInternetCost()

                    val builder = StringBuilder()
                    var totalCost = 0f
                    val startTime = System.currentTimeMillis()
                    //NOTE:注意这里可以多个zip操作符来合并Flow，且多个Flow之间是并行关系
                    electricFlow.zip(waterFlow) { electric, water ->
                        totalCost = electric.cost + water.cost
                        builder.append("${electric.info()},\n").append("${water.info()},\n")
                    }.zip(internetFlow) { two, internet ->
                        totalCost += internet.cost
                        two.append(internet.info()).append(",\n\n总花费：$totalCost")
                    }.collect {
                        val totalStr =
                            it.append("，总耗时：${System.currentTimeMillis() - startTime} ms")
                        log("zip:${totalStr}")
                        mTvZipResult.text = totalStr
                    }
                }
            }
        }
    }

    /**
     * 通过Flow方式，获取本地文件
     */
    private fun getFileInfo() {
        lifecycleScope.launch {
            flow {
                //解析本地json文件，并生成对应字符串
                val configStr = getAssetJsonInfo(requireContext(), "person.json")
                //最后将得到的实体类发送到下游
                emit(configStr)
            }
                .map { json ->
                    log("thread:${Thread.currentThread().name}")
                    Gson().fromJson(json, PersonModel::class.java) //通过Gson将字符串转为实体类
                }
                .flowOn(Dispatchers.IO) //在flowOn之上的所有操作都是在IO线程中进行的
                .onStart { log("onStart") }
                .filterNotNull()
                .onCompletion { log("onCompletion") }
                .catch { ex -> log("catch:${ex.message}") }
                .collect {
                    log("collect parse result:$it")
                }
        }
    }

    /**
     * 读取Assets下的json文件
     * @param context
     * @param fileName
     * @return
     */
    private fun getAssetJsonInfo(context: Context, fileName: String): String {
        val strBuilder = StringBuilder()
        var input: InputStream? = null
        var inputReader: InputStreamReader? = null
        var reader: BufferedReader? = null
        try {
            input = context.assets.open(fileName, AssetManager.ACCESS_BUFFER)
            inputReader = InputStreamReader(input, StandardCharsets.UTF_8)
            reader = BufferedReader(inputReader)
            var line: String?
            while ((reader.readLine().also { line = it }) != null) {
                strBuilder.append(line)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            try {
                input?.close()
                inputReader?.close()
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return strBuilder.toString()
    }

    /**
     * 解析本地Json文件
     * @param downloadFile
     * @param jsonName
     * @return
     */
    private fun processLocalJson(downloadFile: File, jsonName: String): String {
        val strBuilder = StringBuilder()
        val targetFile = File(downloadFile, jsonName)
        if (!targetFile.exists()) return ""
        try {
            var line: String?
            val bufferReader = BufferedReader(InputStreamReader(FileInputStream(targetFile)))
            while ((bufferReader.readLine().also { line = it }) != null) {
                strBuilder.append(line)
            }
        } catch (ex: Exception) {
            log("ex:${ex.message}")
            ex.printStackTrace()
        }
        return strBuilder.toString()
    }
}