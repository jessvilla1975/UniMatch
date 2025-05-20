package com.univalle.unimatch.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.univalle.unimatch.R
import kotlinx.coroutines.tasks.await

class GoogleAuthClient(private val context: Context) {
    private val tokenId = context.getString(R.string.client_id)  // ID de cliente web de Firebase
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(tokenId)   // Solicita el ID token
        .requestEmail()
        .build()
    private val googleSignInClient = GoogleSignIn.getClient(context, gso)
    private val auth = Firebase.auth

    // Intent para iniciar sesión con Google
    fun getSignInIntent(): Intent = googleSignInClient.signInIntent

    // Procesa el resultado de la Intent de Google y autentica en Firebase
    suspend fun signInWithGoogle(data: Intent): FirebaseUser? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = FirebaseAuth.getInstance().signInWithCredential(credential).await()
            result.user
        } catch (e: Exception) {
            Log.e("GoogleAuthClient", "Error en signInWithGoogle: ${e.message}", e)
            null
        }
    }

    // Cierra sesión en Firebase y Google
    fun signOut() {
        auth.signOut()
        googleSignInClient.signOut()
    }
}