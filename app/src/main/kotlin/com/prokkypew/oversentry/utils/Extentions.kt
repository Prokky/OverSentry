package com.prokkypew.oversentry.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import org.jetbrains.anko.onItemSelectedListener

/**
 * Created by alexander.roman on 17.01.2017.
 */
fun Spinner.setSpinnerSelectedColor(color: Int) {
    onItemSelectedListener {
        onItemSelected { adapterView, view, i, l -> (adapterView?.getChildAt(0) as TextView).setTextColor(color) }
    }
}

fun View.getColor(colorResId: Int): Int {
    return ContextCompat.getColor(context, colorResId)
}

fun RecyclerView.setHorizontalDivider(resId: Int) {
    val horizontalDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val horizontalDivider = ContextCompat.getDrawable(context, resId)
    horizontalDecoration.setDrawable(horizontalDivider)
    addItemDecoration(horizontalDecoration)
}

fun Context.attrAsDimen(value: Int): Int {
    return TypedValue.complexToDimensionPixelSize(attribute(value).data, resources.displayMetrics)
}

fun Context.attribute(value: Int): TypedValue {
    val ret = TypedValue()
    theme.resolveAttribute(value, ret, true)
    return ret
}

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int): List<T> {
    return subList(fromIndex, if (toIndex > size) size else toIndex)
}