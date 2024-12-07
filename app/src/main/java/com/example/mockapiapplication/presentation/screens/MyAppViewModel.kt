package com.example.mockapiapplication.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mockapiapplication.domain.model.BaseResult
import com.example.mockapiapplication.data.core.data.utils.ErrorResponse
import com.example.mockapiapplication.data.datasource.local.AppDao
import com.example.mockapiapplication.data.datasource.local.AppEntity
import com.example.mockapiapplication.data.model.AssociatedDrug
import com.example.mockapiapplication.domain.usecase.GetMedicationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAppViewModel @Inject constructor(
    private val getMedicationsUseCase: GetMedicationsUseCase, private val appDao: AppDao
) : ViewModel() {
    private val _medicationsState =
        MutableStateFlow<GetMedicationsState>(GetMedicationsState.IsLoading)
    val medicationsState: StateFlow<GetMedicationsState> = _medicationsState.asStateFlow()
    private val _usernameState = MutableStateFlow<String?>(null)
    val usernameState: StateFlow<String?> = _usernameState.asStateFlow()

    init {
        getMedications()
    }

    private fun getMedications() {
        viewModelScope.launch {
            getMedicationsUseCase.execute().onStart {
                _medicationsState.value = GetMedicationsState.IsLoading
            }.catch {
                _medicationsState.value = GetMedicationsState.Error(
                    ErrorResponse(it.message!!)
                )
            }.collect { result ->
                when (result) {
                    is BaseResult.ErrorState -> {
                        _medicationsState.value = GetMedicationsState.Error(
                            result.errorResponse ?: ErrorResponse("Unknown error")
                        )
                    }

                    is BaseResult.DataState -> {
                        val drugsList = mutableListOf<AssociatedDrug>()
                        result.items?.data?.problems?.firstOrNull()?.Diabetes?.firstOrNull()?.medications?.firstOrNull()?.medicationsClasses?.forEach { medicationClass ->
                            medicationClass.className?.forEach { classNameWrapper ->
                                classNameWrapper.associatedDrug?.let {
                                    drugsList.addAll(it)
                                }
                                classNameWrapper.associatedDrugSecondary?.let {
                                    drugsList.addAll(it)
                                }
                            }

                            medicationClass.className2?.forEach { classNameWrapper ->
                                classNameWrapper.associatedDrug?.let {
                                    drugsList.addAll(it)
                                }
                                classNameWrapper.associatedDrugSecondary?.let {
                                    drugsList.addAll(it)
                                }
                            }
                        }

                        _medicationsState.value = GetMedicationsState.Success(drugsList)

                    }
                }
            }
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            appDao.saveUsername(AppEntity(username = username))
            _usernameState.value = username
        }
    }

    fun fetchUsername() {
        viewModelScope.launch {
            val savedUsername = appDao.getUsername()
            _usernameState.value = savedUsername
        }
    }
}

sealed class GetMedicationsState {
    object IsLoading : GetMedicationsState()
    data class Success(val data: MutableList<AssociatedDrug>) : GetMedicationsState()
    data class Error(val errorResponse: ErrorResponse) : GetMedicationsState()
}