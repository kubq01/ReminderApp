package com.example.reminderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.time.LocalDateTime
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.viewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var servis : ServisClass //= ServisClass(applicationContext)

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        servis = ServisClass(applicationContext)



        var allData : LiveData<List<ReminderObject>> = servis.showAll()
        val viewModelFactory = MainActivityViewModelFactory(125, applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.showAll().observe(this, Observer {
            findViewById<TextView>(R.id.textView).text = it.toString()
        })



    }

    fun onClickInsert(view : View)
    {
       val r : ReminderObject = ReminderObject(1,false,
           LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(),Importance.max,"cat1")


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