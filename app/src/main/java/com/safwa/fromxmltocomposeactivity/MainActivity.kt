package com.safwa.fromxmltocomposeactivity

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.safwa.fromxmltocomposeactivity.ui.theme.FromXMLToComposeActivityTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity_layout)
        val spinner: Spinner = findViewById(R.id.spinner)
        val dateButton: Button = findViewById(R.id.dateButton)
        val saveButton: Button = findViewById(R.id.buttonSave)


        val options = arrayOf("Daily", "Weekly", "Monthly")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                ContextThemeWrapper(this, R.style.DatePickerDialogTheme),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    dateButton.text = selectedDate
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        saveButton.setOnClickListener {
            Toast.makeText(this, "Medication saved", Toast.LENGTH_SHORT).show()
        }
    }
}

