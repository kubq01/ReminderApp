package com.example.reminderapp.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.reminderapp.*
import java.time.LocalDateTime
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var servis : ServisClass //= ServisClass(applicationContext)

    lateinit var viewModel : FragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        servis = ServisClass(applicationContext)

        viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]

        updateData()

        /*
        //var allData : LiveData<List<ReminderObject>> = servis.showAll()
        //val viewModelFactory = MainActivityViewModelFactory(125, applicationContext)


        viewModel.showAll().observe(this, Observer {

            //findViewById<TextView>(R.id.textView).text = it.toString()
            Log.i("AALiveData","changed")
        })


         */



    }

    fun updateData()
    {
        val sharedP = this.getPreferences(Context.MODE_PRIVATE)
        var day = sharedP.getInt("DAY",-1)
        var month = sharedP.getInt("MONTH",-1)
        var year = sharedP.getInt("YEAR",-1)

        val curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val curMonth = Calendar.getInstance().get(Calendar.MONTH)
        val curYear = Calendar.getInstance().get(Calendar.YEAR)

        val editor = sharedP.edit()

        if((year<0 || month<0 || day<0)||(year!=curYear || month!=curMonth || day!=curDay))
        {
            viewModel.updateData()
            editor.putInt("YEAR",curYear)
            editor.putInt("MONTH", curMonth)
            editor.putInt("DAY",curDay)
            editor.commit()

        }

    }

    fun onClickInsert(view : View)
    {
       val r = ReminderObject("text",false,
           LocalDateTime.now(), Importance.max, LocalDateTime.now(),"cat1")


        viewModel.insertReminder(r)
    }

    fun onClickDeleteAll(view : View)
    {
        viewModel.deleteAll()
    }

    /*
    fun showReminders()
    {
       var allData : LiveData<List<ReminderObject>> = servis.showAll()
        val viewModelFactory = MainActivityViewModelFactory(125, applicationContext)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.showAll().observe(this, Observer {
            binding.countText.text = it.toString()
        })

        binding.button.setOnClickListener {
            viewModel.updateCount()
        }
    }
    */




}