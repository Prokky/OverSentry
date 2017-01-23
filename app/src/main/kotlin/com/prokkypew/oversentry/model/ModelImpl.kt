package com.prokkypew.oversentry.model

import com.prokkypew.oversentry.MainApplication
import com.prokkypew.oversentry.model.parser.BlizzParser
import com.prokkypew.oversentry.model.parser.BlizzParserImpl.Companion.PATCH_NOTES_BASE_URL
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by alexander.roman on 16.01.2017.
 */

class ModelImpl : Model {
    init {
        MainApplication().getComponent().inject(this)
    }

    @Inject
    lateinit var parser: BlizzParser

    override fun getPatchNotes(): Observable<ArrayList<PatchNote>> {
        val observable = Observable.create(object : Observable.OnSubscribe<ArrayList<PatchNote>> {
            override fun call(t: Subscriber<in ArrayList<PatchNote>>?) {
                val list = parser.parsePatchNotes(PATCH_NOTES_BASE_URL)
                t?.onNext(list)
                t?.onCompleted()
            }
        })

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPlayer(nickname: String, platform: String, region: String): Observable<BattleNetProfile> {
        val observable = Observable.create(object : Observable.OnSubscribe<BattleNetProfile> {
            override fun call(t: Subscriber<in BattleNetProfile>?) {
                val profile = parser.parsePlayerStats(nickname, platform, region)
                if (profile == null)
                    t?.onError(RuntimeException())
                else
                    t?.onNext(profile)
                t?.onCompleted()
            }
        })

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
