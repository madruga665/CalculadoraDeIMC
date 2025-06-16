package com.madruga665.calculadoradeimc.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

fun calculateIMC(height: Double, weight: Double): Double {
    val convertedHeight = height / 100
    val result = weight / (convertedHeight * convertedHeight)

    return result
}

fun handleCalculateIMC(height: TextFieldState, weight: TextFieldState, imc: MutableDoubleState, imcDescription: MutableState<String>) {
    val result = calculateIMC(height.text.toString().toDouble(), weight.text.toString().toDouble())
    imc.value = String.format("%.1f", result).toDouble()

    if (result < 18.5) {
        imcDescription.value = "Abaixo do peso"
    }
    if (result >= 18.5 && result <= 24.9) {
        imcDescription.value = "Peso normal"
    }
    if (result >= 25 && result <= 29.9) {
        imcDescription.value = "Sobrepeso"
    }
}

@Composable
fun Form() {
    var height = rememberTextFieldState()
    val weight = rememberTextFieldState()
    val imc = remember { mutableDoubleStateOf(0.0) }
    val imcDescription = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            state = height,
            label = { Text("Altura em cm") },
            placeholder = { Text("170") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

            )
        OutlinedTextField(
            state = weight,
            label = { Text("Peso em kg") },
            placeholder = { Text("80") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Text("IMC: ${imc.value}")
        Text(imcDescription.value)
        Button(
            modifier = Modifier.padding(top = 100.dp),
            onClick = { handleCalculateIMC(height, weight, imc, imcDescription) }
        ) { Text("Calcular") }
    }
}