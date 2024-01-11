package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


/**
 * MyImageComponent: A composable function for displaying an image.
 *
 * This composable creates an Image component with specific customization options such as
 * the image resource, modifier, and alignment.
 *
 * @param imageResource The resource ID of the image to be displayed.
 * @param modifier Additional styling options for the image.
 *
 */
@Composable
fun MyImageComponent(imageResource: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(imageResource),
        contentDescription = imageResource.toString(),
        modifier = modifier,
        alignment = Alignment.Center
    )
}

