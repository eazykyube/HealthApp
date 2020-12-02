package com.example.healthapp.habit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HabitService {

    @POST("week-periodicities/")
    fun setPeriodicity(@Body newPeriodicity: Periodicity): Call<Periodicity>

}