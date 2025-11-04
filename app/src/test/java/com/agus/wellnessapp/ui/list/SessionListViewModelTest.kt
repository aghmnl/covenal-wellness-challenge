package com.agus.wellnessapp.ui.list

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.domain.usecase.GetFavoriteIdsUseCase
import com.agus.wellnessapp.domain.usecase.GetNetworkErrorsUseCase
import com.agus.wellnessapp.domain.usecase.GetSessionsUseCase
import com.agus.wellnessapp.domain.usecase.ToggleFavoriteUseCase
import com.agus.wellnessapp.util.MainDispatcherRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SessionListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getSessionsUseCase: GetSessionsUseCase

    @RelaxedMockK
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @RelaxedMockK
    private lateinit var getFavoriteIdsUseCase: GetFavoriteIdsUseCase

    @RelaxedMockK
    private lateinit var getNetworkErrorsUseCase: GetNetworkErrorsUseCase

    private lateinit var viewModel: SessionListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    /**
     * Test case for the successful initialization path.
     * We check if the UI state correctly moves from loading
     * to showing the list of sessions.
     */
    @Test
    fun init_loadsSessions_onSuccess() = runTest {
        // 1. Arrange (Set up the mocks)
        val testPoses = listOf(
            Pose(id = 1, englishName = "Test Pose 1"),
            Pose(id = 2, englishName = "Test Pose 2")
        )
        coEvery { getSessionsUseCase() } returns Result.success(testPoses)
        every { getFavoriteIdsUseCase() } returns flowOf(emptySet())
        every { getNetworkErrorsUseCase() } returns flowOf()

        viewModel = SessionListViewModel(
            getSessionsUseCase,
            getFavoriteIdsUseCase,
            toggleFavoriteUseCase,
            getNetworkErrorsUseCase
        )

        val finalState = viewModel.uiState.value
        assertFalse("isLoading should be false after success", finalState.isLoading)
        assertNull("Error should be null on success", finalState.error)
        assertEquals("Sessions list should match test data", testPoses, finalState.sessions)
    }

    /**
     * Test case for the failure path.
     * We check if the UI state correctly shows an error
     * when the Use Case returns a failure.
     */
    @Test
    fun init_setsError_onFailure() = runTest {
        // 1. Arrange (Set up the mocks)
        val error = Exception("Network Error")
        coEvery { getSessionsUseCase() } returns Result.failure(error)
        every { getFavoriteIdsUseCase() } returns flowOf(emptySet())
        every { getNetworkErrorsUseCase() } returns flowOf()

        viewModel = SessionListViewModel(
            getSessionsUseCase,
            getFavoriteIdsUseCase,
            toggleFavoriteUseCase,
            getNetworkErrorsUseCase
        )

        val finalState = viewModel.uiState.value
        assertFalse("isLoading should be false after failure", finalState.isLoading)
        assertTrue("Sessions list should be empty on failure", finalState.sessions.isEmpty())
        assertEquals("Error message should be set", "Network Error", finalState.error)
    }

    /**
     * Test that calling onToggleFavorite correctly calls the
     * underlying ToggleFavoriteUseCase function.
     */
    @Test
    fun onToggleFavorite_callsUseCase() = runTest {
        runBlocking {
            coEvery { getSessionsUseCase() } returns Result.success(emptyList())
            every { getFavoriteIdsUseCase() } returns flowOf(emptySet())
            every { getNetworkErrorsUseCase() } returns flowOf()
        }
        coEvery { toggleFavoriteUseCase(any()) } just Runs

        viewModel = SessionListViewModel(
            getSessionsUseCase,
            getFavoriteIdsUseCase,
            toggleFavoriteUseCase,
            getNetworkErrorsUseCase
        )

        viewModel.onToggleFavorite("123")

        coVerify(exactly = 1) {
            toggleFavoriteUseCase("123")
        }
    }
}