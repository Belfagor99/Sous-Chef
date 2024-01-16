package eu.mobcomputing.dima.registration.components.add_ingredients

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Composable function representing a date picker dialog for selecting a date.
 *
 * @param onDateSelected Callback function invoked when a date is selected. It provides the selected date as a formatted string.
 * @param onDismiss Callback function invoked when the date picker is dismissed without selecting a date.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {


    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            // Make dates in the past non-selectable
            return utcTimeMillis >= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        // Convert selected date in milliseconds to a formatted string
        convertMillisToDate(it)
    } ?: ""


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePickerDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {

                // --------- OK button ---------
                Button(
                    onClick = {
                        onDateSelected(selectedDate)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.pink_900)),

                    ) {
                    Text(
                        text = "OK",
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.pink_100),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                // --------- Cancel button ---------
                Button(
                    onClick = {
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.pink_900)),
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.pink_100),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

/**
 * Converts milliseconds to a formatted date string.
 *
 * @param millis The time in milliseconds.
 * @return Formatted date string in the "dd/MM/yyyy" format.
 */
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
    return formatter.format(Date(millis))
}



@Preview
@Composable
fun DatePickerPreview(){
    DatePickerView({},{})
}