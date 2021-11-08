package org.ninetripods.mq.study.jetpack.datastore

/**
 * DataStore存储：包括 Preference DataStore、Proto DataStore
 */
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.datastore.preferences.PreferenceKeys
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookSpModel
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.Type
import org.ninetripods.mq.study.kotlin.ktx.id

class DataStoreActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mBtnSaveSp: Button by id(R.id.btn_sp_save)
    private val mBtnGetSp: Button by id(R.id.btn_sp_get)
    private val mTvContentSp: TextView by id(R.id.tv_sp_content)

    private val mBtnSavePf: Button by id(R.id.btn_pf_save)
    private val mBtnGetPf: Button by id(R.id.btn_pf_get)
    private val mTvContentPf: TextView by id(R.id.tv_pf_content)
    private val mBtnMigrationPf: Button by id(R.id.btn_migration_pf)

    private val mBtnSavePt: Button by id(R.id.btn_pt_save)
    private val mBtnGetPt: Button by id(R.id.btn_pt_get)
    private val mTvContentPt: TextView by id(R.id.tv_pt_content)
    private val mBtnMigrationPt: Button by id(R.id.btn_migration_pt)

    private lateinit var mBookViewModel: BookViewModel

    override fun setContentView() {
        setContentView(R.layout.activity_data_store)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack DataStore", true)

        //创建ViewModel
        mBookViewModel = ViewModelProvider(
            this,
            BookRepoFactory(BookRepo.getInstance(this))
        ).get(BookViewModel::class.java)
    }

    override fun initEvents() {
        mBtnSaveSp.setOnClickListener { saveSpData() }
        mBtnGetSp.setOnClickListener { getSpData() }
        mBtnSavePf.setOnClickListener { savePfDataStore() }
        mBtnGetPf.setOnClickListener { getPfDataStore() }
        mBtnSavePt.setOnClickListener { saveProtoDataStore() }
        mBtnGetPt.setOnClickListener { getProtoDataStore() }
    }

    /**
     * SP存数据
     */
    private fun saveSpData() {
        val book = BookSpModel(
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
            mBookViewModel.bookFlow.collect {
                mTvContentSp.text = it.toString()
            }
        }
    }

    /**
     * Preferences DataStore存数据
     */
    private fun savePfDataStore() {
        val savePfStr = "Hello Preferences DataStore"
        lifecycleScope.launch {
            bookDataStorePf.edit { preference ->
                preference[PreferenceKeys.KEY_BOOK_NAME] = savePfStr
            }
            toast("${savePfStr}存入成功")
        }
    }

    /**
     * Preferences DataStore取数据
     */
    private fun getPfDataStore() {
        //Preferences.Key<T>
        lifecycleScope.launch {
            val pfFlow: Flow<String> =
                bookDataStorePf.data.map { preferences ->
                    //preferences[PreferenceKeys.KEY_STRING] ?: "default"
                    preferences[PreferenceKeys.KEY_BOOK_NAME] ?: "default"
                }
            pfFlow.collect {
                mTvContentPf.text = it
            }
        }
    }

    /**
     * 使用Proto DataStore存储数据
     */
    private fun saveProtoDataStore() {
        //构建BookProto.BookSpModel
        val bookInfo = BookProto.Book.getDefaultInstance().toBuilder()
            .setBookName("Android BookSpModel")
            .setPrice(10f)
            .setSeason(BookProto.Season.AUTUMN)
            .build()
        lifecycleScope.launch {
            bookDataStorePt.updateData { bookInfo }
            toast("${bookInfo}存入成功")
        }
    }

    /**
     * 使用Proto DataStore取数据
     */
    private fun getProtoDataStore() {
        lifecycleScope.launch {
            //DataStore.data返回Flow<T>
            val bookFlow: Flow<BookProto.Book> = bookDataStorePt.data
            bookFlow.collect {
                mTvContentPt.text = it.toString()
            }
        }
    }
}