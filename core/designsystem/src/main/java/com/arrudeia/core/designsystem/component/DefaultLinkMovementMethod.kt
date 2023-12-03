package com.arrudeia.core.designsystem.component

import android.text.Layout
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.MotionEvent
import android.widget.TextView


/**
 * Set this on a textview and then you can potentially open links locally if applicable
 */
class DefaultLinkMovementMethod(private val mOnLinkClickedListener: OnLinkClickedListener) :
    LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action

        //http://stackoverflow.com/questions/1697084/handle-textview-link-click-in-my-android-app
        if (action == MotionEvent.ACTION_UP) {
            var x = event.x.toFloat()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout: Layout = widget.layout
            val line: Int = layout.getLineForVertical(y)
            val off: Int = layout.getOffsetForHorizontal(line, x)
            val link = buffer.getSpans(off, off, URLSpan::class.java)
            if (link.size != 0) {
                val url = link[0].url
                val handled = mOnLinkClickedListener.onLinkClicked(url)
                return if (handled) {
                    true
                } else super.onTouchEvent(widget, buffer, event)
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }

    interface OnLinkClickedListener {
        fun onLinkClicked(url: String?): Boolean
    }
}