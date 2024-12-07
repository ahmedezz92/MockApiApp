package com.example.mockapiapplication.data.repository

import com.example.mockapiapplication.data.core.data.utils.WrappedErrorResponse
import com.example.mockapiapplication.data.core.data.utils.WrappedResponse
import com.example.mockapiapplication.data.datasource.remote.AppApi
import com.example.mockapiapplication.domain.model.BaseResult
import com.example.mockapiapplication.data.model.MedicationsResponse
import com.example.mockapiapplication.domain.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApi
) : AppRepository {

    override fun getProblemsList(): Flow<BaseResult<WrappedResponse<MedicationsResponse>>> {
        return flow {
            val response = api.getMedications()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                val errorBody = response.errorBody()?.charStream()
                val type = object : TypeToken<WrappedErrorResponse>() {}.type
                val errorResponse: WrappedErrorResponse =
                    Gson().fromJson(errorBody, type)
                emit(BaseResult.ErrorState(errorResponse.errorResponse))
            }
        }
    }
}
