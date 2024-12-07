package com.example.mockapiapplication.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mockapiapplication.data.model.AssociatedDrug

@Composable
fun DetailsScreen(
    drug: AssociatedDrug?,
) {
    drug?.let { item ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item.strength?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            }
            item.dose?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}