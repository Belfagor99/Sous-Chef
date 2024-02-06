package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R

/**
 * WrongPasswordSubmitterComponent: A composable function for displaying information about wrong password submission attempts.
 *
 * This composable creates a Column containing two Text components, one for displaying a message about
 * entering a wrong password and another for indicating the remaining number of password entry attempts.
 * The number displayed informing about remaining number of password entry attempts of the text is
 * determined by the provided [numberOfTries] parameter.
 *
 * @param numberOfTries The number of remaining password entry attempts.
 *
 */
@Composable
fun WrongPasswordSubmitterComponent(numberOfTries: Int, compDesc: String = "wrong password") {
    val wrongPasswordText = stringResource(id = R.string.wrong_password)
    val remainingTriesText = stringResource(id = R.string.remaining_tries)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics { contentDescription = compDesc }, // Optional: Add padding if needed
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = wrongPasswordText,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = colorResource(id = R.color.base_text_color)
            )
        )

        Text(
            text = "$remainingTriesText $numberOfTries",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                color = colorResource(id = R.color.pink_900)
            )
        )


    }

}
