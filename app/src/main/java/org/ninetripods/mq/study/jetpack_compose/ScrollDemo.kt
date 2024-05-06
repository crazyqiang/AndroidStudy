package org.ninetripods.mq.study.jetpack_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ninetripods.mq.study.kotlin.ktx.log
import kotlin.math.roundToInt

/**
 * 单击、双击、长按等事件
 */
@Composable
fun ClickSample() {
    var count by remember { mutableStateOf(0) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(150.dp),
    ) {
        Text(fontSize = 20.sp,
            textAlign = TextAlign.Center,
            text = count.toString(),
//            modifier = Modifier
//                .clickable { count += 1 }
            //处理双击、长按等事件
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset -> log("onPress: $offset") },
                    onTap = { offset -> log("onTap: $offset") },
                    onDoubleTap = { offset -> log("onDoubleTap: $offset") },
                    onLongPress = { offset -> log("onLongPress: $offset") },
                )
            }

        )
    }
}

/**
 * scrollable与verticalScroll/horizontalScroll的对比：
 * scrollable修饰符只会检测滚动手势，而不会偏移其内容；verticalScroll/horizontalScroll还可以偏移内容
 */
@Composable
fun ScrollSample() {
    var offset by remember { mutableStateOf(0f) }
    //指定ScrollableState 每次滚调时会回调，增量delta以px像素为单位
    val scrollState = rememberScrollableState { delta ->
        offset += delta
        delta
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(150.dp)
            .scrollable(orientation = Orientation.Vertical, state = scrollState)
            .background(Color.LightGray),
    ) {
        Text(text = offset.toString())
    }
}

/**
 * verticalScroll默认支持嵌套滑动，还有一种NestedScrollView的嵌套滑动
 */
@Composable
fun NestedScrollerSample() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(180.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.Yellow)
                            .background(brush = gradient)
                            .padding(20.dp)
                            .height(200.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DragSample() {

    //1、控制横向or纵向drag拖拽
    //注：draggable 与 scrollable类似，仅仅检测手势，如果还需要移动元素，考虑添加offset修饰符
    var offsetX by remember { mutableStateOf(0f) }
    val draggableState = rememberDraggableState(
        onDelta = { delta -> offsetX += delta }
    )
    Text(text = "Drag me!", modifier = Modifier
        .fillMaxWidth()
        .background(Color.Gray)
        .offset { IntOffset(offsetX.roundToInt(), 0) }
        .draggable(
            orientation = Orientation.Horizontal, state = draggableState
        ))

    //2、如果需要控制整个拖动手势，直接改为pointerInput来检测手势即可
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            //.offset(offsetX.px2dp().dp, offsetY.px2dp().dp)
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            })
    }
}

enum class Switch { CLOSE, OPEN }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSample() {
    val width = 144.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(Switch.CLOSE)
    //LocalDensity.current获取当前组合中的像素密度，进而进行dp->px的转换 ，px=dp*density
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    //每个状态都对应一个锚点，锚点以键值对进行表示：key表示偏移量(单位是Px)，value是状态
    //如下设置：偏移量为0f时表示的是CLOSE状态，而偏移96dp时表示的是OPEN状态
    val anchors =
        mapOf(0f to Switch.CLOSE, sizePx * 2 to Switch.OPEN) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                /**
                 * ThresholdConfig阈值有两个具体实现：
                 * 1、FixedThreshold(private val offset: Dp)来设置具体偏移值，thresholds默认就是 { _, _ -> FixedThreshold(56.dp) }
                 * 2、FractionalThreshold(fraction: Float)来设置偏移比例， 其中fraction的取值范围是[0.0, 1.0]，
                 *
                 * 当滑动超过阈值时，松手，滑块也会自动吸附到目标状态。如下设置中，默认是CLOSE状态，当滑动超过20%时会自动滑动到OPEN状态；反之，当前是OPEN状态，需要滑动超过30%时才会自动滑动到CLOSE状态。
                 */
                thresholds = { from, to ->
                    //from、to都表示的是anchors中设置的状态，这里表示的是CLOSE/OPEN状态。
                    if (from == Switch.CLOSE) {
                        FractionalThreshold(0.2f)
                    } else {
                        FractionalThreshold(0.3f)
                    }
                },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.Red)
        )
    }
}

@Composable
fun TransformableSample() {
    var scale by remember { mutableStateOf(1f) }
    var rotationAngle by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state =
        rememberTransformableState(onTransformation = { zoomChange, offsetChange, rotationChange ->
            log("zoomChange:$zoomChange, offsetChange:$offsetChange, rotationChange:$rotationChange ")
            scale *= zoomChange
            offset += offsetChange
            rotationAngle += rotationChange
        })

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .rotate(rotationAngle)
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .scale(scale)
//                .graphicsLayer(
//                    scaleX = scale,
//                    scaleY = scale,
//                    rotationZ = rotationAngle,
//                    translationX = offset.x,
//                    translationY = offset.y
//                )
                .transformable(state = state)
                .background(Color.Blue)
            //2、上述的transformable() 可以使用detectTransformGestures 进行替换
//                .pointerInput(Unit) {
//                    detectTransformGestures(
//                        //如果panZoomLock为true，则只有在平移或缩放运动之前检测到旋转的触摸倾斜时才允许旋转。否则，将检测平移和缩放手势，但不会检测旋转手势。
//                        //如果panZoomLock为false，所有三种手势都被检测，默认是false。
//                        panZoomLock = false,
//                        onGesture = { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
//                            offset += pan
//                            scale *= zoom
//                            rotationAngle += rotation
//                        })
//                }
        )
    }
}


/**
 * 1、awaitPointerEvent()类似于View体系中的onTouchEvent()，
 * 方法返回PointerEvent类型，包含所有手指交互信息changes以及事件信息internalPointerEvent
 *
 * 2、手势事件分发：PointerEventPass有三个值：Initial, Main, Final ，
 * 其中Initial类似于View体系中的onInterceptTouchEvent()、Main相当于View体系中的onTouchEvent()。 手势分发顺序Initial -> Main -> Final
 *
 * 3、手势事件消费：
 *
 */
@Composable
fun NestedBoxSample() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(400.dp)
            .background(Color.Red)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    //单指交互信息封装在PointerInputChange里了。
                    val event: PointerEvent = awaitPointerEvent()
                    awaitFirstDown(false)
                    log("x:${event.changes[0].position.x},y:${event.changes[0].position.y}")
                    awaitPointerEvent(PointerEventPass.Main)
                    log("first layer")
                }
            }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        awaitPointerEvent(PointerEventPass.Main)
                        log("second layer")
                    }
//                    awaitPointerEventScope { awaitFirstDown() }
                }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            awaitPointerEvent(PointerEventPass.Main)
                            log("third layer")
                        }
                    }) {
                Box(modifier = Modifier
                    .size(50.dp)
                    .background(Color.White)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            awaitPointerEvent(PointerEventPass.Main)
                            log("fourth layer")
                        }
                    })
            }
        }

    }
}
