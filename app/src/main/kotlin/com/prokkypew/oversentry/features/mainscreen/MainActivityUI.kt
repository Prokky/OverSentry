package com.prokkypew.oversentry.features.mainscreen

import android.graphics.Color
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.utils.attrAsDimen
import com.prokkypew.oversentry.utils.getColor
import com.prokkypew.oversentry.utils.setHorizontalDivider
import com.prokkypew.oversentry.utils.setSpinnerSelectedColor
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView


/**
 * Created by alexander.roman on 23.12.2016.
 */
class MainActivityUI(val searchesAdapter: MainActivity.SearchesAdapter, val click: View.OnClickListener) : AnkoComponent<MainActivity> {
    companion object {
        val PLATFORM_SPINNER_ID = View.generateViewId()
        val REGION_SPINNER_ID = View.generateViewId()
        val SEARCH_PROGRESSBAR_ID = View.generateViewId()
        val SEARCH_EDITTEXT_ID = View.generateViewId()
        val SEARCH_BUTTON_ID = View.generateViewId()
        val PATCHNOTES_BUTTON_ID = View.generateViewId()
        val TOOLBAR_ID = View.generateViewId()
    }

    override fun createView(ui: AnkoContext<MainActivity>) = ui.apply {
        linearLayout {
            toolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                lparams(width = matchParent, height = ctx.attrAsDimen(R.attr.actionBarSize))
                backgroundColor = getColor(R.color.colorPrimary)
                elevation = 4f
                id = TOOLBAR_ID
            }
            lparams(width = matchParent, height = matchParent)
            backgroundColor = getColor(R.color.backgroundColor)
            orientation = LinearLayout.VERTICAL
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(16)
                }
                horizontalPadding = dip(16)
                isFocusableInTouchMode = true
                orientation = LinearLayout.HORIZONTAL
                editText {
                    id = SEARCH_EDITTEXT_ID
                    lparams(width = matchParent, height = wrapContent, weight = 1f)
                    textColor = getColor(R.color.colorPrimary)
                    hintTextColor = Color.GRAY
                    horizontalPadding = dip(16)
                    hint = resources.getString(R.string.btag_hint)
                    singleLine = true
                }

                progressBar {
                    id = SEARCH_PROGRESSBAR_ID
                    visibility = View.INVISIBLE
                }
                spinner {
                    id = PLATFORM_SPINNER_ID
                    lparams {
                        marginStart = dip(-15)
                        marginEnd = dip(-10)
                    }
                    adapter = ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, arrayListOf("PC", "XBL", "PSN"))
                    setSelection(0)
                    setSpinnerSelectedColor(getColor(R.color.colorPrimary))
                }
                spinner {
                    id = REGION_SPINNER_ID
                    lparams {
                        marginStart = dip(-15)
                        marginEnd = dip(-10)
                    }
                    adapter = ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, arrayListOf("EU", "US", "KR", "CH"))
                    setSelection(0)
                    setSpinnerSelectedColor(getColor(R.color.colorPrimary))
                }
            }
            button {
                id = SEARCH_BUTTON_ID
                lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(16)
                    horizontalMargin = dip(16)
                }
                text = ctx.getString(R.string.search)
                backgroundColor = getColor(R.color.colorPrimary)
                textColor = Color.WHITE
                setOnClickListener(click)
            }
            recyclerView {
                lparams(width = matchParent, height = 0, weight = 1f) {
                    verticalMargin = dip(8)
                    horizontalPadding = dip(16)
                }
                setHorizontalDivider(R.drawable.horizontal_divider)
                layoutManager = LinearLayoutManager(ctx)
                itemAnimator = DefaultItemAnimator()
                adapter = searchesAdapter
            }
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)
                gravity = Gravity.RIGHT
                button(ctx.getString(R.string.latest_patches)) {
                    id = PATCHNOTES_BUTTON_ID
                    background = null
                    textColor = getColor(R.color.colorPrimary)
                    setOnClickListener(click)
                    transitionName = "titleTransition"
                }
            }
        }
    }.view
}