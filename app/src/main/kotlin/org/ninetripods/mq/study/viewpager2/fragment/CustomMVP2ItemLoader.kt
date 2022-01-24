package org.ninetripods.mq.study.viewpager2.fragment

import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.imageLoader.BaseLoader

class CustomMVP2ItemLoader : BaseLoader() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun createView(context: Context): View {
        val itemView = super.createView(context)
        itemView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    0, 0, view?.width ?: 0,
                    view?.height ?: 0, 50f
                )
            }
        }
        itemView.clipToOutline = true
        return itemView
    }

    override fun display(context: Context, content: Any, targetView: View) {
        Glide.with(context).load(content).into(targetView as ImageView)
    }

}