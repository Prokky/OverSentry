package com.prokkypew.oversentry.features.patchnotes

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.prokkypew.oversentry.MainApplication
import com.prokkypew.oversentry.model.BlizzParser
import com.prokkypew.oversentry.model.PatchNote
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import java.util.*
import javax.inject.Inject

/**
 * Created by alexander.roman on 26.12.2016.
 */
@InjectViewState
class PatchNotesActivityPresenter : MvpPresenter<PatchNotesActivityView>() {
    init {
        Log.d("INIT", "YAY")
        MainApplication().getComponent().inject(this)
    }

    @Inject
    lateinit var compositeSubscription: CompositeSubscription
    @Inject
    lateinit var parser: BlizzParser

    fun loadPatchNotes() {
        compositeSubscription.add(parser.getPatchNotes().subscribe(object : Subscriber<ArrayList<PatchNote>>() {
            override fun onNext(t: ArrayList<PatchNote>?) {
                viewState.patchNotesLoaded(t!!)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                viewState.showError("Failed to load patch notes")
            }
        }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }
}