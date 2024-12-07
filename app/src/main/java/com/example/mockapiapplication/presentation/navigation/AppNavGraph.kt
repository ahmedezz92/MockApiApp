package com.example.mockapiapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mockapiapplication.data.model.AssociatedDrug
import com.example.mockapiapplication.presentation.screens.DetailsScreen
import com.example.mockapiapplication.presentation.screens.LoginScreen
import com.example.mockapiapplication.presentation.screens.WelcomeScreen
import com.example.mockapiapplication.presentation.utils.navigateToDetails
import com.google.gson.Gson

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("welcome")
                }
            )
        }
        composable(
            route = "welcome"
        ) {
            WelcomeScreen(
                onItemClick = { drug ->
                    navigateToDetails(navController, drug)
                })
        }
        composable(
            route = "details/{drugData}",
            arguments = listOf(
                navArgument("drugData") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val drugJson = backStackEntry.arguments?.getString("drugData") ?: ""
            val drug = Gson().fromJson(drugJson, AssociatedDrug::class.java)
            DetailsScreen(drug = drug)
        }
    }
}