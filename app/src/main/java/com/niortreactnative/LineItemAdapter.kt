package com.niortreactnative

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.line_item_view.view.*

class LineItemAdapter (private val context:Context,
                       private val dataSource:ArrayList<LineItem>) : BaseAdapter() {


    private val inflater: LayoutInflater
        = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.line_item_view, parent, false)


        val lineItem = getItem(position) as LineItem
        rowView.departure.text = lineItem.departure
        rowView.arrival.text = lineItem.arrival

        return rowView
    }
}