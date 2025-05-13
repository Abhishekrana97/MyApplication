package com.example.myapplication.data.repositoryImpl


import com.example.myapplication.utils.SettingsPreference
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SettingsRepositoryImplTest {

    private lateinit var repository: SettingsRepositoryImpl
    private val settingsPreference: SettingsPreference = mock()  // Mock SettingsPreference

    @Before
    fun setUp() {
        repository = SettingsRepositoryImpl(settingsPreference)
    }

    @Test
    fun `test getDarkModePreference returns correct value`() = runTest {
        // Mock flow to return true (for example, dark mode is enabled)
        whenever(settingsPreference.isDarkModeEnabled).thenReturn(flowOf(true))

        // Call the method under test
        val result = repository.getDarkModePreference()

        // Assert that the result is true (dark mode enabled)
        result.collect { value ->
            assertTrue(value)
        }

        // Verify that settingsPreference is accessed
        verify(settingsPreference).isDarkModeEnabled
    }

    @Test
    fun `test saveDarkModePreference calls setDarkMode`() = runTest {
        // Mock nothing, just verify that save method calls setDarkMode with the correct value
        val darkModeValue = true

        // Call the method under test
        repository.saveDarkModePreference(darkModeValue)

        // Verify that the setDarkMode method was called with the correct value
        verify(settingsPreference).setDarkMode(darkModeValue)
    }

    @Test
    fun `test getFingerprintPreference returns correct value`() = runTest {
        // Mock flow to return false (for example, fingerprint is not enabled)
        whenever(settingsPreference.isFingerprintEnabled).thenReturn(flowOf(false))

        // Call the method under test
        val result = repository.getFingerprintPreference()

        // Assert that the result is false (fingerprint is not enabled)
        result.collect { value ->
            assertEquals(false, value)
        }

        // Verify that settingsPreference is accessed
        verify(settingsPreference).isFingerprintEnabled
    }

    @Test
    fun `test saveFingerprintPreference calls setFingerprint`() = runTest {
        // Mock nothing, just verify that save method calls setFingerprint with the correct value
        val fingerprintValue = true

        // Call the method under test
        repository.saveFingerprintPreference(fingerprintValue)

        // Verify that the setFingerprint method was called with the correct value
        verify(settingsPreference).setFingerprint(fingerprintValue)
    }


    @Test
    fun testSetLanguagePreference() = runTest {
        // Mock nothing, just verify that save method calls savePreferredLanguage with the correct value
        val fingerprintValue = 0

        // Call the method under test
        repository.saveLanguagePreference(fingerprintValue)

        // Verify that the savePreferredLanguage method was called with the correct value
        verify(settingsPreference).savePreferredLanguage(fingerprintValue)
    }


    @Test
    fun testGetFingerprintPreference() = runTest {
        // Mock flow to return 0
        whenever(settingsPreference.getPreferredLanguage).thenReturn(flowOf(0))

        // Call the method under test
        val result = repository.getLanguagePreference()

        // Assert that the result is 0
        result.collect { value ->
            assertEquals(0, value)
        }

        // Verify that settingsPreference is accessed
        verify(settingsPreference).getPreferredLanguage
    }

}
