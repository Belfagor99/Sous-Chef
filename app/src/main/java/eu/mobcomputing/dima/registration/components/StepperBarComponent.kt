package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R


/**
 * StepperBar: A composable function for displaying a horizontal stepper bar.
 *
 * This composable creates a Row containing individual StepperItem components for each step in the provided
 * list. It allows visualizing the progress of completing steps, with the current step highlighted and completed
 * steps differentiated.
 *
 * @param steps A list of strings representing the names of each step in the process.
 * @param currentStep The index of the current step in the process.
 *
 */
@Composable
fun StepperBar(steps: List<String>, currentStep: Int) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, step ->
            StepperItem(
                text = step,
                isCompleted = index < currentStep,
                isCurrent = index == currentStep
            )
            if (index < steps.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

/**
 * StepperItem: A composable function for displaying an individual step within a stepper bar.
 *
 * This composable creates a Row containing an Icon (representing completion status) and Text (displaying the step
 * text). The appearance of the step is determined by the provided parameters, indicating whether the step is
 * completed, current, or pending.
 *
 * @param text The text to be displayed for the step.
 * @param isCompleted A boolean flag indicating whether the step is completed.
 * @param isCurrent A boolean flag indicating whether the step is the current step.
 *
 */
@Composable
fun StepperItem(text: String, isCompleted: Boolean, isCurrent: Boolean) {
    val color = when {
        isCompleted -> colorResource(id = R.color.light_green)
        isCurrent -> colorResource(id = R.color.pink_600)
        else -> colorResource(id = R.color.pink_200)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when {
                isCompleted -> Icons.Default.Check
                else -> Icons.Default.Circle
            },
            contentDescription = null,
            tint = color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = color)
    }
}
