package com.univalle.unimatch.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
)
 {
     fun registerUser(
         email: String,
         password: String,
         nombre: String,
         apellido: String,
         nombreCuenta: String,
         phoneNumber: String,
         birthDate: String,
         gender: String,
         onSuccess: () -> Unit,
         onError: (String) -> Unit,
     ) {
         auth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     val user = auth.currentUser
                     val uid = user?.uid

                     val userData = mapOf(
                         "uid" to uid,
                         "nombre" to nombre,
                         "apellido" to apellido,
                         "nombreCuenta" to nombreCuenta,
                         "email" to email,
                         "phoneNumber" to phoneNumber,
                         "birthDate" to birthDate,
                         "gender" to gender,
                         "verified" to false
                     )

                     uid?.let {
                         firestore.collection("users")
                             .document(it)
                             .set(userData)
                             .addOnFailureListener { e ->
                                 onError("Error al guardar datos en Firestore: ${e.message}")
                             }
                     }
                 } else {
                     onError("Error al registrar usuario: ${task.exception?.message}")
                 }
             }
         }
     fun getCurrentUser() = auth.currentUser
}