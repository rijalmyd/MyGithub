package com.rijaldev.mygithub.presentation.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.domain.repository.UserRepository
import com.rijaldev.mygithub.util.DummyTest
import com.rijaldev.mygithub.util.LiveDataTestUtil.getOrAwaitValue
import com.rijaldev.mygithub.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var homeViewModel: HomeViewModel
    private val dummyQuery = DummyTest.dummyQuery
    private val dummyUsers = DummyTest.generateDummySearchUsers()
    private val dummyError = DummyTest.generateErrorMessage()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(userRepository)
    }

    @Test
    fun `when searchUsers Should Return Result Success`() {
        val expectedValue = MutableLiveData<Result<List<User>>>()
        expectedValue.value = Result.Success(dummyUsers)

        `when`(userRepository.searchUsers(dummyQuery)).thenReturn(expectedValue)

        homeViewModel.fetchSearchUsers(dummyQuery)
        val actualValue = homeViewModel.searchUsers.getOrAwaitValue()
        assertTrue(actualValue is Result.Success)
        assertNotNull((actualValue as Result.Success).data)
        assertEquals(dummyUsers.size, actualValue.data?.size)
    }

    @Test
    fun `when searchUsers Should Return Result Error`() {
        val expectedValue = MutableLiveData<Result<List<User>>>()
        expectedValue.value = Result.Error(dummyError)

        `when`(userRepository.searchUsers(dummyQuery)).thenReturn(expectedValue)

        homeViewModel.fetchSearchUsers(dummyQuery)
        val actualValue = homeViewModel.searchUsers.getOrAwaitValue()
        assertTrue(actualValue is Result.Error)
        assertNotNull((actualValue as Result.Error).message)
        assertEquals(dummyError, actualValue.message)
    }
}