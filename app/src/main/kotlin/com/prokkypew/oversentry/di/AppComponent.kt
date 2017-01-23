package com.prokkypew.oversentry.di

import com.prokkypew.oversentry.features.mainscreen.MainActivityPresenter
import com.prokkypew.oversentry.features.patchnotes.PatchNotesActivityPresenter
import dagger.Component
import javax.inject.Singleton


/**
 * Created by alexander.roman on 19.01.2017.
 */
@Singleton
@Component(modules = arrayOf(PresenterModule::class))
interface AppComponent {
    fun inject(mainActivityPresenter: MainActivityPresenter)
    fun inject(patchNotesActivityPresenter: PatchNotesActivityPresenter)
}