package com.prokkypew.oversentry.model

import rx.Observable
import java.util.*

/**
 * Created by alexander.roman on 19.01.2017.
 */
interface ParserModel {
    fun getPatchNotes(): Observable<ArrayList<PatchNote>>
    fun getPlayer(nickname: String, platform: String, region: String): Observable<BattleNetProfile>
}