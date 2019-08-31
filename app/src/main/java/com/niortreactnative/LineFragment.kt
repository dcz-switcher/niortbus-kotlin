package com.niortreactnative

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.fragment_line.*
import kotlinx.android.synthetic.main.line_item_view.*
import org.json.JSONObject
import java.io.InputStream
import java.nio.charset.Charset
import kotlinx.coroutines.*
import org.json.JSONArray
import java.io.StringReader


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_JSONFILE = "json_file_name"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LineFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LineFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LineFragment : Fragment() {


    private val viewTag = "TAG_lineFragment"
    lateinit var mContext:Context


    private var jsonFile: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var jsonLine: Line? = null

    private lateinit var stationsRecyclerView:RecyclerView
    private lateinit var stationsLayoutManager: RecyclerView.LayoutManager
    private lateinit var stationsAdapter: RecyclerView.Adapter<*>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            jsonFile = it.getString(ARG_JSONFILE)
        }

        Log.d(viewTag, "onCreate ... jsonFile = $jsonFile")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_line, container, false)

        Log.d(viewTag, "onCreateView")

        //var view = inflater.inflate(R.layout.fragment_line, container, false)
        //populateView()
        //return view

        return inflater.inflate(R.layout.fragment_line, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(viewTag, "onAttach")
        if (context is OnFragmentInteractionListener) {
            listener = context
            mContext = context

            arguments?.getString(ARG_JSONFILE)?.let{
                jsonFile = it
            }

            try{
                GlobalScope.launch (Dispatchers.Main){

                    withContext(Dispatchers.IO){
                        populateView()
                    }

                    Log.d(viewTag, "long task finished, update UI")

                    updateUI()

                }
            }catch (e:Exception){
                Log.e(viewTag, e.toString())
            }


        } else {
            throw RuntimeException("$context.toString() must implement OnFragmentInteractionListener")
        }



    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStart() {
        super.onStart()
        Log.d(viewTag, "onStart")
        Log.d(viewTag, view?.toString())
    }




    private fun updateUI(){
        try{
            // set departure and arrival stations
            lineDeparture.text = jsonLine?.departure.toString()
            lineArrival.text = jsonLine?.arrival.toString()


            // set stations list
            stationsLayoutManager = LinearLayoutManager(this.context)



            stationsRecyclerView = buslineStations.apply {
                layoutManager = stationsLayoutManager
                adapter = BuslineStationsAdapter(JSONArray(arrayOf("un", "deux", "trois")))
            }

        }catch (e:Exception){
            Log.e(viewTag, e.toString())
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param jsonFile json file name
         * @return A new instance of fragment LineFragment.
         */
        @JvmStatic
        fun newInstance(jsonFile: String) =
            LineFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_JSONFILE, jsonFile)
                }
            }
    }



    private fun populateView(){

        try{
        /*
        val line = readJsonFileFromAssets("$jsonFile.json")
        Log.d(viewTag, line.toString())
        */
          //  val jsonLine = Klaxon().parse<Line>(line.toString())
            Log.d(viewTag, "populateView before parsing")

            jsonLine = Klaxon().parse<Line>(mContext.assets.open("$jsonFile.json"))
            //jsonLine = Klaxon().parse<Line>(mContext.assets.open("ligne0.json"))
            Log.d(viewTag, "DEBUG open ligne0.json to understand how Klaxon works")

            Log.d(viewTag, "populateView after parsing")

            Log.d(viewTag, "DEPARTURE = " + jsonLine?.departure)
            Log.d(viewTag, "ARRIVAL = " + jsonLine?.arrival)

            Log.d(viewTag, "PERIODE SIZE = " + jsonLine?.periodes?.size)
            Log.d(viewTag, "PERIODE 0 NAME = " + jsonLine?.periodes?.get(0)?.name)

            Log.d(viewTag, "ALLER ARRAY SIZE = " + jsonLine?.periodes?.get(0)?.aller?.size)
            Log.d(viewTag, "STATION 0 NAME = " + jsonLine?.periodes?.get(0)?.aller?.get(0)?.name)

            
        }catch (e:Exception){
            Log.e(viewTag, e.toString())
        }

    }

    private fun readJsonFileFromAssets(fileName: String)
        =   try {

            val inputStream: InputStream = mContext.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String( buffer, Charset.defaultCharset())


        } catch (e:Exception) {
        Log.e(viewTag, "ERROR !!!!!")
            Log.e(viewTag, e.toString())
        }

}
