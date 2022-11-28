package com.example.reminderapp.UI

import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.example.reminderapp.R
import com.google.android.material.button.MaterialButtonToggleGroup
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewReminderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewReminderFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var switch : SwitchCompat
    private lateinit var spinner : Spinner
    private lateinit var button : Button

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_reminder, container, false)

        button = view.findViewById(R.id.buttonDeadline)
        button.setVisibility(View.GONE)

        button.setOnClickListener(View.OnClickListener {
            val datePicker : DatePickerDialog = DatePickerDialog(
                requireContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            )
            datePicker.show()
        })

        switch = view.findViewById(R.id.switchImpOrDL)
        spinner = view.findViewById(R.id.spinnerImportance)

        switch.setOnCheckedChangeListener{buttonView, isChecked ->

            if (isChecked) {
                switch.setText(R.string.Deadline)
                button.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            } else {
                switch.setText(R.string.Importance)
                button.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewReminderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewReminderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        Log.i("AAAdate", "$p1 $p2 $p3")
        System.out.println("AAAAAAAA")
    }
}