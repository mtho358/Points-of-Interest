package com.coolcats.localinterestsapplication.view


import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.coolcats.localinterestsapplication.MyLocationListener
import com.coolcats.localinterestsapplication.R
import com.coolcats.localinterestsapplication.util.State
import com.coolcats.localinterestsapplication.viewmodel.PlacesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var locationManager: LocationManager
    private lateinit var myLocation: Location
    private val viewModel: PlacesViewModel by viewModels()
    private val placesAdapter = PlacesAdapter(listOf())
    private lateinit var selectedOption: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.options_list, android.R.layout.simple_spinner_dropdown_item)
        options_spinner.adapter = spinnerAdapter

        options_spinner.onItemSelectedListener = this

        viewModel.liveData.observe(this, {
            placesAdapter.updatePlaces(it)
        })

        viewModel.geoData.observe(this, {
            location_textview.text = it.formatted_address
        })

        places_recyclerview.adapter = placesAdapter

        viewModel.statusData.observe(this, {
            when(it){
                State.LOADING -> status_progressbar.visibility = View.VISIBLE
                State.ERROR -> displayError()
                else -> status_progressbar.visibility = View.GONE
            }
        })

    }

    private fun displayError(){
        status_progressbar.visibility = View.GONE
        Snackbar.make(view_main, "An error occured!", Snackbar.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            5000L,
            5f,
            myLocationListener
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(myLocationListener)
    }

    private val myLocationListener = MyLocationListener(
        object : MyLocationListener.LocationDelegate {
        override fun provideLocation(location: Location){
            myLocation = location
        }
    })

    private fun makeApiCall(location: Location) {
        viewModel.getPlacesNearMe(location, selectedOption)
        //viewModel.getMyLocation(location)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        selectedOption = parent?.getItemAtPosition(position).toString()
            if(this::myLocation.isInitialized) {
                Log.d("TAG_M", selectedOption)
                makeApiCall(myLocation)
            }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}