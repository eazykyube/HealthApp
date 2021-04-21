package com.example.healthapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateHabitViewModel: ViewModel() {

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val _perId = MutableLiveData<Int>()
    val perId: LiveData<Int>
    get() = _perId

    private val _goalId = MutableLiveData<Int>()
    val goalId: LiveData<Int>
        get() = _goalId

    init {
        _userId.value = 29
        _perId.value = 0
        _goalId.value = 0
    }

    fun setPerId(n: Int) {
        _perId.value = n
    }

    fun setGoalId(n: Int) {
        _goalId.value = n
    }
}