
package com.example.healthapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.example.healthapp.databinding.FragmentCreateHabitBinding
import com.example.healthapp.habit.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class CreateHabitFragment: Fragment() {

    companion object {
        val GOAL_ITEMS = listOf("COUNT", "SEC", "MET", "PAGES")
        val WEEKDAYS: Map<String, Int> = mapOf("MONDAY" to 1, "TUESDAY" to 2, "WEDNESDAY" to 3, "THURSDAY" to 4,
                "FRIDAY" to 5, "SATURDAY" to 6, "SUNDAY" to 7)
    }

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: CreateHabitViewModel

    private lateinit var newPeriodicity: Periodicity
    private lateinit var newGoal: Goal
    private lateinit var newHabit: Habit

    private lateinit var habitService: HabitService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(CreateHabitViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Customizing day picker
        binding.dayPicker.locale = Locale.ENGLISH
        binding.dayPicker.firstDayOfWeek = MaterialDayPicker.Weekday.MONDAY

        // Adding items to the goal list
        val goals = GOAL_ITEMS
        val adapter = ArrayAdapter(requireContext(), R.layout.goal_item, goals)
        (binding.goalList as? AutoCompleteTextView)?.setAdapter(adapter)

        // Check whether all of the fields are filled
        binding.createButton.setOnClickListener {
            if (binding.titleEdit.text.isNotBlank() && binding.descEdit.text.isNotBlank() &&
                    binding.dayPicker.selectedDays.isNotEmpty() && binding.valueEdit.text.isNotBlank()) {
                // Adding selected days
                newPeriodicity = Periodicity()
                newPeriodicity.daysOfWeek = convertWeekDays(binding.dayPicker.selectedDays)

                // Adding goal
                newGoal = Goal()
                newGoal.unit = binding.goalList.text.toString()
                newGoal.value = binding.valueEdit.text.toString().toInt()

                // Adding habit
                newHabit = Habit()
                newHabit.user = viewModel.userId.value
                newHabit.name = binding.titleEdit.text.toString()
                newHabit.description = binding.descEdit.text.toString()
                newHabit.periodicity = viewModel.perId.value
                newHabit.goal = viewModel.goalId.value
                newHabit.isGood = true

                habitService = ServiceBuilder.buildService(HabitService::class.java)

                sendRequests()

                viewModel.perId.observe(viewLifecycleOwner, { id ->
                    newHabit.periodicity = id
                })

                viewModel.goalId.observe(viewLifecycleOwner, { id ->
                    newHabit.goal = id
                })
            }
            else {
                Toast.makeText(context, "Please, fill all of the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertWeekDays(list: List<MaterialDayPicker.Weekday>): MutableList<Int?> {
        val result = mutableListOf<Int?>()
        for (elem in list) {
            result.add(WEEKDAYS[elem.toString()])
        }
        return result
    }

    private fun sendRequests() {
        createPeriodicity()
    }

    private fun createPeriodicity() {
        val perCall = habitService.setPeriodicity(newPeriodicity)

        perCall.enqueue(object: retrofit2.Callback<Periodicity> {
            override fun onResponse(call: Call<Periodicity>, response: Response<Periodicity>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully added periodicity", Toast.LENGTH_SHORT).show()
                    viewModel.setPerId(response.body()?.id!!)
                    createGoal()
                }
                else {
                    Toast.makeText(context, "Failed to add periodicity", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Periodicity>, t: Throwable) {
                Toast.makeText(context, "Failed to add periodicity", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createGoal() {
        val goalCall = habitService.setGoal(newGoal)

        goalCall.enqueue(object: retrofit2.Callback<Goal> {
            override fun onResponse(call: Call<Goal>, response: Response<Goal>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully added goal", Toast.LENGTH_SHORT).show()
                    viewModel.setGoalId(response.body()?.id!!)
                    createHabit()
                }
                else {
                    Toast.makeText(context, "Failed to add goal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Goal>, t: Throwable) {
                Toast.makeText(context, "Failed to add goal", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createHabit() {
        val habitCall = habitService.setHabit(newHabit)

        habitCall.enqueue(object: retrofit2.Callback<Habit> {
            override fun onResponse(call: Call<Habit>, response: Response<Habit>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Successfully added habit", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "Failed to add habit", Toast.LENGTH_SHORT).show()
                }
                // While back-end is not fixed
                binding.createButton.setOnClickListener {
                    it.findNavController().navigate(CreateHabitFragmentDirections.actionCreateHabitFragmentToListHabitFragment())
                }
            }

            override fun onFailure(call: Call<Habit>, t: Throwable) {
                Toast.makeText(context, "Failed to add habit", Toast.LENGTH_SHORT).show()
                // While back-end is not fixed
                binding.createButton.setOnClickListener {
                    it.findNavController().navigate(CreateHabitFragmentDirections.actionCreateHabitFragmentToListHabitFragment())
                }
            }
        })
    }
}