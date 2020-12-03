package com.chibi.diffutilssample

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import java.lang.String


class CustomAdapter(
    private val arrayList: ArrayList<Int>,
    private val listener: AdapterListener
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v("Adapter", "onBindViewHolder")
        holder.ll_item.setOnClickListener { listener.onSelected(position) }
        holder.tv_items.text = String.valueOf(arrayList[position])
        holder.img_remove.setOnClickListener { listener.onRemoveItem(position) }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.v("Adapter", "payloads")
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isNotEmpty()) {
            holder.ll_item.setOnClickListener { listener.onSelected(position) }
            holder.tv_items.text = String.valueOf(arrayList[position])
            holder.img_remove.setOnClickListener { listener.onRemoveItem(position) }
    } else {
        // in this case regular onBindViewHolder will be called
        super.onBindViewHolder(holder, position, payloads);
    }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun removeByPosition(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun addList(list: java.util.ArrayList<Int>) {
        arrayList.addAll(list)
        notifyItemInserted(arrayList.size)
    }

    fun addItem(value: Int) {
        arrayList.add(value)
        notifyItemInserted(arrayList.size)
    }

    fun getItemByPosition(position: Int): Int {
        return arrayList[position]
    }

    fun getLastPositionValue(): Int {
        return arrayList[itemCount - 1]
    }

    fun onNewData(newData: java.util.ArrayList<Int>) {
        val arrayValues = newData;
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(DiffUtilCallBack(arrayValues, arrayList))

        diffResult.dispatchUpdatesTo(this)

        this.arrayList.clear()
        this.arrayList.addAll(arrayValues)

    }

    fun getArrays(): java.util.ArrayList<Int> {
        return arrayList
    }


    interface AdapterListener {
        fun onSelected(position: Int)
        fun onRemoveItem(position: Int)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ll_item = itemView.findViewById<ConstraintLayout>(R.id.ll_item)
        val tv_items = itemView.findViewById<TextView>(R.id.tv_items)
        val img_remove = itemView.findViewById<ImageView>(R.id.img_remove)
    }
}