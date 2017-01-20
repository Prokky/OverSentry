package com.prokkypew.oversentry.features.playerstats

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import com.arellomobile.mvp.MvpActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

/**
 * Created by alexander.roman on 19.01.2017.
 */
class PlayerStatsActivity : MvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerStatsUI().setContentView(this)
        setActionBar(find<Toolbar>(PlayerStatsUI.TOOLBAR_ID))
        actionBar.setDisplayHomeAsUpEnabled(true)
        title = intent.getStringExtra("btag")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}