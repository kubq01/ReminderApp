package com.example.reminderapp.UI

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.FragmentViewModel
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
    private lateinit var spinnerImp : Spinner
    private lateinit var button : Button
    private lateinit var spinnerCat : Spinner


    //data to pass to ReminderObject
    private var reminderText : String? = null
    private var containsDeadline : Boolean?  = null
    private var deadline : LocalDateTime? = null
    private var importance : Importance? = null
    private var startDate : LocalDateTime? = null
    private var category : String? = null

    private var categories : MutableList<String> = mutableListOf<String>()
    private lateinit var viewModel : FragmentViewModel
    private var newCat = false
    private lateinit var addcat : ImageView
    private var catSet = false

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

        viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]

        categories.add("ddd")


        spinnerCat = view.findViewById(R.id.spinnerCategory)

        var spinCAd = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_layout,
            categories)

        spinCAd.setDropDownViewResource( R.layout.spinner_item_layout)
        spinnerCat.setAdapter(spinCAd)

        viewModel.viewModelScope.launch(Dispatchers.Main) {

            categories = viewModel.getAllCategories() as MutableList<String>
            //categories.add(getString(R.string.newCat))

            spinCAd = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item_layout,
                categories)

            spinCAd.setDropDownViewResource( R.layout.spinner_item_layout)
            spinnerCat.setAdapter(spinCAd)
            catSet = true

            /*
            spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position.equals(categories.size-1))
                    {
                        Log.i("AAAAA","new item")
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle(R.string.addItem)

                        val editText = EditText(requireContext())
                        editText.setHint(R.string.addItemEdit)
                        builder.setView(editText)

                        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                            if(editText.text!=null && !(editText.text.toString().equals("")))
                            {
                                if(!newCat) {
                                    categories.set(categories.size - 1, editText.text.toString())
                                    categories.add(getString(R.string.newCat))
                                    newCat = true
                                }else
                                {
                                    categories.set(categories.size - 2, editText.text.toString())
                                }

                                Log.i("AAAAA","1${editText.text}2")
                            }
                        }

                        builder.setNegativeButton(android.R.string.no) { dialog, which ->

                        }
                        builder.show()
                    }
                    else
                        Log.i("AAAAA","pos $position")
                }

            }

             */
        }

        addcat = view.findViewById(R.id.imageNewCat)
        addcat.setOnClickListener{

            if(catSet) {

                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.addItem)

                val editText = EditText(requireContext())
                editText.setHint(R.string.addItemEdit)
                builder.setView(editText)

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    if (editText.text != null && !(editText.text.toString().equals(""))) {
                        if (!newCat) {
                            categories.add(editText.text.toString())
                            newCat = true
                        } else {
                            categories.set(categories.size - 1, editText.text.toString())
                        }

                        spinCAd.notifyDataSetChanged()
                        spinnerCat.setSelection(categories.size-1)

                        Log.i("AAAAA", "1${editText.text}2")
                    }
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->

                }
                builder.show()
            }
        }


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
        spinnerImp = view.findViewById(R.id.spinnerImportance)

        val impArray = arrayOf<String>(Importance.max.name,Importance.mid.name,Importance.min.name)

        val spinIAd = ArrayAdapter(
                requireContext(),
            R.layout.spinner_item_layout,
        impArray)

        spinIAd.setDropDownViewResource( R.layout.spinner_item_layout)

        spinnerImp.setAdapter(spinIAd)


        switch.setOnCheckedChangeListener{buttonView, isChecked ->

            if (isChecked) {
                switch.setText(R.string.Deadline)
                button.setVisibility(View.VISIBLE);
                spinnerImp.setVisibility(View.GONE);
            } else {
                switch.setText(R.string.Importance)
                button.setVisibility(View.GONE);
                spinnerImp.setVisibility(View.VISIBLE);
            }
        }



        Log.i("AAAAstartf2", "fragment 2 started")

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
        val str = "$p1-$p2-$p3 00:00"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        deadline = LocalDateTime.parse(str, formatter)
        button.setText("$p3.$p2.$p1")

    }


}