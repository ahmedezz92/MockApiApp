package com.example.mockapiapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mockapiapplication.data.core.data.utils.ErrorResponse
import com.example.mockapiapplication.data.core.data.utils.WrappedResponse
import com.example.mockapiapplication.data.datasource.local.AppDao
import com.example.mockapiapplication.data.model.AssociatedDrug
import com.example.mockapiapplication.data.model.ClassInfo
import com.example.mockapiapplication.data.model.DiabetesInfo
import com.example.mockapiapplication.data.model.Medication
import com.example.mockapiapplication.data.model.MedicationClass
import com.example.mockapiapplication.data.model.MedicationsResponse
import com.example.mockapiapplication.data.model.Problem
import com.example.mockapiapplication.domain.model.BaseResult
import com.example.mockapiapplication.domain.repository.AppRepository
import com.example.mockapiapplication.domain.usecase.GetMedicationsUseCase
import com.example.mockapiapplication.presentation.screens.GetMedicationsState
import com.example.mockapiapplication.presentation.screens.MyAppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MyAppViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var appDao: AppDao

    private lateinit var getMedicationsUseCase: GetMedicationsUseCase
    private lateinit var viewModel: MyAppViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Create the use case with the mocked repository
        getMedicationsUseCase = GetMedicationsUseCase(appRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test save and fetch username`() = runTest {
        // Arrange
        val testUsername = "testUser"

        // Simulate saving username
        whenever(appDao.saveUsername(any())).thenAnswer { /* Do nothing */ }

        // Simulate fetching username
        whenever(appDao.getUsername()).thenReturn(testUsername)

        // Create ViewModel with mocked dependencies
        viewModel = MyAppViewModel(getMedicationsUseCase, appDao)

        // Act
        viewModel.saveUsername(testUsername)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.fetchUsername()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        // Verify DAO methods were called
        verify(appDao).saveUsername(any())
        verify(appDao).getUsername()

        // Check username state
        assertEquals(testUsername, viewModel.usernameState.value)
    }

    @Test
    fun `test medications state on successful fetch`() = runTest {
        // Arrange
        val mockMedicationsResponse = createSuccessfulMedicationsResponse()
        whenever(appRepository.getProblemsList()).thenReturn(
            flow { emit(mockMedicationsResponse) }
        )

        // Create ViewModel with mocked dependencies
        viewModel = MyAppViewModel(getMedicationsUseCase, appDao)

        // Wait for the state to be updated
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertTrue(viewModel.medicationsState.value is GetMedicationsState.Success)

        // Additional check for the data
        val successState = viewModel.medicationsState.value as GetMedicationsState.Success
        assertTrue(successState.data.isNotEmpty())
    }

    @Test
    fun `test medications state on error`() = runTest {
        // Arrange
        val mockErrorResponse = createErrorMedicationsResponse()
        whenever(appRepository.getProblemsList()).thenReturn(
            flow { emit(mockErrorResponse) }
        )

        // Create ViewModel with mocked dependencies
        viewModel = MyAppViewModel(getMedicationsUseCase, appDao)

        // Wait for the state to be updated
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        assertTrue(viewModel.medicationsState.value is GetMedicationsState.Error)
    }

    private fun createSuccessfulMedicationsResponse(): BaseResult.DataState<WrappedResponse<MedicationsResponse>> {
        val associatedDrug = AssociatedDrug(
            name = "TestDrug",
            strength = "200",
            dose = "10mg"
        )

        val medicationClass = MedicationClass(
            className = listOf(
                ClassInfo(
                    associatedDrug = listOf(associatedDrug)
                )
            ),
            className2 = listOf(
                ClassInfo(
                    associatedDrug = listOf(associatedDrug)
                )
            )
        )

        val medication = Medication(
            medicationsClasses = listOf(medicationClass)
        )

        val diabetes = DiabetesInfo(
            medications = listOf(medication)
        )

        val problem = Problem(
            Diabetes = listOf(diabetes)
        )

        val medicationsResponse = MedicationsResponse(
            problems = listOf(problem)
        )

        return BaseResult.DataState(
            WrappedResponse(code = 200, status = "success", data = medicationsResponse)
        )
    }

    private fun createErrorMedicationsResponse(): BaseResult.ErrorState {
        return BaseResult.ErrorState(
            ErrorResponse("Test Error")
        )
    }
}