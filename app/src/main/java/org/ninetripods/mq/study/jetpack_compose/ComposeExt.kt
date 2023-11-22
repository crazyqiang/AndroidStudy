package org.ninetripods.mq.study.jetpack_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * Created by mq on 2023/11/15
 */
@Composable
fun SideEffectStudy() {
    SideEffect {
        log("SideEffect launch")
    }
    throw IllegalArgumentException("exception throw")
}



@Composable
fun ComposeEffect(onClick: () -> Unit) {
    //2、rememberCoroutineScope在非重组作用域启动协程任务
    val coroutineScope = rememberCoroutineScope()

    var txt by remember { mutableStateOf("") }
    //3、rememberUpdatedState在LaunchedEffect执行一段异步操作之后，获取最新的值
    val newFuc by rememberUpdatedState(newValue = onClick)

    //1、可组合函数，只能用在可组合函数作用域内
    LaunchedEffect(key1 = Unit) {
        delay(3000)
        txt = "LaunchedEffect延迟执行"
    }

    //4. 在可组合函数里面订阅信息，并在可组合函数退出时取消订阅
    DisposableEffect(key1 = Unit) {
        //onDispose必须调用，在这里面进行解绑或注销操作
        onDispose {

        }
    }


    //5、DisposableEffect + rememberCoroutineScope ≈ LaunchedEffect
    LaunchedEffect(key1 = Unit) {
    }
    val scope = rememberCoroutineScope()
    DisposableEffect(key1 = Unit) {
        val job = scope.launch { }
        onDispose {
            job.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "结果：$txt")

        //测试rememberCoroutineScope
        Button(onClick = {
            coroutineScope.launch {
                delay(3000)
                txt = "rememberCoroutineScope"
            }
        }) {
            Text(text = "rememberCoroutineScope")
        }

        //测试rememberUpdatedState
        Button(onClick = {
            coroutineScope.launch {
                delay(3000)
                newFuc()
//                onClick()
            }
        }) {
            Text(text = "rememberUpdatedState")
        }
    }
}

@Composable
fun ComposeEffect1() {
    var txt by remember { mutableStateOf("") }
    //rememberCoroutineScope在非重组作用域启动协程任务
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = txt)
        Button(onClick = {
            coroutineScope.launch {
                delay(3000)
                txt = "rememberCoroutineScope"
            }
        }) {
            Text(text = "rememberCoroutineScope")
        }
    }
}

@Composable
fun LaunchedEffectScreen(
    state: Boolean = true,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {

    // If the UI state contains an error, show snackbar
    if (state) {
        log("LaunchedEffect launch")

        // `LaunchedEffect` will cancel and re-launch if
        // `scaffoldState.snackbarHostState` changes
        LaunchedEffect(scaffoldState.snackbarHostState) {
            // Show snackbar using a coroutine, when the coroutine is cancelled the
            // snackbar will automatically dismiss. This coroutine will cancel whenever
            // `state.hasError` is false, and only start when `state.hasError` is true
            // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message",
                actionLabel = "Retry message"
            )
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        /* ... */
    }
}

@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    val currentOnStart by rememberUpdatedState(newValue = onStart)
    val currentOnStop by rememberUpdatedState(newValue = onStop)

    //当lifecycleOwner发生改变时，丢弃并重置效应
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        //当效应离开可组合项时，移除观察者
        onDispose {
            log("DisposableEffect: onDispose")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun ShowText() {
    val list = remember { mutableStateListOf<String>() }
    val showText by remember { derivedStateOf { list.size.toString() } }
    Column {
        Button(onClick = { list.add("") }) {
            Text(text = "点我加1")
        }
        Text(text = showText)
    }
}