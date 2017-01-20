package com.prokkypew.oversentry.model

import com.prokkypew.oversentry.model.BlizzParser.Companion.PATCH_NOTES_BASE_URL
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by alexander.roman on 16.01.2017.
 */

class ParserModelImpl : ParserModel {
    override fun getPatchNotes(): Observable<ArrayList<PatchNote>> {
        val observable = Observable.create(object : Observable.OnSubscribe<ArrayList<PatchNote>> {
            override fun call(t: Subscriber<in ArrayList<PatchNote>>?) {
                val list = BlizzParser().parsePatchNotes(PATCH_NOTES_BASE_URL)
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
                val profile = BlizzParser().parsePlayerStats(nickname, platform, region)
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
