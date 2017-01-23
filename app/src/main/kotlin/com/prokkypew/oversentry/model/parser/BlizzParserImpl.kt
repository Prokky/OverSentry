package com.prokkypew.oversentry.model.parser

import com.prokkypew.oversentry.model.BattleNetProfile
import com.prokkypew.oversentry.model.PatchNote
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.util.*

/**
 * Created by alexander.roman on 16.01.2017.
 */
class BlizzParserImpl : BlizzParser {
    companion object {
        val BATTLE_NET_URL = "https://us.battle.net"
        val PATCH_NOTES_BASE_URL = BATTLE_NET_URL + "/forums/en/overwatch/21446648/"
        val PATCHNOTE_CONCAT_1 = "<span class=\"underline\"><strong>PATCH HIGHLIGHTS"
        val PATCHNOTE_CONCAT_2 = "Technical Support</a> forum."
        var STAT_URL = "https://playoverwatch.com/en-us/career/"
    }

    override fun parsePatchNotes(url: String): ArrayList<PatchNote> {
        val list = ArrayList<PatchNote>()
        val doc = Jsoup.connect(url).get()
        val posts = doc.select(".ForumTopic-details")
        posts.forEach {
            if (it.toString().contains("[ALL]")) {
                val title = it.select(".ForumTopic-title").first().text().replace("[ALL]", "").trim()
                val topicUrl = BATTLE_NET_URL + it.select("[href]").attr("href")
                val notePosts = Jsoup.connect(topicUrl).get().select(".TopicPost-bodyContent")
                val patchNote = StringBuilder()
                notePosts.forEach {
                    patchNote.append(it.toString()).append("<br>")
                }
                var concatPos = 0
                if (patchNote.contains(PATCHNOTE_CONCAT_1))
                    concatPos = patchNote.indexOf(PATCHNOTE_CONCAT_1)
                else if (patchNote.contains(PATCHNOTE_CONCAT_2))
                    concatPos = patchNote.indexOf(PATCHNOTE_CONCAT_2) + PATCHNOTE_CONCAT_2.length
                list.add(PatchNote(title, patchNote.substring(concatPos, patchNote.length)))
            }
        }
        return list
    }

    override fun parsePlayerStats(btag: String, platform: String, region: String): BattleNetProfile? {
        var url = STAT_URL
        if (platform.equals("pc", true))
            url += "pc/" + region.toLowerCase() + "/" + btag.replace("#", "-")
        else
            url += platform.toLowerCase() + "/" + btag
        try {
            val doc = Jsoup.connect(url).get()
            if (doc.select(".masthead").isEmpty())
                return null
            val nickName = doc.select(".header-masthead").text()
            val avatarUrl = doc.select(".player-portrait").first().attr("src")
            val profile = BattleNetProfile(btag, platform, region, avatarUrl)
            profile.parsedTime = System.currentTimeMillis()
            profile.nickName = nickName
            //TODO PARSE MORE
            return profile
        } catch (e: HttpStatusException) {
            return null
        }
    }
}