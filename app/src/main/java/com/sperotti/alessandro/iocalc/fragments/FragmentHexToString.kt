package com.sperotti.alessandro.iocalc.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.sperotti.alessandro.iocalc.R
import com.sperotti.alessandro.iocalc.utils.Calcolatore
import com.sperotti.alessandro.iocalc.utils.Constants
import java.nio.charset.StandardCharsets


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentHexToString.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentHexToString.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentHexToString : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var hexEdt: EditText
    private lateinit var strEdt: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val pref = PreferenceManager.getDefaultSharedPreferences(this.activity)
        val view =  inflater.inflate(R.layout.fragment_hex_to_string, container, false)

        hexEdt = view.findViewById(R.id.hexEdt)
        strEdt = view.findViewById(R.id.strEdt)

        hexEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val upperCaseHex = hexEdt.text.toString().toUpperCase()


                if (upperCaseHex.matches(Constants.HEX_REGEX.toRegex()) || upperCaseHex.isEmpty()) {

                    if (!upperCaseHex.isEmpty()) {

                        strEdt.setText(Calcolatore.hexToStr(upperCaseHex, pref.getString(Constants.CHARSET, Charsets.US_ASCII.name())))

                    }

                } else {
                    //showError(Constants.HEX)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        strEdt.setOnClickListener {
            //TODO
        }
        // Inflate the layout for this fragment
        return view
    }

    fun showError(keepEdt: Int, showSnackbar: Boolean) {
        if (keepEdt != Constants.HEX) hexEdt.text.clear()
        //if (!inputErrorSnackbar.isShown() && showSnackbar) inputErrorSnackbar.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentHexToString.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                FragmentHexToString().apply {
                }
    }
}
