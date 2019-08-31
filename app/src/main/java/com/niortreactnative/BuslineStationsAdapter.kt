package com.niortreactnative


import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.station_item.view.*
import org.json.JSONArray


private const val VIEW_TAG = "BuslineStationsAdapter"


class BuslineStationsAdapter(private val dataSet: ArrayList<LineStation>) :

    RecyclerView.Adapter<BuslineStationsAdapter.BuslineStationsViewHolder>() {



    class BuslineStationsViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val stationName = view.stationName

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BuslineStationsViewHolder {
        return BuslineStationsViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.station_item, p0, false))

    }

    override fun onBindViewHolder(p0: BuslineStationsViewHolder, p1: Int) {
        p0.stationName.text = dataSet.get(p1).name
        Log.d(VIEW_TAG, dataSet[p1].toString())
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }

}