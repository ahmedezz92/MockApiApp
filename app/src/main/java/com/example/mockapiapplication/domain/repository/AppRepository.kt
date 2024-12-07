package com.example.mockapiapplication.domain.repository

import com.example.mockapiapplication.data.core.data.utils.WrappedResponse
import com.example.mockapiapplication.domain.model.BaseResult
import com.example.mockapiapplication.data.model.MedicationsResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getProblemsList(): Flow<BaseResult<WrappedResponse<MedicationsResponse>>>
}