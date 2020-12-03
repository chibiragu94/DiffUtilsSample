package com.chibi.diffutilssample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(), CustomAdapter.AdapterListener {

    private lateinit var rvItems : RecyclerView

    private val arrayList = ArrayList<Int>()
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        addInitialArrayValues();
        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_item -> {
                addSingleItems()
                true
            }
            R.id.update_items -> {
                updateItems()
                true
            }
            R.id.swap_items -> {
                swapItems()
                true
            }
            R.id.paginate_items -> {
                addPaginationItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateItems() {

        var array = adapter.getArrays()
        var dummyArray = ArrayList<Int>()

        for (i in 0 until array.size){
            if (i < 5){
                dummyArray.add(0)
            }else{
                dummyArray.add(i)
            }
        }
        adapter.onNewData(dummyArray)
    }

    private fun swapItems() {

        var array = adapter.getArrays()
        var newarray = ArrayList<Int>()

        for (i in array.indices.reversed()) {

            // Append the elements in reverse order
            newarray.add(array[i])
        }

        adapter.onNewData(newarray)
    }

    override fun onSelected(position: Int) {
        val value = adapter.getItemByPosition(position)
        Snackbar.make(rvItems, "Display's $value", Snackbar.LENGTH_SHORT)
                .show()
    }

    override fun onRemoveItem(position: Int) {
        adapter.removeByPosition(position)
    }

    private fun initAdapter() {
        adapter = CustomAdapter(arrayList, this)

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.setHasFixedSize(true)
        rvItems.adapter = adapter
    }

    private fun initView() {
        rvItems = findViewById(R.id.rv_items)
    }

    private fun addInitialArrayValues() {
        for (i in 1..20){
            arrayList.add(i)
        }
    }

    private fun addSingleItems(){
        adapter.addItem(adapter.getLastPositionValue() + 1)
    }

    private fun addPaginationItems(){
        // wait for 2 sec
        Timer().schedule(2000) {

            val list = ArrayList<Int>()

            val lastValue = adapter.getLastPositionValue() + 1
            for (i in lastValue until lastValue + 20) {
                list.add(i)
            }
            runOnUiThread { adapter.addList(list) }
        }
    }
}