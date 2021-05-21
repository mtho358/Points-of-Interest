package com.coolcats.localinterestsapplication.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolcats.localinterestsapplication.GooglePlaceRetrofit
import com.coolcats.localinterestsapplication.model.Result
import com.coolcats.localinterestsapplication.util.FunctionUtility.Companion.toFormattedString
import com.coolcats.localinterestsapplication.util.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel(){

    private var netJob: Job? = null

    private val retrofit = GooglePlaceRetrofit()

    val liveData: MutableLiveData<List<Result>> = MutableLiveData()

    val statusData = MutableLiveData<State>()


    fun getPlacesNearMe(location: Location, type: String){
        statusData.value = State.LOADING

        netJob = viewModelScope.launch {
            try{
                val result = retrofit.makeApiCallAsync(
                    type, //Replace with value from spinner
                    location.toFormattedString(),
                    1000
                ).await()
                liveData.postValue(result.results)
                statusData.postValue(State.SUCCESS)
            }catch (e: Exception){
                Log.d("TAG_M", e.toString())
                statusData.postValue(State.ERROR)
            }
        }
    }


    override fun onCleared() {
        netJob?.cancel()
        super.onCleared()
    }
}