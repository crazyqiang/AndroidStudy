package org.ninetripods.mq.study.jetpack_compose

import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack_compose.compose.ComposeExampleActivity
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

/**
 * Created by mq on 2023/11/15
 */
class JetpackComposeFragment : BaseFragment() {

    private val mTvState: TextView by id(R.id.tv_remember_state)

    override fun getLayoutId(): Int = R.layout.fragment_jetpack_compose

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mTvState.setOnClickListener {
            NavitateUtil.startActivity(activity, ComposeExampleActivity::class.java)
        }
    }

}