package com.chibi.diffutilssample

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack(val newList: ArrayList<Int>?, val oldList: ArrayList<Int>?) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val old = oldList!![oldItemPosition];
        val newList = newList!![newItemPosition];
        val result = newList.compareTo(old)
        return result == 0
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        val newItem = newList!![newItemPosition]
        val oldItem = oldList!![oldItemPosition]
        // Bundles value pair checking any change between new and old items
        val diff = Bundle()

        if (newItem != oldItem){
            diff.putInt("", 0)
        }

        if (diff.size() == 0){
            return null
        }

        return diff.size()

    }
}