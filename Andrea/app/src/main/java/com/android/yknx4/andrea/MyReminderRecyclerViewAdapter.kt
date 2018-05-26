package com.android.yknx4.andrea

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dpro.widgets.WeekdaysPicker
import java.text.SimpleDateFormat
import java.util.*


/**
 * [RecyclerView.Adapter] that can display a [Reminder] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
 class MyReminderRecyclerViewAdapter(private val mValues:List<Reminder>, private val mListener:OnListFragmentInteractionListener?):RecyclerView.Adapter<MyReminderRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
    val view = LayoutInflater.from(parent.context)
    .inflate(R.layout.fragment_reminder, parent, false)
    return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int) {
        val formatter = SimpleDateFormat("KK:mm a")

        val r = mValues[position]
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, r.hour)
        calendar.set(Calendar.MINUTE, r.min)

    holder.mItem = r
        holder.mIdView.text = r.title
        holder.mContentView.text = formatter.format(calendar.time)
        holder.weekd.selectedDays = r.selectedDays

    holder.mView.setOnClickListener {
        val selectedItem = holder.mItem
        if(selectedItem != null) {
            mListener?.onListFragmentInteraction(selectedItem)
        }
    }
    }

    override fun getItemCount():Int {
    return mValues.size
    }

inner class ViewHolder( val mView:View):RecyclerView.ViewHolder(mView) {
 val mIdView:TextView = mView.findViewById<View>(R.id.id) as TextView
    val mContentView:TextView = mView.findViewById<View>(R.id.content) as TextView
    val weekd:WeekdaysPicker = mView.findViewById(R.id.weekday_lv)
    var mItem:Reminder? = null

    override fun toString():String {
    return super.toString() + " '" + mContentView.text + "'"
    }
}
}
