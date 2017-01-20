package com.prokkypew.oversentry.features.patchnotes

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toolbar
import com.arellomobile.mvp.MvpActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.prokkypew.oversentry.R
import com.prokkypew.oversentry.features.patchnotes.PatchNoteItemUI.Companion.TITLE_ID
import com.prokkypew.oversentry.features.patchnotes.PatchNoteItemUI.Companion.TV_ID
import com.prokkypew.oversentry.features.patchnotes.PatchNotesActivityUI.Companion.TOOLBAR_ID
import com.prokkypew.oversentry.model.PatchNote
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import java.util.*


class PatchNotesActivity : MvpActivity(), PatchNotesActivityView {

    @InjectPresenter
    lateinit var presenter: PatchNotesActivityPresenter

    private val listAdapter: PatchNotesAdapter = PatchNotesAdapter()
    var patches: ArrayList<PatchNote> = ArrayList()

    private val progressBar: ProgressBar by lazy { find<ProgressBar>(PatchNotesActivityUI.PROGRESSBAR_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PatchNotesActivityUI(listAdapter).setContentView(this)
        setActionBar(find<Toolbar>(TOOLBAR_ID))
        actionBar.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.latest_patches)
        progressBar.visibility = View.VISIBLE
        presenter.loadPatchNotes()
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

    override fun showError(errorText: String) {
        progressBar.visibility = View.GONE
        toast(errorText)
        onBackPressed()
    }

    override fun patchNotesLoaded(notes: ArrayList<PatchNote>) {
        progressBar.visibility = View.GONE
        patches = ArrayList()
        patches.addAll(notes)
        listAdapter.notifyDataSetChanged()
    }

    inner class PatchNotesAdapter : RecyclerView.Adapter<PatchNoteViewHolder>() {
        override fun onBindViewHolder(holder: PatchNoteViewHolder, position: Int) {
            holder.bind(patches[position])
        }

        override fun getItemCount(): Int {
            return patches.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PatchNoteViewHolder {
            return PatchNoteViewHolder(PatchNoteItemUI().createView(AnkoContext.create(parent!!.context, parent)))
        }
    }

    inner class PatchNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patchText: TextView = itemView.find(TV_ID)
        val patchTitle: TextView = itemView.find(TITLE_ID)
        fun bind(patch: PatchNote) {
            patchTitle.text = patch.title
            patchText.text = Html.fromHtml(patch.detail).trim()
        }
    }
}
