package com.prokkypew.oversentry.features.mainscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.drawee.view.SimpleDraweeView
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.features.mainscreen.MainActivityUI.Companion.PATCHNOTES_BUTTON_ID
import com.prokkypew.oversentry.features.mainscreen.MainActivityUI.Companion.SEARCH_BUTTON_ID
import com.prokkypew.oversentry.features.mainscreen.MainActivityUI.Companion.TOOLBAR_ID
import com.prokkypew.oversentry.features.mainscreen.SearchItemUI.Companion.AVATAR_ID
import com.prokkypew.oversentry.features.mainscreen.SearchItemUI.Companion.NICKNAME_ID
import com.prokkypew.oversentry.features.mainscreen.SearchItemUI.Companion.PLATFORM_ID
import com.prokkypew.oversentry.features.mainscreen.SearchItemUI.Companion.REGION_ID
import com.prokkypew.oversentry.features.patchnotes.PatchNotesActivity
import com.prokkypew.oversentry.features.playerstats.PlayerStatsActivity
import com.prokkypew.oversentry.model.BattleNetProfile
import com.prokkypew.oversentry.utils.SearchDiffCallback
import com.prokkypew.oversentry.utils.safeSubList
import io.realm.RealmResults
import org.jetbrains.anko.*
import java.util.*


class MainActivity : MvpActivity(), MainActivityView, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    private val searchEdit: EditText by lazy { find<EditText>(MainActivityUI.SEARCH_EDITTEXT_ID) }
    private val regionSpinner: Spinner by lazy { find<Spinner>(MainActivityUI.REGION_SPINNER_ID) }
    private val platformSpinner: Spinner by lazy { find<Spinner>(MainActivityUI.PLATFORM_SPINNER_ID) }
    private val searchProgressBar: ProgressBar by lazy { find<ProgressBar>(MainActivityUI.SEARCH_PROGRESSBAR_ID) }
    private val patchNotesButton: Button by lazy { find<Button>(PATCHNOTES_BUTTON_ID) }

    private val listAdapter: SearchesAdapter = SearchesAdapter()
    var searches: ArrayList<BattleNetProfile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searches.addAll(presenter.loadSearches().safeSubList(0, 5))
        MainActivityUI(listAdapter, this).setContentView(this)
        setActionBar(find<Toolbar>(TOOLBAR_ID))
        setTitle(R.string.app_name)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            SEARCH_BUTTON_ID -> {
                searchProgressBar.visibility = View.VISIBLE
                val nickname = searchEdit.editableText.toString().trim()
                val platform = platformSpinner.selectedItem.toString()
                val region = regionSpinner.selectedItem.toString()
                presenter.searchPlayer(nickname, platform, region)
            }
            PATCHNOTES_BUTTON_ID -> {
                val patchNoteActivity = Intent(this, PatchNotesActivity::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, patchNotesButton, patchNotesButton.transitionName)
                    startActivity(patchNoteActivity, options.toBundle())
                } else {
                    startActivity(patchNoteActivity)
                }
            }
        }
    }

    override fun showError(errorText: String) {
        searchProgressBar.visibility = View.INVISIBLE
        toast(errorText)
    }

    override fun playerFound(results: RealmResults<BattleNetProfile>) {
        searchProgressBar.visibility = View.INVISIBLE

        val diffCallback = SearchDiffCallback(searches, results)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        searches.clear()
        searches.addAll(results.safeSubList(0, 5))
        diffResult.dispatchUpdatesTo(listAdapter)
    }

    inner class SearchesAdapter : RecyclerView.Adapter<SearchViewHolder>() {
        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.bind(searches[position])
        }

        override fun getItemCount(): Int {
            return searches.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchViewHolder {
            return SearchViewHolder(SearchItemUI().createView(AnkoContext.create(parent!!.context, parent)))
        }
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val avatarImage: SimpleDraweeView = itemView.find(AVATAR_ID)
        val nicknameText: TextView = itemView.find(NICKNAME_ID)
        val platformText: TextView = itemView.find(PLATFORM_ID)
        val regionText: TextView = itemView.find(REGION_ID)
        fun bind(search: BattleNetProfile) {
            itemView.setOnClickListener(this)
            avatarImage.setImageURI(search.avatarUrl)
            nicknameText.text = search.battleTag
            platformText.text = search.platform
            regionText.text = search.region
        }

        override fun onClick(p0: View?) {
            startActivity<PlayerStatsActivity>("btag" to searches[adapterPosition].battleTag)
        }
    }
}
