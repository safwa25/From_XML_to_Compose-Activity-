package com.safwa.fromxmltocomposeactivity

import android.app.DatePickerDialog
import android.os.Bundle
import android.icu.util.Calendar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.filled.ArrowDropDown



class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                MedicationScreen()
            }
        }
    }
}

@Composable
fun MedicationScreen() {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("Choose Date") }
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var recurrence by remember { mutableStateOf("Daily") }
    val recurrenceOptions = listOf("Daily", "Weekly", "Monthly")
    val selectedChips = remember { mutableStateListOf<String>() }
    val chipOptions = listOf("Morning", "Afternoon", "Evening", "Night")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Add Medication", fontSize = 32.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif)
Spacer(modifier = Modifier.height(12.dp))
        Text("Medication Name", fontSize = 18.sp,fontFamily = FontFamily.SansSerif)
        CustomUnderlinedField(
            value = medicationName,
            onValueChange = { medicationName = it },
            hint = "Enter name"
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Dosage", fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.height(8.dp))
                CustomUnderlinedField(
                    value = dosage,
                    onValueChange = {
                        if (it.all { ch -> ch.isDigit() }) dosage = it
                    },
                    hint = "5",
                    keyboardType = KeyboardType.Number
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Recurrence", fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.height(8.dp))
                StyledSpinner(
                    options = recurrenceOptions,
                    selectedOption = recurrence,
                    onOptionSelected = { recurrence = it },
                    //height = 72.dp
                )
            }
        }
       Spacer(modifier = Modifier.height(8.dp))

        Text("End Date", fontSize = 18.sp,fontFamily = FontFamily.SansSerif)
        StyledDateButton(
            text = selectedDate,
            onClick = {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(context, { _, y, m, d ->
                    selectedDate = "$d/${m + 1}/$y"
                }, year, month, day).show()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text("Times of Day", fontSize = 18.sp,  fontFamily = FontFamily.SansSerif)
        Column {
            chipOptions.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { option ->
                        FilterChip(
                            selected = selectedChips.contains(option),
                            onClick = {
                                if (selectedChips.contains(option))
                                    selectedChips.remove(option)
                                else
                                    selectedChips.add(option)
                            },
                            label = { Text(
                                option,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.SansSerif) },
                            leadingIcon = if (selectedChips.contains(option)) {
                                { Icon(Icons.Default.Check, contentDescription = null) }
                            } else null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Toast.makeText(context, "Medication saved", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF674FA3))
        ) {
            Text("Save", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Composable
fun StyledDateButton(text: String, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEDE0F5),
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd =4.dp)
                )
                .clickable { onClick() }
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .height(52.dp)
        ) {
            Text(
                text = text,
                color = Color(0xFF373535),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            )
        }
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF6B43B5))
        )
    }
}

@Composable
fun StyledSpinner(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    //height: Dp = 64.dp
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
               // .height(height)
                .background(
                    color = Color(0xFFEDE0F5),
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = selectedOption,
                    color = Color(0xFF373535),
                    fontSize = 16.sp,

                    fontFamily = FontFamily.SansSerif
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color(0xFF373535)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF6B43B5))
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onOptionSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun CustomUnderlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFEDE0F5),
                    shape = RoundedCornerShape(topStart =4.dp, topEnd = 4.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(hint) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )
        }
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color(0xFF6B43B5))
        )
    }
}

