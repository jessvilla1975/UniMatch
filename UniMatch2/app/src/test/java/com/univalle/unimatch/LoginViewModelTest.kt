@file:OptIn(ExperimentalCoroutinesApi::class)
package com.univalle.unimatch

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.univalle.unimatch.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor


class LoginViewModelTest {

    private lateinit var mockAuth: FirebaseAuth
    private lateinit var mockTask: Task<AuthResult>
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        println("⚙️  Ejecutando prueba...")
        mockAuth = mock(FirebaseAuth::class.java)
        @Suppress("UNCHECKED_CAST")
        mockTask = mock(Task::class.java) as Task<AuthResult>

        `when`(mockAuth.signInWithEmailAndPassword(anyString(), anyString()))
            .thenReturn(mockTask)

        loginViewModel = LoginViewModel(mockAuth)
        println("✅ prueba completada\n")
    }

    @Test
    fun `loginUser successful sets loginResult to success`() = runTest {
        println("🚀 Iniciando test: login exitoso")

        `when`(mockTask.isSuccessful).thenReturn(true)
        val captor = argumentCaptor<OnCompleteListener<AuthResult>>()
        `when`(mockTask.addOnCompleteListener(captor.capture())).thenReturn(mockTask)

        loginViewModel.loginUser("admin@gmail.com", "1234")
        captor.value.onComplete(mockTask)

        val result = loginViewModel.loginResult.first()
        println("🔍 Resultado obtenido: $result")

        assertTrue("❌ Se esperaba éxito en login, pero fue: $result", result?.isSuccess == true)
        assertEquals("❌ El valor retornado no fue true", true, result?.getOrNull())

        println("✅ Test de login exitoso \n")
    }

    @Test
    fun `loginUser failed sets loginResult to failure`() = runTest {
        println("🚀 Iniciando test: login fallido")

        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(Exception("Fallo de autenticación"))

        val captor = argumentCaptor<OnCompleteListener<AuthResult>>()
        `when`(mockTask.addOnCompleteListener(captor.capture())).thenReturn(mockTask)

        loginViewModel.loginUser("admin@gmail.com", "wrongpass")
        captor.value.onComplete(mockTask)

        val result = loginViewModel.loginResult.first()
        println("🔍 Resultado obtenido: $result")

        assertTrue("❌ Se esperaba fallo, pero fue: $result", result?.isFailure == true)
        assertEquals("❌ El mensaje de error no coincide", "Fallo de autenticación", result?.exceptionOrNull()?.message)

        println("✅ Test de login fallido \n")
    }

    @Test
    fun `resetLoginResult sets loginResult to null`() = runTest {
        println("🚀 Iniciando test: reset de loginResult")

        `when`(mockTask.isSuccessful).thenReturn(true)
        val captor = argumentCaptor<OnCompleteListener<AuthResult>>()
        `when`(mockTask.addOnCompleteListener(captor.capture())).thenReturn(mockTask)

        loginViewModel.loginUser("admin@gmail.com", "1234")
        captor.value.onComplete(mockTask)

        loginViewModel.resetLoginResult()
        val result = loginViewModel.loginResult.first()
        println("🔍 Resultado después de reset: $result")

        assertNull("❌ Se esperaba null después de reset, pero fue: $result", result)

        println("✅ Test de reset \n")
    }

    // Helper para capturar argumentos genéricos
    private inline fun <reified T> argumentCaptor(): ArgumentCaptor<T> =
        ArgumentCaptor.forClass(T::class.java)
}




