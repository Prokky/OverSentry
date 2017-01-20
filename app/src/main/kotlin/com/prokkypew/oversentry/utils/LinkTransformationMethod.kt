package com.prokkypew.oversentry.utils

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.TransformationMethod
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView
import com.prokkypew.oversentry.R


/**
 * Created by alexander.roman on 18.01.2017.
 */
class LinkTransformationMethod : TransformationMethod {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        if (view is TextView) {
            if (view.text == null || view.text !is Spannable) {
                return source
            }
            val spannable = view.text as Spannable
            val spans = spannable.getSpans(0, view.length(), URLSpan::class.java)
            for (i in spans.indices.reversed()) {
                val oldSpan = spans[i]
                val start = spannable.getSpanStart(oldSpan)
                val end = spannable.getSpanEnd(oldSpan)
                val url = oldSpan.url
                spannable.removeSpan(oldSpan)
                spannable.setSpan(CustomTabsUrlSpan(view.context, url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            return view.text
        }
        return source
    }

    override fun onFocusChanged(view: View, sourceText: CharSequence, focused: Boolean, direction: Int, previouslyFocusedRect: Rect) {

    }

    inner class CustomTabsUrlSpan(var ctx: Context, url: String) : URLSpan(url) {
        override fun onClick(widget: View) {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
            builder.setStartAnimations(ctx, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(ctx, R.anim.slide_in_left, R.anim.slide_out_right)
            builder.build().launchUrl(ctx, Uri.parse(url))
        }

        override fun updateDrawState(textPaint: TextPaint) {
            textPaint.color = ContextCompat.getColor(ctx, R.color.colorAccent)
            textPaint.isUnderlineText = true
        }
    }
}