package com.example.reminderapp.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.reminderapp.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    lateinit var servis : ServisClass //= ServisClass(applicationContext)

    lateinit var viewModel : FragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        servis = ServisClass(applicationContext)



        /*
        //var allData : LiveData<List<ReminderObject>> = servis.showAll()
        //val viewModelFactory = MainActivityViewModelFactory(125, applicationContext)
        viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]

        viewModel.showAll().observe(this, Observer {

            //findViewById<TextView>(R.id.textView).text = it.toString()
            Log.i("AALiveData","changed")
        })


         */



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