package com.prokkypew.oversentry.model.parser

import com.prokkypew.oversentry.model.BattleNetProfile
import com.prokkypew.oversentry.model.PatchNote
import java.util.*

/**
 * Created by alexander.roman on 23.01.2017.
 */
interface BlizzParser {
    fun parsePatchNotes(url: String): ArrayList<PatchNote>
    fun parsePlayerStats(btag: String, platform: String, region: String): BattleNetProfile?
}