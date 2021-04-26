package com.example.healthapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthapp.databinding.FragmentListHabitBinding
import com.example.healthapp.habit.Habit
import com.example.healthapp.habit.HabitService
import com.example.healthapp.habit.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class ListHabitFragment: Fragment() {

    private lateinit var viewModel: CreateHabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentListHabitBinding.inflate(inflater)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(CreateHabitViewModel::class.java)

        val getHabitCall = viewModel.habitService.value?.getHabit()

        binding.table.layoutManager = LinearLayoutManager(context)
        binding.table.itemAnimator = DefaultItemAnimator()
        binding.table.adapter = viewModel.adapter.value

        getHabitCall?.enqueue(object: retrofit2.Callback<List<Habit>> {
            override fun onResponse(call: Call<List<Habit>>, response: Response<List<Habit>>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully got habits", Toast.LENGTH_SHORT).show()
                    viewModel.adapter.value?.data = response.body()!!
                }
                else {
                    Toast.makeText(context, "Failed to get habits", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Habit>>, t: Throwable) {
                Toast.makeText(context, "Failed to get habits", Toast.LENGTH_SHORT).show()
            }
        })

        binding.addButton.setOnClickListener {
            it.findNavController().navigate(ListHabitFragmentDirections.actionListHabitFragmentToCreateHabitFragment())
        }

        return binding.root
    }
}