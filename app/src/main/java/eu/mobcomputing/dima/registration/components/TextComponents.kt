package eu.mobcomputing.dima.registration.components

//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R

/**
 * NormalTextComponent: A composable function for rendering normal text in the app.
 *
 * This composable creates a Text component with the specified [value] and applies
 * common styling properties such as font size, weight, style, color, and text alignment.
 *
 * @param value The text content to be displayed.
 *
 */
@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value, modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp), style = TextStyle(
            fontSize = 12.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.base_text_color), textAlign = TextAlign.Center
    )
}

/**
 * HeaderTextComponent: A composable function for rendering header text in the app.
 *
 * This composable creates a Header Text component with the specified [value] and applies
 * common styling properties such as font size, weight, style, color, and text alignment.
 *
 * @param value The text content to be displayed.
 *
 */
@Composable
fun HeaderTextComponent(value: String) {
    Text(
        text = value, modifier = Modifier
            .fillMaxWidth()
            .heightIn(), style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.base_text_color), textAlign = TextAlign.Center
    )
}

/**
 * MyRedHeadingComponent: A composable function for creating a red-colored heading text.
 *
 * This composable creates a Text component with specific customization options such as
 * text value, text style, color, and alignment. It is designed for displaying headings with
 * a red color. The appearance of the heading is determined by the provided [value] and
 * [shouldBeCentered] parameters.
 *
 * @param value The text content of the heading.
 * @param shouldBeCentered A boolean flag indicating whether the heading should be centered.
 *
 */
@Composable
fun MyRedHeadingComponent(value: String, shouldBeCentered: Boolean = false) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal,
        ),
        color = colorResource(id = R.color.pink_900),
        textAlign = if (shouldBeCentered) TextAlign.Center else TextAlign.Left
    )
}

/**
 * MyTextFieldComponent: A composable function for creating a customized OutlinedTextField.
 *
 * This composable creates an OutlinedTextField with specific customization options such as
 * label, icon, colors, and error status. It also allows tracking text changes through the
 * [onTextSelected] callback.
 *
 * @param labelValue The label text for the text field.
 * @param leadingIcon The icon vector to be displayed as a leading icon.
 * @param onTextSelected A callback function triggered when the text in the field changes.
 * @param errorStatus A boolean flag indicating whether the text field has an error.
 *
 */

@Composable
fun MyTextFieldComponent(
    labelValue: String,
    leadingIcon: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
) {

    val textValue: MutableState<String> = remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        label = { Text(text = labelValue) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.pink_100),
            unfocusedContainerColor = colorResource(id = R.color.pink_100),
            cursorColor = colorResource(id = R.color.pink_900),
            focusedBorderColor = colorResource(id = R.color.light_green),
            unfocusedBorderColor = colorResource(id = R.color.pink_900)
        ),
        keyboardOptions = KeyboardOptions.Default,
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = leadingIcon, contentDescription = null)
        },
        isError = !errorStatus,
    )
}

/**
 * MyPasswordFieldComponent: A composable function for creating a customized password input field.
 *
 * This composable creates an OutlinedTextField with specific customization options for password input,
 * such as label, icon, visibility toggle, and error status. It also allows tracking text changes through
 * the [onTextSelected] callback and supports read-only mode.
 *
 * @param labelValue The label text for the password field.
 * @param leadingIcon The icon vector to be displayed as a leading icon.
 * @param onTextSelected A callback function triggered when the text in the field changes.
 * @param errorStatus A boolean flag indicating whether the password field has an error.
 *
 */

@Composable
fun MyPasswordFieldComponent(
    labelValue: String,
    leadingIcon: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
) {
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),

        label = {
            Text(text = labelValue)

        },
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(painter = leadingIcon, contentDescription = null)
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = if (passwordVisible.value) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible.value) "Show Password" else "Hide Password"
                )
            }
        },

        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus,

        )
}

/**
 * ClickableLoginTextComponent: A composable function for creating clickable text with a login-related action.
 *
 * This composable creates a ClickableText component with specific customization options such as
 * text style, color, and an annotation for the login-related action. It is designed for displaying
 * text that allows users to perform a specific action (navigating to the sign-up screen) when clicked.
 *
 * @param onClickAction A lambda function representing the action to be performed on text click.
 *
 */
@Composable
fun ClickableLoginTextComponent(onClickAction: () -> Unit) {
    val initialText = "Not have an account? "
    val loginText = "Sign up"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = colorResource(id = R.color.pink_900))) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.End,
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onClickAction.invoke()
                    }
                }

        },
    )
}

/**
 * ClickableRegisterTextComponent: A composable function for creating clickable text with a register-related action.
 *
 * This composable creates a ClickableText component with specific customization options such as
 * text style, color, and an annotation for the login-related action. It is designed for displaying
 * text that allows users to perform a specific action (e.g., navigating to the log-in screen) when clicked.
 *
 * @param onClickAction A lambda function representing the action to be performed on text click.
 *
 */
@Composable
fun ClickableRegisterTextComponent(onClickAction: () -> Unit) {
    val initialText = "Already have an account? "
    val loginText = "Log in"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = colorResource(id = R.color.pink_900))) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 21.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.End,
        ),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")
                    if (span.item == loginText) {
                        onClickAction.invoke()
                    }
                }

        },
    )
}

/**
 * ClickableForgottenPasswordTextComponent: A composable function for creating clickable text with a forgotten password action.
 *
 * This composable creates a ClickableText component with specific customization options such as
 * text style, color, and an annotation for the login-related action. It is designed for displaying
 * text that allows users to perform a specific action (e.g., trigger sending reset password e-mail) when clicked.
 *
 * @param onClickAction A lambda function representing the action to be performed on text click.
 *
 */
@Composable
fun ClickableForgottenPasswordTextComponent(onClickAction: () -> Unit) {
    val loginText = stringResource(id = R.string.forgot_passwd)

    val annotatedString = buildAnnotatedString {

        withStyle(style = SpanStyle(color = colorResource(id = R.color.pink_900))) {
            pushStringAnnotation(tag = "", annotation = loginText)
            append(loginText)
        }
    }

    ClickableText(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Right
        ),
        text = annotatedString,
        onClick = { offset ->

            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onClickAction.invoke()
                    }
                }

        },
    )
}
