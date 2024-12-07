package com.example.mockapiapplication.domain.usecase

import com.example.mockapiapplication.data.core.data.utils.WrappedResponse
import com.example.mockapiapplication.domain.model.BaseResult
import com.example.mockapiapplication.data.model.MedicationsResponse
import com.example.mockapiapplication.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicationsUseCase @Inject constructor(private val appRepository: AppRepository) {
    fun execute(): Flow<BaseResult<WrappedResponse<MedicationsResponse>>> {
        return appRepository.getProblemsList()
    }
}