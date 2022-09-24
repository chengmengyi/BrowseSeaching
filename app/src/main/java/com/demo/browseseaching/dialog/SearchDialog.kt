package com.demo.browseseaching.dialog

import android.view.Gravity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.demo.browseseaching.R
import com.demo.browseseaching.base.BaseDialogFragment
import com.demo.browseseaching.eventbus.EventbusBean
import com.demo.browseseaching.eventbus.EventbusCode
import kotlinx.android.synthetic.main.activity_search.*

class SearchDialog:BaseDialogFragment(R.layout.activity_search) {
    override fun onStart() {
        super.onStart()
        mWindow?.setGravity(Gravity.BOTTOM)
        mWindow?.setWindowAnimations(R.style.BottomAnimation)
    }

    override fun onView() {
        immersionBar?.apply {
            statusBarColor(R.color.color_5854BE)
            statusBarView(top_view)
            init()
        }

        tv_cancel.setOnClickListener { dismiss() }

        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(textView: TextView?, actionId: Int, keyEvent: KeyEvent?): Boolean {
                if ((actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEARCH) && keyEvent != null) {
                    EventbusBean(EventbusCode.SEARCH_URL, str = et_search.text.toString()).send()
                    dismiss()
                    return true
                }
                return false
            }
        })
    }
}