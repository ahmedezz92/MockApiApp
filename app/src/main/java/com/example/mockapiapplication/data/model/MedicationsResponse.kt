package com.example.mockapiapplication.data.model

data class MedicationsResponse(
    val problems: List<Problem>
)

// Problem data class
data class Problem(
    val Diabetes: List<DiabetesInfo>?,
)

// Diabetes-specific information
data class DiabetesInfo(
    val medications: List<Medication>?,
)