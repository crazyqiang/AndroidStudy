package org.ninetripods.mq.study.jetpack_compose.compose

import android.content.res.Resources
import android.graphics.BitmapShader
import android.graphics.Shader
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseMvvmActivity
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack_compose.NestedBoxSample
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * Created by mq on 2023/10/23
 */
class ComposeExampleActivity : BaseMvvmActivity() {

    private val mVModel: ComposeVModel by viewModels()

    override fun getViewModel(): BaseViewModel = mVModel

    override fun init() {}

    override fun setContentView() {
        setContent {
            //RememberScreen() //1、remember相关用法
            //2、测试DisposableEffect相关用法
//            HomeScreen(
//                onStart = { log("HomeScreen: onStart()") },
//                onStop = { log("HomeScreen: onStop()") })
            // LaunchedEffectScreen() //3、测试LaunchedEffectScreen相关用法

//            var num by remember { mutableStateOf(0) }
//            ComposeEffect(onClick = {
//                //4、测试rememberUpdatedState相关用法
//                log("最新Num:$num")
//            })
//            ShowText()
//            ButtonSample()
//            SliderSample()
            // DialogSample()
//            ModifierDemo()
//            NestedScrollerSample()
//            Column {
////                DragSample()
////                ClickSample()
////                ScrollSample()
//                Spacer(modifier = Modifier.height(100.dp))
//                //.............Swipe滑动.............
//                Text(text = "Modifier.swipeable()")
//                SwipeableSample()
//                //.............Transform变换.............
//                Spacer(modifier = Modifier.height(20.dp))
//                Text(text = "Modifier.transformable()")
//                TransformableSample()
//            }
            NestedBoxSample()
            //AnimationScreen()
            //SideEffectStudy()
        }
    }


    @Composable
    fun RememberScreen() {
        var resId by remember { mutableStateOf(R.drawable.icon_flower) }
        log("resId: $resId")

        Greeting(resId = resId,
            onClickAction = {
                resId = R.drawable.icon_qrcode_we_chat
                mVModel.getWanInfo()
            })
    }

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Greeting(
        res: Resources = LocalContext.current.resources,
        @DrawableRes resId: Int,
        onClickAction: () -> Unit,
    ) {
        //1、remember的三种用法 ，remember会存储值，直到退出组合。
        val mutableState = remember { mutableStateOf("init0") } //返回MutableState<T>类型
        var value1 by remember { mutableStateOf("init1") } //返回T类型
        val (value2, setValue) = remember { mutableStateOf("init") } //返回两个值分别为：T,Function1<T, kotlin.Unit>

        //2、remember还可以接受key参数，当key发生变化，缓存值会失效并再次对 lambda 块进行计算。这种机制可控制组合中对象的生命周期。在key发生变化之前（而不是在记住的值退出组合之前），计算会一直有效。
        //示例：下面这个代码段会创建一个 ShaderBrush 并将其用作 Box 可组合项的背景绘制。remember 则会存储 ShaderBrush 实例，因为其重建成本高昂（如前所述）。此外，remember 也会接受 avatarRes 作为 key1 参数，即选定的背景图片。如果 avatarRes 发生变化，笔刷会根据新图片进行重组，然后重新应用于 Box。当用户从选择器中选择另一张图片作为背景时，可能就会发生这种情况。
        val bitmap = remember(key1 = resId) {
            ShaderBrush(
                BitmapShader(
                    ImageBitmap.imageResource(res, resId).asAndroidBitmap(),
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT
                )
            )
        }

        // 1、如果涉及到配置更改后的状态恢复，直接使用rememberSaveable，会将值存储到Bundle中
        // 2、如果存储的值不支持Bundle，可以将Model声明为@Parcelable 或者使用MapSaver、ListSaver自定义存储规则
        var parcelCity by rememberSaveable {
            mutableStateOf(CityParcel("Beijing", "China"))
        }

        var mapSaverCity by rememberSaveable(stateSaver = CityMapSaver) {
            mutableStateOf(City("Beijing", "China"))
        }

        var listSaverCity by rememberSaveable(stateSaver = CityListSaver) {
            mutableStateOf(City("Beijing", "China"))
        }
        log("parcelCity: $parcelCity")
        log("mapSaverCity: $mapSaverCity")
        log("listSaverCity: $listSaverCity")

        //Flow转State
        val state by mVModel.mWanFlow.collectAsStateWithLifecycle()
        //LiveData转State
        val liveDataState by mVModel.mWanLiveData.observeAsState()
        log("state:${state}")

        val uiState by produceState<WanUiState>(WanUiState.Loading, mVModel) {
            //在协程中
            mVModel.mWanFlow
                .map { WanUiState.SUC(it) }
                .collect { value = it }
        }
        when (val finalUiState = uiState) {
            is WanUiState.Loading -> Text("Loading...")
            is WanUiState.SUC -> Column {
                for (model in finalUiState.models) {
                    Text("Hello, ${model.desc}")
                }
            }
        }

        Column(modifier = Modifier.background(bitmap)) {
            Text(
                text = "flow: $state",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "liveData: $liveDataState",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = onClickAction) {
                Text(text = "点击更改文本")
            }
        }
    }
}

sealed class WanUiState {
    object Loading : WanUiState()
    data class SUC(val models: List<WanModel>) : WanUiState()
}

@Parcelize
data class CityParcel(val name: String, val country: String) : Parcelable

data class City(val name: String, val country: String)

//MapSaver自定义存储规则，将对象转换为系统可保存到 Bundle 的一组值。
val CityMapSaver = run {
    val nameKey = "Beijing"
    val countryKey = "China"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

//ListSaver
val CityListSaver = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },
    restore = { City(it[0] as String, it[1] as String) }
)