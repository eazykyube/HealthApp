package com.example.healthapp.habit

import com.google.gson.annotations.SerializedName

data class Periodicity(

    var id: Int = 0,
    @SerializedName("days_of_week")
    var daysOfWeek: List<Int>? = null

)