package com.prokkypew.oversentry.features.mainscreen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.prokkypew.oversentry.MainApplication
import com.prokkypew.oversentry.model.BattleNetProfile
import com.prokkypew.oversentry.model.ParserModel
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import rx.Subscriber
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject


/**
 * Created by alexander.roman on 26.12.2016.
 */
@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    @Inject
    lateinit var compositeSubscription: CompositeSubscription
    @Inject
    lateinit var model: ParserModel
    var realm: Realm = Realm.getDefaultInstance()

    init {
        MainApplication().getComponent().inject(this)
    }

    fun searchPlayer(nickname: String, platform: String, region: String) {
        compositeSubscription.add(model.getPlayer(nickname, platform, region).subscribe(object : Subscriber<BattleNetProfile>() {
            override fun onNext(p: BattleNetProfile?) {
                if (p == null)
                    viewState.showError("Not found")
                else {
                    realm.executeTransaction {
                        realm.copyToRealmOrUpdate(p)
                        viewState.playerFound(realm.where(BattleNetProfile::class.java).findAllSorted("parsedTime", Sort.DESCENDING))
                    }
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                viewState.showError("Not found")
            }
        }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
        realm.close()
    }

    fun loadSearches(): RealmResults<BattleNetProfile> {
        return realm.where(BattleNetProfile::class.java).findAllSorted("parsedTime", Sort.DESCENDING)
    }
}