package com.example.inz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputFragment : Fragment(),  MyAdapter.OnItemClickListener {
    //*****************variables to set RecyclerView**********************
    lateinit var listInputs: ArrayList<ItemCardView>
    var RecyclerInputs: RecyclerView? = null
    lateinit var adapter: MyAdapter
    var clickposition:Int = -1 //position of list in RecyclerView
    lateinit var user: User



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Toast.makeText(activity?.applicationContext, "CHUJEK", Toast.LENGTH_LONG).show()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        user = MyApplicaton.User!!


        listInputs = ArrayList<ItemCardView>()
        listInputs = DatabaseObjects().CreateArrayESP(user, false)
        adapter = MyAdapter(listInputs, this)

        //              Set RecyclerView with list of components from tasks
        RecyclerInputs = view.findViewById(R.id.RycyclerInputs)



        RecyclerInputs?.adapter = adapter
        RecyclerInputs?.layoutManager = LinearLayoutManager(view.context)
        RecyclerInputs?.setHasFixedSize(true)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        Log.d("Klik", "Position $position")
        //adding = false
        clickposition = position
    }
}