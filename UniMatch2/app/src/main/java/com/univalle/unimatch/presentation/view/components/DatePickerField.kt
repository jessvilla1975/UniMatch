package com.univalle.unimatch.presentation.view.components


import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univalle.unimatch.presentation.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun DatePickerField(viewModel: RegisterViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                viewModel.birthDate = selectedDate
                viewModel.birthDateError = validarFechaNacimiento(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = viewModel.birthDate,
            onValueChange = {
                viewModel.birthDate = it
                viewModel.birthDateError = validarFechaNacimiento(it)
            },
            label = { Text("Fecha de Nacimiento", color = Color.White) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Seleccionar fecha",
                    tint = Color.White,
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            readOnly = true,
            isError = viewModel.birthDateError != null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color.White,
                focusedIndicatorColor = if (viewModel.birthDateError != null) Color.Red else Color.White,
                unfocusedIndicatorColor = if (viewModel.birthDateError != null) Color.Red else Color.Gray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
        viewModel.birthDateError?.let {
            Text(
                text = it,
                color = Color.Yellow,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}

fun validarFechaNacimiento(input: String): String? {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.isLenient = false
        val fecha = sdf.parse(input)
        val hoy = Calendar.getInstance()

        if (fecha == null || fecha.after(hoy.time)) {
            "Fecha de nacimiento no válida"
        } else {
            hoy.add(Calendar.YEAR, -18)
            if (fecha.after(hoy.time)) {
                "Debes tener al menos 18 años"
            } else null
        }
    } catch (e: Exception) {
        "Formato inválido: dd/MM/yyyy"
    }
}

