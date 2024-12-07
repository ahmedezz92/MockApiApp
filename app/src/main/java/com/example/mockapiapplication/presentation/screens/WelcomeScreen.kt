package com.example.mockapiapplication.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mockapiapplication.data.model.AssociatedDrug
import com.example.mockapiapplication.presentation.components.ItemCard
import com.example.mockapiapplication.presentation.utils.getGreetingBasedOnTime

@Composable
fun WelcomeScreen(
    viewModel: MyAppViewModel = hiltViewModel(),
    onItemClick: (AssociatedDrug) -> Unit
) {
    val medicationsState by viewModel.medicationsState.collectAsState()
    val savedUsername by viewModel.usernameState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUsername()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            savedUsername?.let {
                Text(
                    getGreetingBasedOnTime(it),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            when (val state = medicationsState) {
                is GetMedicationsState.IsLoading -> {
                    CircularProgressIndicator()
                }

                is GetMedicationsState.Success -> {
                    val medications = state.data
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(medications) { medication ->
                            ItemCard(associatedDrug = medication,
                                onItemClick = { onItemClick(medication) })
                        }
                    }
                }

                is GetMedicationsState.Error -> {
                    Text(
                        text = "Error: ${state.errorResponse.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {}
            }
        }
    }
}