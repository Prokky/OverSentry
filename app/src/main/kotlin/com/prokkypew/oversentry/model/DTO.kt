package com.prokkypew.oversentry.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by alexander.roman on 16.01.2017.
 */
open class PatchNote(
        open var title: String,
        open var detail: String)

open class BattleNetProfile(@PrimaryKey var battleTag: String, var platform: String, var region: String, var avatarUrl: String) : RealmObject() {
    constructor() : this("", "", "", "")

    var nickName: String? = ""
    var parsedTime: Long? = 0
}