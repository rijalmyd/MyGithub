package com.rijaldev.mygithub.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rijaldev.mygithub.domain.repository.SettingRepository
import com.rijaldev.mygithub.util.DummyTest
import com.rijaldev.mygithub.util.LiveDataTestUtil.getOrAwaitValue
import com.rijaldev.mygithub.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var settingRepository: SettingRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(settingRepository)
    }

    @Test
    fun `when getTheme, Should Return True`() {
        val isDarkModeActive = DummyTest.isDarkModeActiveTrueDummy
        `when`(settingRepository.getTheme()).thenReturn(flowOf(isDarkModeActive))

        val actualValue = mainViewModel.getTheme().getOrAwaitValue()
        verify(settingRepository).getTheme()
        assertNotNull(actualValue)
        assertTrue(actualValue)
    }

    @Test
    fun `when getTheme, Should Return False`() {
        val isDarkModeActive = DummyTest.isDarkModeActiveFalseDummy
        `when`(settingRepository.getTheme()).thenReturn(flowOf(isDarkModeActive))

        val actualValue = mainViewModel.getTheme().getOrAwaitValue()
        verify(settingRepository).getTheme()
        assertNotNull(actualValue)
        assertFalse(actualValue)
    }

    @Test
    fun `when setTheme, Should Call setTheme in SettingRepository`() = runTest {
        val isDarkModeActive = DummyTest.isDarkModeActiveTrueDummy
        mainViewModel.setTheme(isDarkModeActive)
        verify(settingRepository).setTheme(isDarkModeActive)
    }
}