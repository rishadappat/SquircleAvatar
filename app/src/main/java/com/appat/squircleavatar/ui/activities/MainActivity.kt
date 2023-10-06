package com.appat.squircleavatar.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.appat.graphicov.webservice.service.Status
import com.appat.squircleavatar.databinding.ActivityMainBinding
import com.appat.squircleavatar.ui.adapters.ProfileDataAdapter
import com.appat.squircleavatar.utilities.LinearLayoutManagerWrapper
import com.appat.squircleavatar.viewmodel.PersonsViewModel
import com.appat.squircleavatar.viewmodel.ViewModelFactory
import com.appat.squircleavatar.webservice.api.Api
import com.appat.squircleavatar.webservice.service.WebService
import com.appat.squircleavatar.webservice.serviceinterface.ServiceInterface

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    val api = Api(WebService().getWebService(ServiceInterface::class.java))

    var adapter: ProfileDataAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = ProfileDataAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManagerWrapper(
            this@MainActivity,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = adapter
        initViewModel()
    }

    private fun initViewModel()
    {
        val factory = ViewModelFactory(api)
        val personsViewModel = ViewModelProvider(this, factory).get(PersonsViewModel::class.java)
        getPersons(personsViewModel)
    }

    private fun getPersons(personsViewModel: PersonsViewModel)
    {
        personsViewModel.getPersons().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {  persons ->
                            Log.d(TAG, persons.toString())
                            adapter?.setData(persons.data)
                        }
                    }
                    Status.ERROR -> {
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }
}