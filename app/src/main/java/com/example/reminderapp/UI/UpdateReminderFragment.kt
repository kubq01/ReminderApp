package com.example.reminderapp.UI

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.reminderapp.FragmentViewModel
import com.example.reminderapp.Importance
import com.example.reminderapp.R
import com.example.reminderapp.ReminderObject
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpdateReminderFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var param1: String? = null
    private var param2: String? = null
    private val args : UpdateReminderFragmentArgs by navArgs()

    private lateinit var switch : SwitchCompat
    private lateinit var spinnerImp : Spinner
    private lateinit var button : Button
    private lateinit var spinnerCat : Spinner


    //data to pass to ReminderObject
    private var reminderText : String = ""
    private var containsDeadline : Boolean  = false
    private var deadline : LocalDateTime? = null
    private var importance : Importance = Importance.min
    private var startDate : LocalDateTime = LocalDateTime.now()
    private var category : String = ""

    private lateinit var reminder : ReminderObject

    private var categories : MutableList<String> = mutableListOf<String>()
    private lateinit var viewModel : FragmentViewModel
    private var newCat = false
    private lateinit var addcat : ImageView
    private var catSet = false
    private lateinit var editText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(editText.text.toString().equals(""))
                {
                    //Toast.makeText(requireContext(),R.string.missingText,Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(getFragment()).navigate(R.id.action_updateReminderFragment_to_listFragment)
                    return
                }

                if(switch.isChecked && !containsDeadline)
                {
                    Toast.makeText(requireContext(),R.string.missingDeadline,Toast.LENGTH_SHORT).show()
                    Log.i("AAAAAA","switch error")
                    NavHostFragment.findNavController(getFragment()).navigate(R.id.action_updateReminderFragment_to_listFragment)
                    return

                }

                reminderText = editText.text.toString()

                if(!catSet)
                {
                    NavHostFragment.findNavController(getFragment()).navigate(R.id.action_updateReminderFragment_to_listFragment)
                    return
                }

                category = spinnerCat.selectedItem.toString()

                if(!containsDeadline)
                {
                    importance = Importance.valueOf(spinnerImp.selectedItem.toString())
                }else
                {
                    setImportance()
                }


                deadline?.let{
                    reminder.reminderText = reminderText
                    reminder.containsDeadline = containsDeadline
                    reminder.deadline = it
                    reminder.importance = importance
                    reminder.category = category
                    viewModel.updateReminder(reminder)
                    NavHostFragment.findNavController(getFragment()).navigate(R.id.action_updateReminderFragment_to_listFragment)
                } ?: kotlin.run {
                    Toast.makeText(requireContext(),R.string.missingDeadline,Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(getFragment()).navigate(R.id.action_updateReminderFragment_to_listFragment)
                }

            }
        })

    }

    private fun getFragment(): Fragment{
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_new_reminder, container, false)

        //view.findViewById<EditText>(R.id.editTextReminderText).setText(args.reminder.reminderText)

        reminderText = args.reminder.reminderText
        containsDeadline = args.reminder.containsDeadline
        deadline = args.reminder.deadline
        importance = args.reminder.importance
        startDate = args.reminder.startDate
        category = args.reminder.category

        reminder = args.reminder

        editText = view.findViewById(R.id.editTextReminderText)
        editText.setText(args.reminder.reminderText)

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

            if(categories.size.equals(0))
                categories.add(getString(R.string.defaultCat))

            spinCAd = ArrayAdapter(
                requireContext(),
                R.layout.spinner_item_layout,
                categories)

            spinCAd.setDropDownViewResource( R.layout.spinner_item_layout)
            spinnerCat.setAdapter(spinCAd)

            spinnerCat.setSelection(categories.indexOf(args.reminder.category))
            catSet = true
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
        if(containsDeadline)
            button.setText("${args.reminder.deadline.dayOfMonth}.${args.reminder.deadline.monthValue}.${args.reminder.deadline.year}")
        button.setVisibility(View.GONE)

        button.setOnClickListener(View.OnClickListener {
            if(containsDeadline) {
                val datePicker: DatePickerDialog = DatePickerDialog(
                    requireContext(),
                    this,
                    args.reminder.deadline.year,
                    args.reminder.deadline.monthValue,
                    args.reminder.deadline.dayOfMonth,
                )
                datePicker.show()
            }else{

                val datePicker: DatePickerDialog = DatePickerDialog(
                    requireContext(),
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                )
                datePicker.show()
            }
        })

        switch = view.findViewById(R.id.switchImpOrDL)
        containsDeadline = args.reminder.containsDeadline
        spinnerImp = view.findViewById(R.id.spinnerImportance)

        val impArray = arrayOf<String>(Importance.max.name,Importance.mid.name,Importance.min.name)

        val spinIAd = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item_layout,
            impArray)

        spinIAd.setDropDownViewResource( R.layout.spinner_item_layout)

        spinnerImp.setAdapter(spinIAd)

        spinnerImp.setSelection(impArray.indexOf(args.reminder.importance.name))

        if(containsDeadline)
        {
            switch.isChecked = true
            switch.setText(R.string.Deadline)
            button.setVisibility(View.VISIBLE);
            spinnerImp.setVisibility(View.GONE);
        }


        switch.setOnCheckedChangeListener{buttonView, isChecked ->

            if (isChecked) {
                switch.setText(R.string.Deadline)
                button.setVisibility(View.VISIBLE);
                spinnerImp.setVisibility(View.GONE);
               // containsDeadline = true
            } else {
                switch.setText(R.string.Importance)
                button.setVisibility(View.GONE);
                spinnerImp.setVisibility(View.VISIBLE);
                containsDeadline = false
            }
        }

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            if(editText.text.toString().equals(""))
            {
                Toast.makeText(requireContext(),R.string.missingText,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(switch.isChecked && !containsDeadline)
            {
                Toast.makeText(requireContext(),R.string.missingDeadline,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            reminderText = editText.text.toString()

            if(!catSet)
                return@setOnClickListener

            category = spinnerCat.selectedItem.toString()

            if(!containsDeadline)
            {
                importance = Importance.valueOf(spinnerImp.selectedItem.toString())
            }else
            {
                setImportance()
            }


            deadline?.let{
                reminder.reminderText = reminderText
                reminder.containsDeadline = containsDeadline
                reminder.deadline = it
                reminder.importance = importance
                reminder.category = category
                viewModel.updateReminder(reminder)
                NavHostFragment.findNavController(this).navigate(R.id.action_updateReminderFragment_to_listFragment)
            } ?: kotlin.run {
                Toast.makeText(requireContext(),R.string.missingDeadline,Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }




        }

        Log.i("AAAAstartf3", "fragment 3 started")

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateReminderFragment.
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
        var str : String
        if(p3<10)
            str = "$p1-$p2-0$p3 00:00"
        else
            str = "$p1-$p2-$p3 00:00"
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        deadline = LocalDateTime.parse(str, formatter)
        containsDeadline = true
        button.setText("$p3.$p2.$p1")
    }

    private fun setImportance()
    {
        val current : LocalDateTime = LocalDateTime.now()

        val timeLeft : Duration = Duration.between(current, deadline)
        val timeLeftDays : Long = timeLeft.toDays()

        val timeGiven : Duration = Duration.between(startDate, deadline)
        val timeGivenDays : Long = timeGiven.toDays()

        val percentage : Double = (timeLeftDays*1.0)/(timeGivenDays*1.0)*100

        if(percentage<33)
        {
            importance = Importance.max
        }else if(percentage<66)
        {
            importance = Importance.mid
        }else
        {
            importance = Importance.min
        }
    }


}