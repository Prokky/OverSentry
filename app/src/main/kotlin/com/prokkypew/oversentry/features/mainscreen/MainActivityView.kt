package com.prokkypew.oversentry.features.mainscreen

import com.arellomobile.mvp.MvpView
import com.prokkypew.oversentry.model.BattleNetProfile
import io.realm.RealmResults

/**
 * Created by alexander.roman on 17.01.2017.
 */
interface MainActivityView : MvpView {
    fun playerFound(results: RealmResults<BattleNetProfile>)
    fun showError(errorText: String)
}