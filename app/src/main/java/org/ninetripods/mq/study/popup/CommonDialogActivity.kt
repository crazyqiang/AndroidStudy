package org.ninetripods.mq.study.popup

import android.os.Handler
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ninetripods.sydialoglib.SYDialog
import com.ninetripods.sydialoglib.manager.DialogWrapper
import com.ninetripods.sydialoglib.manager.SYDialogsManager
import com.ninetripods.sydialoglib.widget.DialogLoadingView
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.MyApplication
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.popup.CommonDialogActivity.ShareAdapter.ShareHolder
import org.ninetripods.mq.study.popup.dialog.DialogUtil

class CommonDialogActivity : BaseActivity() {
    private var dialog: SYDialog? = null
    override fun setContentView() {
        setContentView(R.layout.activity_common_dialog)
    }

    override fun initViews() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        initToolBar(toolbar, "CommonDialog", true)
    }

    override fun initEvents() {
        super.initEvents()
    }

    /**
     * 1、默认Dialog:一个Button
     *
     * @param view View
     */
    fun showDefaultDialog(view: View?) {
        DialogUtil.createDefaultDialog(this, "我是标题", "你好,我们将在30分钟处理，稍后通知您订单结果！",
            "") { dialog -> dialog.dismiss() }
    }

    /**
     * 2、默认Dialog:二个Button
     *
     * @param view View
     */
    fun showDefaultDialogTwo(view: View?) {
        DialogUtil.createDefaultDialog(this, "分享", "分享此接单码，您和乘客都将获得现金红包！",
            "立即分享", { dialog ->
                Toast.makeText(MyApplication.getApplication(), "立即分享", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            },
            "有钱不要") { dialog ->
            Toast.makeText(MyApplication.getApplication(), "有钱不要", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    /**
     * 3、自定义Dialog 基本使用
     *
     * @param view View
     */
    fun showBaseUseDialog(view: View?) {
        SYDialog.Builder(this)
            .setDialogView(R.layout.layout_dialog) //设置dialog布局
            .setAnimStyle(R.style.translate_style) //设置动画 默认没有动画
            .setScreenWidthP(0.85f) //设置屏幕宽度比例 0.0f-1.0f
            .setGravity(Gravity.CENTER) //设置Gravity
            .setWindowBackgroundP(0.2f) //设置背景透明度 0.0f-1.0f 1.0f完全不透明
            .setCancelable(true) //设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
            .setCancelableOutSide(true) //设置dialog外点击是否可以让dialog消失
            .setOnDismissListener { //监听dialog dismiss的回调
                toast("dismiss回调")
            }
            .setBuildChildListener { dialog, view, layoutRes ->
                //设置子View
                //dialog: IDialog
                //view： DialogView
                //layoutRes :Dialog的资源文件 如果一个Activity里有多个dialog 可以通过layoutRes来区分
                val editText = view.findViewById<EditText>(R.id.et_content)
                val btn_ok = view.findViewById<Button>(R.id.btn_ok)
                btn_ok.setOnClickListener {
                    var editTextStr: String? = null
                    editTextStr = if (!TextUtils.isEmpty(editText.text)) {
                        editText.text.toString()
                    } else {
                        "EditText输入为空"
                    }
                    dialog.dismiss()
                    Toast.makeText(MyApplication.getApplication(), editTextStr, Toast.LENGTH_SHORT)
                        .show()
                }
            }.show()
    }

    /**
     * 4、展示进度条
     *
     * @param view View
     */
    fun showLoadingDialog(view: View?) {
        DialogUtil.createLoadingDialog(this)
        Handler().postDelayed({ DialogUtil.closeLoadingDialog(this@CommonDialogActivity) }, 5000)
    }

    /**
     * 5、全屏广告弹窗
     *
     * @param view View
     */
    fun showAdDialog(view: View?) {
        SYDialog.Builder(this)
            .setDialogView(R.layout.layout_ad_dialog)
            .setWindowBackgroundP(0.5f)
            .setAnimStyle(0)
            .setBuildChildListener { dialog, view, layoutRes ->
                val iv_close = view.findViewById<ImageView>(R.id.iv_close)
                iv_close.setOnClickListener { dialog.dismiss() }
                val iv_ad = view.findViewById<ImageView>(R.id.iv_ad)
                iv_ad.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "点击广告", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
            }.show()
    }

    /**
     * 6、底部选择弹窗
     *
     * @param view View
     */
    fun showBottomDialog(view: View?) {
        SYDialog.Builder(this)
            .setDialogView(R.layout.layout_bottom_up)
            .setWindowBackgroundP(0.5f)
            .setAnimStyle(R.style.AnimUp)
            .setCancelableOutSide(true)
            .setCancelableOutSide(true)
            .setBuildChildListener { dialog, view, layoutRes ->
                val btn_take_photo = view.findViewById<Button>(R.id.btn_take_photo)
                btn_take_photo.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "拍照", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                val btn_select_photo = view.findViewById<Button>(R.id.btn_select_photo)
                btn_select_photo.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "相册选取", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
                val btn_cancel_dialog = view.findViewById<Button>(R.id.btn_cancel_dialog)
                btn_cancel_dialog.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
            .setScreenWidthP(1.0f)
            .setGravity(Gravity.BOTTOM).show()
    }

    /**
     * 7、分享dialog
     *
     * @param view View
     */
    fun showDialogShare(view: View?) {
        dialog = SYDialog.Builder(this)
            .setDialogView(R.layout.layout_share)
            .setWindowBackgroundP(0.5f)
            .setScreenWidthP(1.0f)
            .setGravity(Gravity.BOTTOM)
            .setCancelable(false)
            .setCancelableOutSide(false)
            .setAnimStyle(R.style.AnimUp)
            .setBuildChildListener { dialog, view, layoutRes ->
                val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this@CommonDialogActivity,
                    LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = ShareAdapter()
                val btn_cancel_dialog = view.findViewById<Button>(R.id.btn_cancel_dialog)
                btn_cancel_dialog.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }.show()
    }

    /**
     * 8、支持空视图 错误视图 失败重试
     */
    fun showRetryDialog(view: View?) {
        SYDialog.Builder(this)
            .setDialogView(R.layout.layout_dialog_retry)
            .setAnimStyle(0)
            .setScreenHeightP(0.7f)
            .setCancelableOutSide(true)
            .setBuildChildListener { dialog, parent, layoutRes ->
                val loadingView: DialogLoadingView = parent.findViewById(R.id.loading_view)
                //展示加载中
                loadingView.showLoading()
                //支持失败重试
                loadingView.setRetryListener { //模拟成功
                    loadingView.hide()
                }
                val iv_close = parent.findViewById<ImageView>(R.id.iv_close)
                iv_close.setOnClickListener { dialog.dismiss() }

                //模拟请求失败
                Handler().postDelayed({ loadingView.showError() }, 1500)
            }
            .show()
    }

    /**
     * 9、全局管理dialog
     *
     * @param view View
     */
    fun showGlobalDialog(view: View?) {

        //Build第一个Dialog
        val builder1 = SYDialog.Builder(this)
            .setDialogView(R.layout.layout_ad_dialog)
            .setWindowBackgroundP(0.5f)
            .setBuildChildListener { dialog, view, layoutRes ->
                val iv_close = view.findViewById<ImageView>(R.id.iv_close)
                iv_close.setOnClickListener { dialog.dismiss() }
                val iv_ad = view.findViewById<ImageView>(R.id.iv_ad)
                iv_ad.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "点击广告", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
            }
        //Build第二个Dialog
        val builder2 = SYDialog.Builder(this)
            .setDialogView(R.layout.layout_bottom_up)
            .setWindowBackgroundP(0.5f)
            .setAnimStyle(R.style.AnimUp)
            .setBuildChildListener { dialog, view, layoutRes ->
                val btn_take_photo = view.findViewById<Button>(R.id.btn_take_photo)
                btn_take_photo.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "拍照", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                val btn_select_photo = view.findViewById<Button>(R.id.btn_select_photo)
                btn_select_photo.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "相册选取", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }
                val btn_cancel_dialog = view.findViewById<Button>(R.id.btn_cancel_dialog)
                btn_cancel_dialog.setOnClickListener {
                    Toast.makeText(MyApplication.getApplication(), "取消", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
            .setScreenWidthP(1.0f)
            .setGravity(Gravity.BOTTOM)
        //添加第一个Dialog
        SYDialogsManager.getInstance().requestShow(DialogWrapper(builder1))
        //添加第二个Dialog
        SYDialogsManager.getInstance().requestShow(DialogWrapper(builder2))
    }

    internal inner class ShareAdapter : RecyclerView.Adapter<ShareHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_share, parent, false)
            return ShareHolder(view)
        }

        override fun onBindViewHolder(holder: ShareHolder, position: Int) {
            holder.ll_share.setOnClickListener {
                Toast.makeText(MyApplication.getApplication(), "分享", Toast.LENGTH_SHORT).show()
                dismissDialog()
            }
        }

        override fun getItemCount(): Int {
            return 8
        }

        internal inner class ShareHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var ll_share: LinearLayout

            init {
                ll_share = itemView.findViewById(R.id.ll_share)
            }
        }
    }

    /**
     * 关闭弹窗 注意dialog=null;防止内存泄漏
     */
    private fun dismissDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}