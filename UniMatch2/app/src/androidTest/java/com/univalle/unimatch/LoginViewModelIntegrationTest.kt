package com.univalle.unimatch

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.auth.FirebaseAuth
import com.univalle.unimatch.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginViewModelIntegrationTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        // Obtener el contexto (requerido por Firebase en tests instrumentados)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val auth = FirebaseAuth.getInstance()
        viewModel = LoginViewModel(auth)
    }

    @Test
    fun loginUser_withValidCredentials_returnsSuccess() = runBlocking {
        val email = "diego.tolosa@correounivalle.edu.co"
        val password = "123456"

        viewModel.loginUser(email, password)

        val result = viewModel.loginResult.first { it != null }

        assertTrue(result?.isSuccess == true)
        assertEquals(true, result?.getOrNull())
    }

    @Test
    fun loginUser_withInvalidCredentials_returnsFailure() = runBlocking {
        val email = "noexiste@correounivalle.edu.co"
        val password = "clavefalsa"

        viewModel.loginUser(email, password)

        val result = viewModel.loginResult.first { it != null }

        assertTrue(result?.isFailure == true)
        assertNotNull(result?.exceptionOrNull())
    }
}


