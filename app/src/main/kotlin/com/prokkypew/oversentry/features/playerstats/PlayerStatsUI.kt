package com.prokkypew.oversentry.features.playerstats

import android.view.View
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.utils.attrAsDimen
import com.prokkypew.oversentry.utils.getColor
import org.jetbrains.anko.*


/**
 * Created by alexander.roman on 23.12.2016.
 */
class PlayerStatsUI : AnkoComponent<PlayerStatsActivity> {
    companion object {
        val TOOLBAR_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<PlayerStatsActivity>) = ui.apply {
        linearLayout {
            toolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                lparams(width = matchParent, height = ctx.attrAsDimen(R.attr.actionBarSize))
                backgroundColor = getColor(R.color.colorPrimary)
                elevation = 4f
                id = TOOLBAR_ID
            }
            backgroundColor = getColor(R.color.backgroundColor)
        }
    }.view
}