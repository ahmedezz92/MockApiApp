package com.example.mockapiapplication.data.model

import com.google.gson.annotations.SerializedName

data class Medication(
    val medicationsClasses: List<MedicationClass>?
)

data class MedicationClass(
    val className: List<ClassInfo>?,
    val className2: List<ClassInfo>?
)

data class ClassInfo(
    @SerializedName("associatedDrug")
    val associatedDrug: List<AssociatedDrug>? = null,
    @SerializedName("associatedDrug#2")
    val associatedDrugSecondary: List<AssociatedDrug>? = null
)

data class AssociatedDrug(
    val name: String?,
    val dose: String?,
    val strength: String?
)