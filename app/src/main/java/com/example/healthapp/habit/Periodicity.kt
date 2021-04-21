package com.example.healthapp.habit

import com.google.gson.annotations.SerializedName

data class Periodicity(
        var id: Int? = null,
        @SerializedName("days_of_week")
        var daysOfWeek: MutableList<Int?>? = null
)