package com.example.mockapiapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mockapiapplication.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: MyAppViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Blue.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    stringResource(id = R.string.label_username),
                    textAlign = TextAlign.Center
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue.copy(alpha = 0.7f),
                unfocusedBorderColor = Color.Gray
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    stringResource(id = R.string.label_password),
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue.copy(alpha = 0.7f),
                unfocusedBorderColor = Color.Gray
            )
        )

        Button(
            onClick = {
                viewModel.saveUsername(username)
                onLoginSuccess()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Blue.copy(alpha = 0.7f)
            ),
        ) {
            Text(
                stringResource(id = R.string.label_Login),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}