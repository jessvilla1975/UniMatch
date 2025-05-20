package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.univalle.unimatch.ui.theme.UvMatchTheme



@Composable
fun AddphotosScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Agrega fotos",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Subtítulo
        Text(
            text = "Agrega al menos 2 fotos para poder",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "continuar",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Espacio para fotos (simulado)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar foto",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar foto",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Botón CONTINUAR
        Button(
            onClick = { /* Sin lógica */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD81B60)
            )
        ) {
            Text("CONTINUAR", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPhotosScreenPreview() {
    UvMatchTheme {
        AddphotosScreen()
    }
}



