package com.example.inz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_input_view.*
import kotlinx.android.synthetic.main.fragment_output_view.*
import java.util.*
import kotlin.collections.HashSet

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutputView.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutputView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var status: Boolean? = null
    lateinit var ESPO: ESPO
    lateinit var editTextName: EditText
    lateinit var editTextPin: EditText
    lateinit var switchStatusOutput: Switch
    lateinit var editTextDescription: EditText
    lateinit var tempSave:ESPO
    lateinit var sensors:Array<ESPS>
    lateinit var adapter:ArrayAdapter<String>
    lateinit var list: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getBoolean("status").let {
            status = it
        }
        if (status == true) ESPO = MyApplicaton.ESPO!!
        sensors = MyApplicaton.User!!.ESPsensor!!
        list = CreateList(MyApplicaton.User!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_output_view, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tempSave = ESPO("0","name",0, false, "",null, null , null, 0)
        editTextName = view.findViewById(R.id.editTextNameOutput)
        editTextPin = view.findViewById(R.id.editTextPinOutput)
        switchStatusOutput = view.findViewById(R.id.switchStatusOutput)
        editTextDescription = view.findViewById(R.id.editTextDescriptionOutput)


        adapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,list)
        var spinner = view.findViewById(R.id.Sensor_spinner) as Spinner
        spinner.adapter = adapter

        if (status == true)
        {
            editTextName.setText(ESPO.name, TextView.BufferType.EDITABLE)
            editTextPin.setText(ESPO.pin.toString())
            editTextDescription.setText(ESPO.description)
            switchStatusOutput.isChecked = ESPO.status
            if ((ESPO.sensor != "").and(ESPO.sensor != null))
            {
                spinner.setSelection(FindName(MyApplicaton.User!!, ESPO.sensor!!.toInt(),list).toInt())
                optymal.isVisible = true

                if (ESPO.minValue == null) editTextMinValue.setText("")
                else editTextMinValue.setText(ESPO.minValue, TextView.BufferType.EDITABLE)

                if (ESPO.maxValue == null) {editTextMaxValue.setText("")}
                else {editTextMaxValue.setText(ESPO.maxValue)}


            }
            else{
                spinner.setSelection(0)
                optymal.isVisible = false
            }

        }
        else if (status == false){
            btnDeleteOutput.isEnabled = status!!
            btnDeleteOutput.isClickable = status!!
        }


        btnBackOutput.setOnClickListener {
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, OutputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }
        btnDeleteOutput.setOnClickListener {
            MyApplicaton.User!!.ESPoutputs = Refreshlist(MyApplicaton.User!!.ESPoutputs!!, ESPO)
            DatabaseObjects().DeleteESP(ESPO, null)
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, OutputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()

        }
        btnSaveOutput.setOnClickListener {
            val SpinnerText = spinner.selectedItem.toString()


            if (status == true)
            {
                ESPO.name = editTextName.text.toString()
                ESPO.pin = editTextPin.text.toString().toInt()
                ESPO.status = switchStatusOutput.isChecked
                ESPO.description = editTextDescription.text.toString()
                if (SpinnerText == "none")
                {
                    ESPO.sensor = ""
                }
                else{
                    ESPO.sensor = FindSensor(MyApplicaton.User!!, SpinnerText)
                    if (editTextMaxValue.text?.toString() == "") ESPO.maxValue = null
                    else ESPO.maxValue = editTextMaxValue.text?.toString()
                    if (editTextMinValue.text?.toString() == "") ESPO.minValue = null
                    else ESPO.minValue = editTextMinValue.text?.toString()
                }
                DatabaseObjects().SaveESPO(ESPO, "POST",MyApplicaton.User!!)    //POST
            }
            else{
                tempSave.description = editTextDescription.text.toString()
                tempSave.status = switchStatusOutput.isChecked
                tempSave.pin = editTextPin.text.toString().toInt()
                tempSave.name = editTextName.text.toString()
                MyApplicaton.User!!.ESPoutputs = AddESPtoUSER(MyApplicaton.User!!.ESPoutputs!!, tempSave)
                if (SpinnerText == "none")
                {
                    tempSave.sensor = ""
                }
                else{
                    tempSave.sensor = FindSensor(MyApplicaton.User!!, SpinnerText)
                    if (editTextMaxValue.text?.toString() == "") tempSave.maxValue = null
                    else tempSave.maxValue = editTextMaxValue.text?.toString()
                    if (editTextMinValue.text?.toString() == "") tempSave.minValue = null
                    else tempSave.minValue = editTextMinValue.text?.toString()
                }

                DatabaseObjects().SaveESPO(MyApplicaton.User!!.ESPoutputs!![MyApplicaton.User!!.ESPoutputs!!.size - 1]  , "PUT",MyApplicaton.User!!)     //PUT
            }
            val intent = activity?.supportFragmentManager?.beginTransaction()
            intent?.replace(R.id.frame_layout, OutputFragment())
            intent?.disallowAddToBackStack()
            intent?.commit()
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                optymal.isVisible = position != 0

            }


            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }

    fun Refreshlist(T: Array<ESPO>, item: ESPO):Array<ESPO>
    {
        val set = HashSet(Arrays.asList(*T))
        set.remove(item)
        val temp:Array<ESPO> = set.toArray(arrayOfNulls<ESPO>(set.size))
        return temp
    }
    fun AddESPtoUSER(array: Array<ESPO>, element: ESPO): Array<ESPO>?
    {
        val arr = arrayOfNulls<ESPO>(array.size + 1)
        System.arraycopy(array, 0, arr, 0, array.size)
        arr[array.size] = element
        return arr as Array<ESPO>?
    }
    fun CreateList(user: User): List<String>
    {
        val list = mutableListOf("none")
        for (item in user.ESPsensor!!)
        {
            list += item.name
        }

        return list
    }
    fun FindName(user: User, id:Int, list: List<String>): String
    {
        var name: String
        var sensor = user.ESPsensor?.find { it.id == id.toString() }
        var index = list.indexOf(sensor!!.name)

        return index.toString()
    }
    fun FindSensor(user: User, name:String):String
    {
        val sensor = user.ESPsensor?.find { it.name == name }

        return sensor?.id!!
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutputView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OutputView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}