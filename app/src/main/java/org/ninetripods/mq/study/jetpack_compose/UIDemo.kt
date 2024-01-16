package org.ninetripods.mq.study.jetpack_compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.twotone.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.showToast

@Composable
fun ModifierDemo() {
    /**
     *  Modifier的能力：
     * 1、更改可组合项的大小、布局、行为和外观
     * 2、添加信息，如无障碍标签
     * 3、处理用户输入
     * 4、添加高级互动，如使元素可点击、可滚动、可拖动或可缩放
     */
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        //设置渐变色
        val bgGradient = Brush.verticalGradient(
            colors = listOf(Color.Red, Color.Green, Color.Yellow, Color.Cyan)
        )
        Row(
            modifier = Modifier
                .background(bgGradient)
                .fillMaxWidth()
                .height(40.dp),
            //设置具体的宽高 如果子布局中有requiredSize，那么requiredSize的优先级更高一些
            //.width(500.dp).height(100.dp).size(width = 500.dp, height = 100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Tab1",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
            Image(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.BottomCenter, true)
                    .weight(1f)
                    .clip(CircleShape)
                    .border(width = 2.dp, Color.Red, CircleShape)
                    .requiredSize(80.dp),
                painter = painterResource(id = R.drawable.icon_cat_w),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
            Text(
                text = "Tab2",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

/**
 * padding通过位置不同来表示内外边距
 */
@Composable
fun ModifierOrderSample() {
    Box(
        modifier = Modifier
            .size(200.dp, 100.dp)
            .background(Color.Gray)
            .padding(20.dp)
            .border(1.dp, Color.Red, RectangleShape)
            .padding(20.dp)
            .background(Color.Green)
    )
}

/**
 *
 */
@Composable
fun OffsetSample() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "你好", modifier = Modifier.background(Color.Yellow))
        //.offset(x = 100.dp, y = 100.dp) //2种写法
        //此外：absoluteOffset还可以强制从左到右进行布局
        Text(text = "Jetpack Compose", modifier = Modifier.offset {
            IntOffset(100.dp.roundToPx(), 100.dp.roundToPx())
        })
    }
}

/**
 * matchParentSize vs fillMaxSize
 */
@Composable
private fun MatchParentSizeSample() {
    Box {
        //换成fillMaxSize之后会填充整个父控件所允许的最大尺寸
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Yellow)
        )
        Text(text = "Jetpack Compose", modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun AnimationScreen() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_cat_h),
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                contentDescription = "",
                contentScale = ContentScale.Fit,
            )
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.icon_cat_w),
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                contentDescription = "",
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Image(
            imageVector = Icons.Filled.Call,
            contentDescription = "",
        )

        Button(modifier = Modifier.align(Alignment.End),
            onClick = { showToast("点击取消") }) {
            Image(
                imageVector = Icons.TwoTone.Close,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "取消")
        }
    }
}

@Composable
fun ButtonSample() {
    val interactSource = remember { MutableInteractionSource() }
    val pressState = interactSource.collectIsPressedAsState()
    val borderColor = if (pressState.value) Color.Red else Color.White
    Button(
        onClick = { },
        border = BorderStroke(2.dp, color = borderColor),
        interactionSource = interactSource
    ) {
        Text(text = "长按点击")
    }
}

@Composable
fun SliderSample() {
    var sliderPos by remember { mutableStateOf(0f) }
    Text(text = "%.1f".format(sliderPos * 100) + "%")
    Slider(value = sliderPos, onValueChange = { sliderPos = it })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogSample() {

    var isShow by remember { mutableStateOf(false) }
    //普通Dialog
    if (isShow) {
        //注意: Compose中的Dialog并不像View体系中那种调用show/dismiss展示与消失，它也需要通过重组的形式进行显隐：
        //当Dialog参与重组时就会显示，反之隐藏，
        Dialog(
            onDismissRequest = {
                log("onDismissRequest")
                isShow = false
            }, properties = DialogProperties(
                dismissOnBackPress = true, //点击系统返回键是否允许关闭弹窗，调用到onDismissRequest中控制
                dismissOnClickOutside = true, //点击dialog外部区域是否关闭弹窗，调用到onDismissRequest中控制
                securePolicy = SecureFlagPolicy.Inherit, //TODO
                usePlatformDefaultWidth = true, //对话框内容是否限制在默认范围内
            )
        ) {
            Surface(
                modifier = Modifier.size(300.dp), color = Color.Green
            ) {
                Text(text = "Dialog")
            }
        }
    }

    var isShowAlertDialog by remember { mutableStateOf(true) }
    if (isShowAlertDialog) {
        AlertDialog(properties = DialogProperties(
            dismissOnBackPress = false, //点击系统返回键是否允许关闭弹窗，调用到onDismissRequest中控制
            dismissOnClickOutside = false, //点击dialog外部区域是否关闭弹窗，调用到onDismissRequest中控制
            securePolicy = SecureFlagPolicy.Inherit,
            usePlatformDefaultWidth = true, //对话框内容是否限制在默认范围内
        ),
            onDismissRequest = { isShowAlertDialog = false },
            title = { Text(text = "权限申请") },
            text = { Text(text = "请求存储权限，用于存储本地照片、拍照等，请允许") },
            confirmButton = {
                TextButton(onClick = { isShowAlertDialog = false }) {
                    Text(text = "同意")
                }
            },
            dismissButton = {
                TextButton(onClick = { isShowAlertDialog = false }) {
                    Text(text = "取消")
                }
            })
    }
}

@Composable
fun LayoutSample() {

}

