package org.ninetripods.mq.study.fragment

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.matrix.SetPolyToPoly


/**
 * Poly Fragment
 * <a href="https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/Code/SetPolyToPoly.md">SetPolyToPoly</a>
 */
class SetPolyToPolyFragment : BaseFragment() {

    private val poly: SetPolyToPoly by id(R.id.poly)
    private val group: RadioGroup by id(R.id.group)

    override fun getLayoutId(): Int {
        return R.layout.layout_matrix_poly_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        group.setOnCheckedChangeListener { group, checkedId ->
            when (group.checkedRadioButtonId) {
                R.id.point0 -> poly.setTestPoint(0)
                R.id.point1 -> poly.setTestPoint(1)
                R.id.point2 -> poly.setTestPoint(2)
                R.id.point3 -> poly.setTestPoint(3)
                R.id.point4 -> poly.setTestPoint(4)
            }
        };
    }

}

