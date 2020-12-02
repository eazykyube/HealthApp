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
import kotlinx.android.synthetic.main.fragment_create_habit.view.*
import retrofit2.Call
import retrofit2.Response
import java.time.DayOfWeek
import javax.security.auth.callback.Callback

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

    @RequiresApi(Build.VERSION_CODES.O)
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
                            Toast.makeText(context, "Successfully added periodicity", Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(context, "Failed to add periodicity", Toast.LENGTH_LONG).show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDays(): MutableList<Int> {
        var _daysOfWeek: MutableList<Int> = ArrayList()
        if (binding.monCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.MONDAY.value)
        }
        if (binding.tueCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.TUESDAY.value)
        }
        if (binding.wedCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.WEDNESDAY.value)
        }
        if (binding.thuCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.THURSDAY.value)
        }
        if (binding.friCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.FRIDAY.value)
        }
        if (binding.satCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.SATURDAY.value)
        }
        if (binding.sunCheck.isChecked) {
            _daysOfWeek.add(DayOfWeek.SUNDAY.value)
        }
        return _daysOfWeek
    }

}