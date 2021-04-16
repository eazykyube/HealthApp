package com.example.healthapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.healthapp.databinding.FragmentCreateHabitBinding
import com.example.healthapp.habit.HabitService
import com.example.healthapp.habit.Periodicity
import com.example.healthapp.habit.ServiceBuilder
import retrofit2.Call
import retrofit2.Response
import java.time.DayOfWeek

class CreateHabitFragment: Fragment() {

    var daysOfWeek: MutableList<Int> = ArrayList()
    lateinit var binding: FragmentCreateHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener {
            daysOfWeek = getDays()
            if (daysOfWeek.isNotEmpty()) {
                val newPeriodicity = Periodicity()
                newPeriodicity.daysOfWeek = daysOfWeek

                val habitService = ServiceBuilder.buildService(HabitService::class.java)
                val requestCall = habitService.setPeriodicity(newPeriodicity)

                requestCall.enqueue(object: retrofit2.Callback<Periodicity> {
                    override fun onResponse(call: Call<Periodicity>, response: Response<Periodicity>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Successfully added periodicity", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(context, "Failed to add periodicity", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Periodicity>, t: Throwable) {
                        Toast.makeText(context, "Failed to add periodicity", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else {
                Toast.makeText(context, "Please, select periodicity", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDays(): MutableList<Int> {
        val _daysOfWeek: MutableList<Int> = ArrayList()
        when {
            binding.monCheck.isChecked -> _daysOfWeek.add(1)
            binding.tueCheck.isChecked -> _daysOfWeek.add(2)
            binding.wedCheck.isChecked -> _daysOfWeek.add(3)
            binding.thuCheck.isChecked -> _daysOfWeek.add(4)
            binding.friCheck.isChecked -> _daysOfWeek.add(5)
            binding.satCheck.isChecked -> _daysOfWeek.add(6)
            binding.sunCheck.isChecked -> _daysOfWeek.add(7)
        }
        return _daysOfWeek
    }
}