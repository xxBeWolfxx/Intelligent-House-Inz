package com.example.inz

import android.R.attr.x
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_input_view.*
import java.util.*
import kotlin.collections.HashSet


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputView.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var status: Boolean? = null
    lateinit var ESPS: ESPS
    lateinit var tempSave: ESPS
    lateinit var user: User
    lateinit var  editTextName: EditText
    lateinit var editTextPin: EditText
    lateinit var  switchStatusInput: Switch
    lateinit var  textCurrentValue: TextView
    lateinit var editTextDescription: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean("status").let {
            status = it
        }
        if (status == true)
        {
            ESPS = MyApplicaton.ESPS!!
            MyApplicaton.listValue = ESPS.valueAvgDay.split(",")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempSave = ESPS("0","name",0,false,0," "," ","")
        editTextName = view.findViewById(R.id.editTextNameInput)
        editTextPin = view.findViewById(R.id.editTextPinInput)
        switchStatusInput = view.findViewById(R.id.switchStatusInput)
        textCurrentValue = view.findViewById(R.id.textCurrentValueInput)
        editTextDescription = view.findViewById(R.id.editTextDescriptionInput)

        if (status == true)
        {
            editTextName.setText(ESPS.name, TextView.BufferType.EDITABLE)
            editTextPin.setText(ESPS.pin.toString())
            textCurrentValue.text = ESPS.valueTemp.toString()
            editTextDescription.setText(ESPS.description)
            switchStatusInput.isChecked = ESPS.status
        }
        else if(status == false){
            btnDeleteInput.isEnabled = status!!
            btnDeleteInput.isClickable = status!!
            graph_btn.isEnabled = status!!
            graph_btn.isClickable = status!!
        }
        graph_btn.setOnClickListener {
            AssignValue(ESPS)
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, ChartFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }


        btnBackInput.setOnClickListener {
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, InputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }

        btnSaveInput.setOnClickListener {


            if (status == true)
            {
                ESPS.name = editTextName.text.toString()
                ESPS.pin = editTextPinInput.text.toString().toInt()
                ESPS.status = switchStatusInput.isChecked
                ESPS.description = editTextDescription.text.toString()
                DatabaseObjects().SaveESPS(ESPS, "POST",MyApplicaton.User!!)    //POST
            }
            else{
                tempSave.description = editTextDescription.text.toString()
                tempSave.status = switchStatusInput.isChecked
                tempSave.pin = editTextPinInput.text.toString().toInt()
                tempSave.name = editTextName.text.toString()
                MyApplicaton.User!!.ESPsensor = AddESPtoUSER(MyApplicaton.User!!.ESPsensor!!, tempSave)

                DatabaseObjects().SaveESPS(MyApplicaton.User!!.ESPsensor!![MyApplicaton.User!!.ESPsensor!!.size - 1]  , "PUT",MyApplicaton.User!!)     //PUT
            }
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, InputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }

        btnDeleteInput.setOnClickListener {
            MyApplicaton.User!!.ESPsensor = Refreshlist(MyApplicaton.User!!.ESPsensor!!, ESPS)
            DatabaseObjects().DeleteESP(null, ESPS)
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, InputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) {
            InputView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }

    }
    fun AssignValue(esps: ESPS) {
        MyApplicaton.listValue = esps!!.valueAvgDay.split(",")
    }

    fun Refreshlist(T: Array<ESPS>, item: ESPS):Array<ESPS>
    {
        val set = HashSet(Arrays.asList(*T))
        set.remove(item)
        val temp:Array<ESPS> = set.toArray(arrayOfNulls<ESPS>(set.size))
        return temp
    }
    fun AddESPtoUSER(array: Array<ESPS>, element: ESPS): Array<ESPS>?
    {
        val arr = arrayOfNulls<ESPS>(array.size + 1)
        System.arraycopy(array, 0, arr, 0, array.size)
        arr[array.size] = element
        return arr as Array<ESPS>?
    }
}