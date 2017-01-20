package com.prokkypew.oversentry.features.patchnotes

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.utils.attrAsDimen
import com.prokkypew.oversentry.utils.getColor
import com.prokkypew.oversentry.utils.setHorizontalDivider
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


/**
 * Created by alexander.roman on 23.12.2016.
 */
class PatchNotesActivityUI(val listAdapter: PatchNotesActivity.PatchNotesAdapter) : AnkoComponent<PatchNotesActivity> {
    companion object {
        val PROGRESSBAR_ID = View.generateViewId()
        val TOOLBAR_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<PatchNotesActivity>) = ui.apply {
        linearLayout {
            toolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                lparams(width = matchParent, height = ctx.attrAsDimen(R.attr.actionBarSize))
                backgroundColor = getColor(R.color.colorPrimary)
                elevation = 4f
                id = TOOLBAR_ID
                transitionName = "titleTransition"
            }
            backgroundColor = getColor(R.color.backgroundColor)
            orientation = LinearLayout.VERTICAL
            lparams(width = matchParent, height = matchParent)
            relativeLayout {
                padding = dip(16)
                lparams(width = matchParent, height = matchParent, weight = 1f)
                recyclerView {
                    lparams(width = matchParent, height = matchParent)
                    adapter = listAdapter
                    layoutManager = LinearLayoutManager(ctx)
                    itemAnimator = DefaultItemAnimator()
                    setHorizontalDivider(R.drawable.horizontal_divider)
                }
                progressBar {
                    id = PROGRESSBAR_ID
                    visibility = View.INVISIBLE
                }.lparams {
                    centerInParent()
                }
            }
        }
    }.view
}