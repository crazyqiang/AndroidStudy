package org.ninetripods.mq.study.kotlin.flow.fragment

import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.flow.FlowViewModel
import org.ninetripods.mq.study.kotlin.flow.PersonModel
import org.ninetripods.mq.study.kotlin.ktx.log
import java.io.*
import java.nio.charset.StandardCharsets

/**
 * Flow典型使用场景
 */
class FlowCaseFragment : BaseFragment() {

    private lateinit var mFlowModel: FlowViewModel
    private lateinit var mSimpleFlow: Flow<String>

    override fun getLayoutId(): Int = R.layout.activity_flow_case

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFlowModel = ViewModelProvider(this).get(FlowViewModel::class.java)
        getFileInfo()
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