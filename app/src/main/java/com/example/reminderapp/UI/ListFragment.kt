package com.example.reminderapp.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reminderapp.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
public class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel : FragmentViewModel
    lateinit var servis : ServisClass

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

        val view = inflater.inflate(R.layout.fragment_list, container, false)



        viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val rAdapter = RecycleViewAdapter(viewModel,requireContext(),this)
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.showAll().observe(viewLifecycleOwner, Observer {

            //findViewById<TextView>(R.id.textView).text = it.toString()
            Log.i("AALiveData","changed")
            rAdapter.addData(it)
        })

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_listFragment_to_newReminderFragment)
           // val r = ReminderObject("textttttteee",false,
           //     LocalDateTime.now(), Importance.min, LocalDateTime.now(),"cat1")


          //  viewModel.insertReminder(r)
        }

        Log.i("AAAAstartf1", "fragment 1 started")
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}