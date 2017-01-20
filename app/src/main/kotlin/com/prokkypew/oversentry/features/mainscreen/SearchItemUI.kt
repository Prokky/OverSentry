package com.prokkypew.oversentry.features.mainscreen

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.LinearLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.utils.getColor
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView


/**
 * Created by alexander.roman on 23.12.2016.
 */
class SearchItemUI : AnkoComponent<ViewGroup> {
    companion object {
        val NICKNAME_ID = View.generateViewId()
        val REGION_ID = View.generateViewId()
        val PLATFORM_ID = View.generateViewId()
        val AVATAR_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    verticalPadding = dip(8)
                }
                backgroundResource = R.drawable.search_bg
                isClickable = true
                orientation = LinearLayout.HORIZONTAL
                draweeView {
                    lparams(width = dip(60), height = dip(60)) {
                        rightMargin = dip(16)
                    }
                    id = AVATAR_ID
                }
                textView {
                    lparams(width = 0, height = dip(60), weight = 1f) {
                        rightMargin = dip(16)
                    }
                    textColor = getColor(R.color.colorPrimary)
                    gravity = Gravity.CENTER_VERTICAL
                    textSize = 20f
                    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                    id = NICKNAME_ID
                }
                textView {
                    lparams(width = dip(60), height = dip(60)) {
                        rightMargin = dip(16)
                    }
                    textColor = getColor(R.color.colorPrimary)
                    gravity = Gravity.CENTER
                    id = PLATFORM_ID
                }
                textView {
                    lparams(width = dip(60), height = dip(60))
                    textColor = getColor(R.color.colorPrimary)
                    gravity = Gravity.CENTER
                    id = REGION_ID
                }
            }
        }

    }
}

inline fun ViewManager.draweeView(theme: Int = 0) = draweeView(theme) {}
inline fun ViewManager.draweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit) = ankoView({ SimpleDraweeView(it) }, theme, init)