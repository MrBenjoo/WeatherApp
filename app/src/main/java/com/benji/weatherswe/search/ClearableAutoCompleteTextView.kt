package com.benji.weatherswe.search


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import com.benji.weatherswe.R


class ClearableAutoCompleteTextView : AppCompatAutoCompleteTextView, OnTouchListener {

    lateinit var onClearListener: OnClearListener

    private var imgClearButton =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_clear, null)

    override fun onTouch(v: View, event: MotionEvent): Boolean =
        when (isClearBtnPressed(event) && eventActionDown(event)) {
            true -> {
                onClearListener.onClear()
                true
            }
            false -> false
        }

    private fun isClearBtnPressed(event: MotionEvent): Boolean =
        (event.x > width - paddingRight - imgClearButton!!.intrinsicWidth)


    private fun eventActionDown(event: MotionEvent): Boolean =
        event.action == MotionEvent.ACTION_DOWN


    interface OnClearListener {
        fun onClear()
    }

    /* Required methods, not used in this implementation */
    constructor(context: Context) : super(context) {
        init()
    }

    /* Required methods, not used in this implementation */
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    /* Required methods, not used in this implementation */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        // Set the bounds of the button
        this.setCompoundDrawablesWithIntrinsicBounds(
            null, null,
            imgClearButton, null
        )

        this.setOnTouchListener(this)
    }

}

