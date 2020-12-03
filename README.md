# DiffUtils

## Why Recylerview
recyclerview is a just improved version of Listview. In ListView, if you don’t create viewholder 
class then it creates views for all the rows, which is not good for the performance of the app as 
it takes more time as well as more memory. In recyclerview creating viewholder is a must, so that only a 
few views are needed to show the data and it can be recycled when and where needed.

* ## Pros
We use recyclerview because it doesn’t create views for all the rows, instead, 
it creates views that can fill up the screen and few above and below, and just recycles it.

* ## Cons
When we use notifyDataSetChanged() method it updates all the view (all visible view on screen and 
few buffer view above and below the screen). It’s inappropriate way of updating list if just a few 
rows have changed.

## Why DiffUtils
DiffUtil is a utility class that can calculate the difference between two lists and output a 
list of update operations that converts the first list into the second one.It can be used to 
calculate updates for a RecyclerView Adapter.
In Recyclerview NotifyDataSetChanged is costly. DiffUtil class solves that problem with four abstract Methods

* getOldListSize() : It returns the size of the old list.
* getNewListSize() : Returns the size of the new list;
* areItemsTheSame(int oldItemPosition, int newItemPosition) : 
Called by the DiffUtil to decide whether two object represent the same Item.
If your items have unique ids, this method should check their id equality. If true it goes to next method or 
else stops comparing using remaining abstract methods.
* areContentsTheSame(int oldItemPosition, int newItemPosition) : 
Checks whether two items have the same data.You can change its behavior depending on your UI. 
This method is called by DiffUtil only if areItemsTheSame returns true. If False it goes to next abstract method
getChangePayload(int oldItemPosition, int newItemPosition) : 
If areItemTheSame return true and areContentsTheSame returns false DiffUtil calls this method to 
get a payload about the change. It gives the number of items had changed, so only the changed items 
will get updated remaining items won't get any update.

Below example for implementing diffutils

```
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
```

