package com.agus.wellnessapp.ui.list

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.repository.FavoritesRepository
import com.agus.wellnessapp.data.repository.SessionRepository
import com.agus.wellnessapp.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.mockkStatic
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

    // This rule swaps main dispatchers for testing coroutines
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // This rule executes all architecture component tasks synchronously
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Create relaxed mocks (they return default values for non-stubbed methods)
    @RelaxedMockK
    private lateinit var sessionRepository: SessionRepository

    @RelaxedMockK
    private lateinit var favoritesRepository: FavoritesRepository

    @RelaxedMockK
    private lateinit var applicationContext: Application

    // The ViewModel we are testing
    private lateinit var viewModel: SessionListViewModel

    @Before
    fun setUp() {
        // Initialize all the @RelaxedMockK annotated properties in this class
        MockKAnnotations.init(this)

        // Mock all static Log methods to do nothing
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
        // Mock the repositories to return successful data
        coEvery { sessionRepository.getSessions() } returns Result.success(testPoses)
        every { favoritesRepository.getFavoriteIds() } returns flowOf(emptySet())

        // 2. Act (Create the ViewModel, which triggers 'init')
        viewModel = SessionListViewModel(sessionRepository, favoritesRepository)

        // 3. Assert (Check the final state)
        val finalState = viewModel.uiState.value
        assertFalse("isLoading should be false after success", finalState.isLoading)
        assertNull("Error should be null on success", finalState.error)
        assertEquals("Sessions list should match test data", testPoses, finalState.sessions)
    }

    /**
     * Test case for the failure path.
     * We check if the UI state correctly shows an error
     * when the repository returns a failure.
     */
    @Test
    fun init_setsError_onFailure() = runTest {
        // 1. Arrange (Set up the mocks)
        val error = Exception("Network Error")
        // Mock the repository to return a failure
        coEvery { sessionRepository.getSessions() } returns Result.failure(error)
        every { favoritesRepository.getFavoriteIds() } returns flowOf(emptySet())

        // 2. Act (Create the ViewModel)
        viewModel = SessionListViewModel(sessionRepository, favoritesRepository)

        // 3. Assert (Check the final state)
        val finalState = viewModel.uiState.value
        assertFalse("isLoading should be false after failure", finalState.isLoading)
        assertTrue("Sessions list should be empty on failure", finalState.sessions.isEmpty())
        assertEquals("Error message should be set", "Network Error", finalState.error)
    }

    /**
     * Test that calling onToggleFavorite correctly calls the
     * underlying repository function.
     */
    @Test
    fun onToggleFavorite_callsRepository() = runTest {
        // 1. Arrange (Set up mocks and create the ViewModel)
        // We can use runBlocking here because it's just setup
        runBlocking {
            coEvery { sessionRepository.getSessions() } returns Result.success(emptyList())
            every { favoritesRepository.getFavoriteIds() } returns flowOf(emptySet())
        }
        // Mock the function we expect to be called
        coEvery { favoritesRepository.toggleFavorite(any()) } just Runs

        viewModel = SessionListViewModel(sessionRepository, favoritesRepository)

        // 2. Act (Call the function)
        viewModel.onToggleFavorite("123")

        // 3. Assert (Verify the function was called)
        coVerify(exactly = 1) {
            favoritesRepository.toggleFavorite("123")
        }
    }
}