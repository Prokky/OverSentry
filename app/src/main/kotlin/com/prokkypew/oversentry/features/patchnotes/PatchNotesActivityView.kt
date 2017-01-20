package com.prokkypew.oversentry.features.patchnotes

import com.arellomobile.mvp.MvpView
import com.prokkypew.oversentry.model.PatchNote
import java.util.*

/**
 * Created by alexander.roman on 26.12.2016.
 */
interface PatchNotesActivityView : MvpView {
    fun patchNotesLoaded(notes: ArrayList<PatchNote>)
    fun showError(errorText: String)
}