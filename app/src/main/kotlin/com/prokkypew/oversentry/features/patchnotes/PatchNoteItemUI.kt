package com.prokkypew.oversentry.features.patchnotes

import android.graphics.Color
import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.prokkypew.oversentry.utils.LinkTransformationMethod
import org.jetbrains.anko.*


/**
 * Created by alexander.roman on 23.12.2016.
 */
class PatchNoteItemUI : AnkoComponent<ViewGroup> {
    companion object {
        val TITLE_ID = View.generateViewId()
        val TV_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                textView {
                    id = TITLE_ID
                    lparams(width = matchParent, height = wrapContent)
                    textSize = 20f
                    textColor = Color.WHITE
                    topPadding = dip(16)
                    typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                }
                textView {
                    id = TV_ID
                    lparams(width = matchParent, height = wrapContent)
                    textSize = 14f
                    textColor = Color.WHITE
                    topPadding = dip(16)
                    bottomPadding = dip(16)
                    maxLines = 6
                    transformationMethod = LinkTransformationMethod()
                    movementMethod = LinkMovementMethod.getInstance()

                    onClick {
                        if (maxLines == 6)
                            maxLines = 999999
                        else
                            maxLines = 6
                    }
                }

            }
        }

    }
}