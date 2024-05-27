package com.codecat.directwhatsapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import com.codecat.directwhatsapp.databinding.ActivityCountrySelectBinding
import com.sampath.directwhatsapp.CountriesAdapter

/**This activity displays a list of countries to pick a country code form, and also shows the device default country at the top. */
class CountrySelectorActivity : AppCompatActivity() {


    //view binding
    private lateinit var binding: ActivityCountrySelectBinding

    //countries adapter. Passing a function to the parameter for handling item click.
    private val adapter = CountriesAdapter{ countrySelected(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountrySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        //setting up layout display
        binding.counter.text = countries.size.toString()
        binding.recycler.adapter = adapter

        //setting up the device default country.
        setupDeviceCountry()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredList =
                        countries.filter { country -> country.name.contains(newText, true) }
                    if (filteredList.isEmpty())
                        createToast(R.string.no_data)
                    else
                        adapter.onFilter(filteredList)
                }
                return false
            }
        })
    }

    //when a country is selected by the user, its position is sent back to the main activity.
    private fun countrySelected(countryCode: String) {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra("country", countryCode) })
        finish()
    }

    private fun setupDeviceCountry() {

        val deviceCountry = deviceDefaultCountry

        //device country will be null if we are unable to find a match for the device's
        // selected country in our list of countries.
        if (deviceCountry == null) {
            binding.deviceDefault.root.visibility = View.GONE
            binding.defaultHeader.visibility = View.GONE
            return
        }

        //setup default country display
        val name = "${deviceCountry.name}, ${deviceCountry.isoCode}"
        val dialingCode = "+${deviceCountry.isdCode}"
        val referenceId = deviceCountry.flagResource

        binding.deviceDefault.flag.setImageResource(referenceId)
        binding.deviceDefault.countryCode.text = dialingCode
        binding.deviceDefault.countryName.text = name

        binding.deviceDefault.root.setOnClickListener { countrySelected(countryCode = "IO") }
    }



}