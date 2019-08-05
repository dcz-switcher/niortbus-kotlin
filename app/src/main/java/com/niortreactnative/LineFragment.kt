package com.niortreactnative

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.InputStream
import java.nio.charset.Charset


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

        var view = inflater.inflate(R.layout.fragment_line, container, false)

        populateView()

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            mContext = context

            arguments?.getString(ARG_JSONFILE)?.let{
                jsonFile = it
            }

        } else {
            throw RuntimeException("$context.toString() must implement OnFragmentInteractionListener")
        }



    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
        //TODO: read JSON and populate View

        val line = readJsonFileFromAssets("$jsonFile.json")
        Log.d(viewTag, line.toString())


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
