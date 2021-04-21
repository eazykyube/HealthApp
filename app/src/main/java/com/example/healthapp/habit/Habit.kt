package com.example.healthapp.habit

import com.google.gson.annotations.SerializedName

data class Habit (
        var id: Int? = null,
        var user: Int? = null,
        var name: String? = null,
        var description: String? = null,
        var periodicity: Int? = null,
        var goal: Int? = null,
        @SerializedName("is_good")
        var isGood: Boolean? = null,
        @SerializedName("start_day")
        var startDay: String? = null
        )