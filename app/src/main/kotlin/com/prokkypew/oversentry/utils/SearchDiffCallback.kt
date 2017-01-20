package com.prokkypew.oversentry.utils

import android.support.v7.util.DiffUtil
import com.prokkypew.oversentry.model.BattleNetProfile


/**
 * Created by alexander.roman on 19.01.2017.
 */
class SearchDiffCallback(private val mOldList: List<BattleNetProfile>?, private val mNewList: List<BattleNetProfile>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return mNewList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList!![newItemPosition].nickName === mOldList!![oldItemPosition].nickName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList!![newItemPosition] == (mOldList!![oldItemPosition])
    }
}