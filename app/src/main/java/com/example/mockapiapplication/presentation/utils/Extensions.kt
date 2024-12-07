package com.example.mockapiapplication.presentation.utils

import androidx.navigation.NavController
import com.example.mockapiapplication.data.model.AssociatedDrug
import com.google.gson.Gson
import java.util.Calendar

fun getGreetingBasedOnTime(username: String): String {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        currentHour < 12 -> "Good Morning, $username!"
        currentHour < 18 -> "Good Afternoon, $username!"
        else -> "Good Evening, $username!"
    }
}

fun navigateToDetails(navController: NavController, drug: AssociatedDrug) {
    val drugJson = Gson().toJson(drug)
    navController.navigate("details/${drugJson}")
}