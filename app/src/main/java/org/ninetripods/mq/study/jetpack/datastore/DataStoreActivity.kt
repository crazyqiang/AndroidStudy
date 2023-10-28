package org.ninetripods.mq.study.jetpack.datastore

/**
 * DataStore存储：包括 Preference DataStore、Proto DataStore
 */
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.CommonWebviewActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookModel
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.Type
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.Constant

class DataStoreActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mBtnSaveSp: Button by id(R.id.btn_sp_save)
    private val mBtnGetSp: Button by id(R.id.btn_sp_get)
    private val mTvContentSp: TextView by id(R.id.tv_sp_content)

    private val mBtnSavePf: Button by id(R.id.btn_pf_save)
    private val mBtnGetPf: Button by id(R.id.btn_pf_get)
    private val mTvContentPf: TextView by id(R.id.tv_pf_content)

    private val mBtnSavePt: Button by id(R.id.btn_pt_save)
    private val mBtnGetPt: Button by id(R.id.btn_pt_get)
    private val mTvContentPt: TextView by id(R.id.tv_pt_content)

    private val mBookViewModel: BookViewModel by viewModels()

    override fun setContentView() {
        setContentView(R.layout.activity_data_store)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack DataStore", true, true, TYPE_BLOG)
    }

    override fun initEvents() {
        mBtnSaveSp.setOnClickListener { saveSpData() }
        mBtnGetSp.setOnClickListener { getSpData() }

        mBtnSavePf.setOnClickListener { savePfDataStore() }
        mBtnGetPf.setOnClickListener { getPfDataStore() }

        mBtnSavePt.setOnClickListener { saveProtoDataStore() }
        mBtnGetPt.setOnClickListener { getProtoDataStore() }
    }

    override fun openWebview() {
        CommonWebviewActivity.webviewEntrance(this, Constant.BLOG_JETPACK_DATASTORE)
    }

    /**
     * SP存数据
     */
    private fun saveSpData() {
        val book = BookModel(
            name = "The Avengers",
            price = (1..10).random().toFloat(), //这里价格每次点击都会变化，为了展示UI层能随时监听数据变化
            type = Type.ENGLISH
        )
        mBookViewModel.saveSpData(book)
        toast("sp存数据：$book")
    }

    /**
     * SP获取数据
     */
    private fun getSpData() {
        lifecycleScope.launch {
            mBookViewModel.bookSpFlow.collect {
                mTvContentSp.text = it.toString()
            }
        }
    }

    /**
     * Preferences DataStore存数据
     */
    private fun savePfDataStore() {
        val book = BookModel(
            name = "Hello Preferences DataStore",
            price = (1..10).random().toFloat(), //这里价格每次点击都会变化，为了展示UI层能随时监听数据变化
            type = Type.MATH
        )
        mBookViewModel.savePfData(book)
        toast("${book}存入成功")
    }

    /**
     * Preferences DataStore取数据
     */
    private fun getPfDataStore() {
        lifecycleScope.launch {
            mBookViewModel.bookPfFlow.collect {
                mTvContentPf.text = it.toString()
            }
        }
    }

    /**
     * 使用Proto DataStore存储数据
     */
    private fun saveProtoDataStore() {
        //构建BookProto.Book
        val bookInfo = BookProto.Book.getDefaultInstance().toBuilder()
            .setName("Hello Proto DataStore")
            .setPrice(20f)
            .setType(BookProto.Type.ENGLISH)
            .build()
        mBookViewModel.savePtData(bookInfo)
        toast("${bookInfo}存入成功")
    }

    /**
     * 使用Proto DataStore取数据
     */
    private fun getProtoDataStore() {
        lifecycleScope.launch {
            mBookViewModel.bookPtFlow.collect {
                mTvContentPt.text = it.toString()
            }
        }
    }
}