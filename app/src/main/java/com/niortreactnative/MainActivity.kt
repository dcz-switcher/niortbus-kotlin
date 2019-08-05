package com.niortreactnative

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(),
    LineFragment.OnFragmentInteractionListener {

    private val logTag = "TAG_MainActivity"

    private val transitionDuration:Long = 150
    private val transitionStartDelay:Long = 0
    private val enterSlide = Slide()

    private lateinit var fragmentManager:FragmentManager
    private lateinit var fragmentTransaction:FragmentTransaction

    private lateinit var lineListView:ListView

    private lateinit var lineList:ArrayList<LineItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        lineListView = line_item_list_view


        fragmentManager = supportFragmentManager

        enterSlide.startDelay = transitionStartDelay
        enterSlide.duration = transitionDuration

        addEvents()

        populateLineListView()

    }


    private fun addEvents(){

        lineListView.setOnItemClickListener{ _, _, position, _ ->
            val selectedLine = lineList[position]

            Log.d(logTag, "line $position was clicked, departure = " + selectedLine.departure)
            Log.d(logTag, "jsonFile is " + selectedLine.jsonFile)

            try{
                val lineFragment = LineFragment.newInstance(selectedLine.jsonFile)
                fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, lineFragment)
                fragmentTransaction.addToBackStack(null)



                lineFragment.enterTransition = enterSlide


                fragmentTransaction.commit()
            }catch (e:java.lang.Exception){
                Log.e(logTag, e.message)
            }
        }

        /*
        lineItem1.setOnClickListener{
            Log.d(logTag, "click")
            Log.d(logTag, "lineId = " + (it as LineItemView).getLineId())

            // try to load fragment
            try{
                val lineFragment = LineFragment()
                fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, lineFragment)
                fragmentTransaction.addToBackStack(null)


                /*
                var enterFade = Fade()
                enterFade.startDelay = transitionStartDelay
                enterFade.duration = transitionDuration
                */


                lineFragment.enterTransition = enterSlide


                fragmentTransaction.commit()
            }catch (e:java.lang.Exception){
                Log.e(logTag, e.message)
            }
        }
        */

    }



    /**
     * populate line list view
     */
    private fun populateLineListView(){
        try{

            //val line = readJsonFileFromAssets("ligne1.json")
            //Log.d(logTag, line.toString())

            /*
            // simple list of text
            val array = arrayOf("ligne 1", "ligne 2")
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, array)
            lineListView.adapter = adapter
            */


            lineList = ArrayList()
            lineList.add(LineItem("Trévin / Parpin", "Pôle Universitaire", "ligne1"))
            lineList.add(LineItem("Ebaupin / Bois Chamaillard", "Brizeaux CAF", "ligne2"))
            lineList.add(LineItem("Pôle Universitaire", "Terre de sport", "ligne3"))
            lineList.add(LineItem("Mairie Aiffre", "Sainte Pezenne", "ligne4"))
            lineList.add(LineItem("Parpin / Château Driguet", "Chaintre brûlée", "ligne5"))
            lineList.add(LineItem("Surimeau", "Saint Liguaire", "ligne6"))

            val lineItemAdapter = LineItemAdapter(this, lineList)

            lineListView.adapter = lineItemAdapter

        } catch (e:java.lang.Exception){
            Log.e(logTag, e.message)
         }
    }


    private fun readJsonFileFromAssets(fileName: String)
            =   try {
        val inputStream:InputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String( buffer, Charset.defaultCharset())
    }
    catch (e:Exception) {
        println("Aie !")
        Log.e(logTag, e.message)
    }



    override fun onFragmentInteraction(uri: Uri){
        Log.d(logTag, "onFragmentInteractionListener")
    }
}
