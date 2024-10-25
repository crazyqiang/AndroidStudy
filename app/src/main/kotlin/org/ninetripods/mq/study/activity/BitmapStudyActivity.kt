package org.ninetripods.mq.study.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.log
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class BitmapStudyActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.layout_activity_bitmap)
    }

    override fun initViews() {
        //1、DisplayMetrics中的相关信息
        resources.displayMetrics.run {
            log("density: $density")
            log("scaledDensity: $scaledDensity")
            log("xdpi: $xdpi")
            log("ydpi: $ydpi")
            log("widthPixels: $widthPixels")
            log("heightPixels: $heightPixels")
            log("densityDpi: $densityDpi")
        }

        //2、通过BitmapFactory.Options来计算图片的信息
        val options = BitmapFactory.Options().apply {
//            inJustDecodeBounds = true //只解析图片元信息，不加载到内存
        }
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher, options)
        //图片占用内存大小 (bytes) = 图片宽度 (pixels) × 图片高度 (pixels) × 每个像素的字节数
        val memorySize =
            options.outWidth * options.outHeight * getBytesPerPixel(options.inPreferredConfig)
        log("图片宽度: ${options.outWidth}, 高度: ${options.outHeight}")
        log("图片加载到内存时占用大小: ${memorySize / 1024} KB")
        log("options: ${options.inDensity}, ${options.inTargetDensity}")

        /**
         * 生成Bitmap
         */
        //1、BitmapFactory.decodeStream生成bitmap
        lifecycleScope.launch {
            flow<Bitmap> {
                runCatching {
                    val inputStream =
                        URL("https://img0.baidu.com/it/u=264197520,340791108&fm=253&fmt=auto&app=120&f=JPEG?w=570&h=380").openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    emit(bitmap)
                }
            }
                .flowOn(Dispatchers.IO)
                .collectLatest { bm ->
//                    log("在线图片大小 -> width:${bm.width}, height:${bm.height}, " +
//                            "allocationByteCount:${bm.allocationByteCount}=${bm.allocationByteCount / 1024} KB")
                }
        }

        //2、BitmapFactory.decodeResource生成位图
        val bitmap2 = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)


        /**
         * Bitmap相关方法
         */
        bitmap2.run {
//            width
//            height
//            config
//            recycle()
//            isRecycled
//            copy(Bitmap.Config.ARGB_8888, true)
//            compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
//            getPixel(10,10)
//            setPixel(10, 10, Color.RED)
//            hasAlpha()
//            isMutable
//            eraseColor(Color.WHITE)
//            byteCount
//            allocationByteCount
        }
        // 1、copy复制一个新的Bitmap，使用相同的颜色配置，并使其可变
        val copiedBitmap = bitmap2.copy(Bitmap.Config.ARGB_8888, true)
        // 修改新Bitmap的一个像素
        copiedBitmap.setPixel(10, 10, Color.RED)
        // 打印两者的宽高，验证是两个不同的位图对象
        log("Original bitmap width: ${bitmap2.width}, height: ${bitmap2.height}")
        log("Copied bitmap width: ${copiedBitmap.width}, height: ${copiedBitmap.height}")
        log("hashCode: ${bitmap2.hashCode()}, ${copiedBitmap.hashCode()}")


        /**
         * BitmapFactory.Options相关方法
         */
        //val options = BitmapFactory.Options()
        //inBitmap：复用已存在的位图内存，减少内存分配和回收的开销。
        options.inMutable = true //设置为可变位图
        val reusableBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w, options)
        reusableBitmap.run {
            log("1、reusableBitmap -> height:$height, width:$width, byteCount:$byteCount, allocationByteCount:$allocationByteCount")
        }
        options.inBitmap = reusableBitmap
        val targetBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_compose, options)
        targetBitmap.run {
            log("2、targetBitmap -> height:$height, width:$width, byteCount:$byteCount, allocationByteCount:$allocationByteCount")
        }
        log("3、reusableBitmap: ${reusableBitmap.hashCode()}, targetBitmap: ${targetBitmap.hashCode()}")
        //1、reusableBitmap -> height:786, width:1100, byteCount:3458400, allocationByteCount:3458400
        //2、targetBitmap -> height:458, width:424, byteCount:776768, allocationByteCount:3458400
        //3、reusableBitmap: 98561878, targetBitmap: 98561878
        //可以看到3处hashCode的值是一样的，说明Bitmap确实复用了 需要注意宽高只能从大往小复用，反之复用会报错。

        options.run {
            log(
                "inJustDecodeBounds = $inJustDecodeBounds\n" +
                        "            inSampleSize = $inSampleSize\n" +
                        "            inPreferredConfig = $inPreferredConfig\n" +
                        "            inDither = $inDither\n" +
                        "            inMutable = $inMutable\n" +
                        "            outHeight = $outHeight\n" +
                        "            outWidth = $outWidth\n" +
                        "            outMimeType = $outMimeType\n" +
                        "            inDensity = $inDensity\n" +
                        "            inTargetDensity = $inTargetDensity\n" +
                        "            inScreenDensity = $inScreenDensity\n" +
                        "            inScaled = $inScaled"
            )
        }
    }

    // 根据 Bitmap.Config 获取每个像素的字节数
    private fun getBytesPerPixel(config: Bitmap.Config): Int {
        return when (config) {
            Bitmap.Config.ARGB_8888 -> 4 // 4字节（32位）
            Bitmap.Config.RGB_565 -> 2  // 2字节（16位）
            Bitmap.Config.ARGB_4444 -> 2 // 2字节（已被废弃）
            Bitmap.Config.ALPHA_8 -> 1  // 1字节（8位，仅有透明度）
            else -> 4 // 默认情况，按 ARGB_8888 计算
        }
    }
}