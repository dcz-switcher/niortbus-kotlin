package com.niortreactnative

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log

import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.line_item_view.view.*
import java.lang.Exception


class LineItemView : LinearLayout
{

    private val viewTag = "TAG_lineItemView"

    private lateinit var mLineID:String
    private var mLineColor:Int = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr: Int) :
            super(context, attributeSet, defStyleAttr){
            LayoutInflater.from(context).inflate(R.layout.line_item_view, this, true)

            if (attributeSet != null) {
                try{
                    val a = context?.obtainStyledAttributes(attributeSet, R.styleable.LineItemView)

                    if (a != null){

                        if (a.hasValue(R.styleable.LineItemView_lineId) && a.getString(R.styleable.LineItemView_lineId) != null){
                            //Log.d(viewTag, getLineId())
                            //setLineId(a.getString(R.styleable.LineItemView_lineId))
                        }

                        if (a.hasValue(R.styleable.LineItemView_lineColor)) {
                            setLineColor(a.getColor(R.styleable.LineItemView_lineColor, 0))
                            Log.d(viewTag, getLinecolor().toString())
                            my_background.setBackgroundColor(getLinecolor())
                        }
                    }

                    /*
                    if (a?.hasValue(R.styleable.LineItemView_lineId)!!) {
                        setLineId(a.getString(R.styleable.LineItemView_lineId))
                        Log.d(viewTag, getLineId())
                    }

                    if (a.hasValue(R.styleable.LineItemView_lineColor)) {
                        setLineColor(a.getColor(R.styleable.LineItemView_lineColor, 0))
                        Log.d(viewTag, getLinecolor().toString())
                        my_background.setBackgroundColor(getLinecolor())
                    */

                }catch (e:Exception){
                    Log.e(viewTag, e.message)
                }
            }
    }


    /**
     *  move code into last constructor with 3 arguments
     *  source: https://stackoverflow.com/questions/36716794/kotlin-how-to-access-the-attrs-for-a-customview
     *
    init {
        LayoutInflater.from(context).inflate(R.layout.line_item_view, this, true)
    }
     */

    private fun setLineId(lineId: String){
        mLineID = lineId
    }

    private fun getLineId(): String {
        return mLineID
    }

    private fun setLineColor(lineColor: Int){
        mLineColor = lineColor
    }

    private fun getLinecolor():Int{
        return mLineColor
    }

}