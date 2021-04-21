package com.example.healthapp.habit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HabitService {

    @POST("week-periodicities/")
    fun setPeriodicity(@Body periodicity: Periodicity): Call<Periodicity>

    @POST("goals/")
    fun setGoal(@Body goal: Goal): Call<Goal>

    @POST("habits/")
    fun setHabit(@Body habit: Habit): Call<Habit>

}