package com.example.mockapiapplication.data.datasource.remote

import com.example.mockapiapplication.data.core.data.utils.WrappedResponse
import com.example.mockapiapplication.data.model.Medication
import com.example.mockapiapplication.data.model.MedicationsResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("102500d0-80ca-4f15-877e-e4efe69f715f")
    suspend fun getMedications(): Response<WrappedResponse<MedicationsResponse>>
}