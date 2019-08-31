package com.niortreactnative


class Line (
    val departure:String,
    val arrival:String,
    val periodes:ArrayList<LinePeriode>
)

class LinePeriode(
    val name:String = "",
    val aller:ArrayList<LineStation> = ArrayList(),
    val retour:ArrayList<LineStation> = ArrayList()
)

class LineStation(
    val name:String,
    val stops:ArrayList<String>
)
