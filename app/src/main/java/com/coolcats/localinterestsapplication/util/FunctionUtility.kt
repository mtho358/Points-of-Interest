package com.coolcats.localinterestsapplication.util

import android.location.Location
import android.widget.SpinnerAdapter
import com.coolcats.localinterestsapplication.util.Constants.Companion.RESTAURANT

class FunctionUtility {

    companion object{

        fun Location.toFormattedString(): String = "${this.latitude}, ${this.longitude}"

    }
}