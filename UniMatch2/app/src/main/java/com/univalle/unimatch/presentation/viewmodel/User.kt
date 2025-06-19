package com.univalle.unimatch.presentation.viewmodel

data class User(
    val uid: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val nombreCuenta: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val interests: List<String> = emptyList(),
    val photos: List<String> = emptyList(),
    val verified: Boolean = false
) {
    // Función para calcular la edad desde birthDate
    fun getAge(): Int {
        return try {
            val parts = birthDate.split("/")
            if (parts.size == 3) {
                val day = parts[0].toInt()
                val month = parts[1].toInt()
                val year = parts[2].toInt()

                val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1
                val currentDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)

                var age = currentYear - year
                if (currentMonth < month || (currentMonth == month && currentDay < day)) {
                    age--
                }
                age
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }
    }

    // Función para obtener el nombre completo
    fun getFullName(): String {
        return "$nombre $apellido".trim()
    }

    // Función para obtener la primera foto como foto de perfil
    fun getProfilePhoto(): String? {
        return photos.firstOrNull()
    }
}