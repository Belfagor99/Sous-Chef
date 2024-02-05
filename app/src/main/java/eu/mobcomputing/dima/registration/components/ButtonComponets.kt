package eu.mobcomputing.dima.registration.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R


/**
 * ButtonComponent: A composable function for creating a custom-styled button.
 *
 * This composable creates a Button with specific customization options such as label, click action,
 * and enabled status. It uses a Box as a container to allow for custom background color based on the
 * enabled status. The button's appearance is determined by the provided [value], [onClickAction], and
 * [isEnabled] parameters.
 *
 * @param value The text to be displayed on the button.
 * @param onClickAction A lambda function representing the action to be performed on button click.
 * @param isEnabled A boolean flag indicating whether the button is enabled or disabled.
 *
 */

@Composable
fun ButtonComponent(
    value: String,
    onClickAction: () -> Unit,
    isEnabled: Boolean = false,
    buttonDescription: String = ""
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .semantics {
                contentDescription =
                    if (buttonDescription.isNotEmpty()) buttonDescription else value

            },
        onClick = {
            onClickAction.invoke()
        },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    color = if (isEnabled) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    )

                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = if (isInPortraitMode()) 18.sp else 24.sp, // Adjust text size based on the screen orientation
                color = colorResource(id = R.color.pink_100),
                fontWeight = FontWeight.Bold
            )

        }

    }
}

/**
 * SmallButtonComponent: A composable function for creating a smaller-sized custom-styled button.
 *
 * This composable creates a Button with specific customization options such as label, click action,
 * and a custom modifier for additional styling. It uses a Box as a container to allow for a custom
 * background color with rounded corners. The button's appearance is determined by the provided [value],
 * [onClickAction], and [modifier] parameters.
 *
 * @param value The text to be displayed on the button.
 * @param onClickAction A lambda function representing the action to be performed on button click.
 * @param modifier Additional styling options for the button.
 *
 */
@Composable
fun SmallButtonComponent(value: String, onClickAction: () -> Unit, modifier: Modifier) {
    Button(
        onClick = { onClickAction.invoke() },
        enabled = true,
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    color = colorResource(id = R.color.pink_900), shape = RoundedCornerShape(50.dp)
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                color = colorResource(id = R.color.pink_100),
                fontWeight = FontWeight.Bold
            )


        }
    }
}

@Composable
private fun isInPortraitMode(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}
