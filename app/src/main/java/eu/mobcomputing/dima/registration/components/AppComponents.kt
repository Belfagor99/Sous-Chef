package eu.mobcomputing.dima.registration.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.MutableState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import eu.mobcomputing.dima.registration.data.home.BottomNavigationItem
import eu.mobcomputing.dima.registration.data.userInformation.Allergen
import eu.mobcomputing.dima.registration.data.userInformation.DietOption
import eu.mobcomputing.dima.registration.navigation.Screen

/** This file contains all the custom made composable components used in the app. */


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
 * @param iconPainterResource The icon painter resource to be displayed as a leading icon.
 * @param onTextSelected A callback function triggered when the text in the field changes.
 * @param errorStatus A boolean flag indicating whether the text field has an error.
 *
 */
@Composable
fun MyTextFieldComponent(
    labelValue: String,
    iconPainterResource: Painter,
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.light_green),
            focusedLabelColor = colorResource(id = R.color.light_green),
            cursorColor = colorResource(id = R.color.pink_900),
            backgroundColor = colorResource(id = R.color.pink_100)
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
            Icon(
                painter = iconPainterResource, contentDescription = null
            )
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
 * @param iconPainterResource The icon painter resource to be displayed as a leading icon.
 * @param onTextSelected A callback function triggered when the text in the field changes.
 * @param errorStatus A boolean flag indicating whether the password field has an error.
 * @param readOnly A boolean flag indicating whether the field is in read-only mode.
 * @param labelValueFiled The label text to be displayed in read-only mode.
 *
 */
@Composable
fun MyPasswordFieldComponent(
    labelValue: String,
    iconPainterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    readOnly: Boolean = false,
    labelValueFiled: String = ""
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
            if (!readOnly) {
                Text(text = labelValue)
            } else {
                Text(text = labelValueFiled)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.light_green),
            focusedLabelColor = colorResource(id = R.color.light_green),
            cursorColor = colorResource(id = R.color.pink_900),
            backgroundColor = colorResource(id = R.color.pink_100)
        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                painter = iconPainterResource, contentDescription = null
            )
        },

        readOnly = readOnly,
        enabled = !readOnly,

        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible.value) {
                stringResource(R.string.hide_password)
            } else {
                stringResource(R.string.show_password)
            }

            IconButton(onClick = {
                if (!readOnly) {
                    passwordVisible.value = !passwordVisible.value
                }
            }
            ) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus,

        )
}

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
fun ButtonComponent(value: String, onClickAction: () -> Unit, isEnabled: Boolean = false) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
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
                fontSize = 18.sp,
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
                    color = colorResource(id = R.color.pink_900),
                    shape = RoundedCornerShape(50.dp)
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

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
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
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
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

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableTextComponent", "{${span.item}}")

                    if (span.item == loginText) {
                        onClickAction.invoke()
                    }
                }

        },
    )
}

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
fun WrongPasswordSubmitterComponent(numberOfTries: Int) {
    val wrongPasswordText = stringResource(id = R.string.wrong_password)
    val remainingTriesText = stringResource(id = R.string.remaining_tries)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Optional: Add padding if needed
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

/**
 * AllergenGrid: A composable function for displaying a grid of allergen items.
 *
 * This composable uses LazyVerticalGrid to create a grid layout with adaptive columns based on the
 * provided allergens. Each allergen is represented by an AllergenItem, and clicking on an item triggers
 * the specified [onAllergenClick] action.
 *
 * @param allergens A list of allergens to be displayed in the grid.
 * @param onAllergenClick A lambda function to be executed when an allergen item is clicked.
 *
 */
@Composable
fun AllergenGrid(
    allergens: List<Allergen>,
    onAllergenClick: (Allergen) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        content = {
            items(count = allergens.size) { i ->
                AllergenItem(
                    allergens[i],
                    onAllergenClick
                )
            }
        }
    )
}

/**
 * AllergenItem: A composable function for displaying an individual allergen item within the grid.
 *
 * This composable creates a Button containing a Box with background color based on the allergen's selected
 * state. The allergen name is displayed in the center, and clicking on the item triggers the specified
 * [onAllergenClick] action.
 *
 * @param allergen An allergen object representing the item to be displayed.
 * @param onAllergenClick A lambda function to be executed when the allergen item is clicked.
 *
 */
@Composable
fun AllergenItem(
    allergen: Allergen,
    onAllergenClick: (Allergen) -> Unit
) {
    Button(
        onClick = { onAllergenClick(allergen) },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (allergen.selectedState.value) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = allergen.name,
                color = if (allergen.selectedState.value) colorResource(id = R.color.pink_50) else colorResource(
                    id = R.color.pink_900
                ),
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

/**
 * DietOptionItem: A composable function for displaying an individual diet option item.
 *
 * This composable creates a Column containing a Box with a background color based on the diet option's selected state.
 * The diet option icon is displayed in the center, and an optional check icon is shown if the option is selected.
 * Clicking on the item triggers the specified [onDietOptionClick] action.
 *
 * @param dietOption A diet option object representing the item to be displayed.
 * @param onDietOptionClick A lambda function to be executed when the diet option item is clicked.
 *
 */
@Composable
fun DietOptionItem(
    dietOption: DietOption,
    onDietOptionClick: (DietOption) -> Unit
) {
    val isSelected = dietOption.selected.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onDietOptionClick(dietOption) })
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = if (isSelected) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    )
                )
        ) {
            Image(
                painter = painterResource(id = dietOption.icon),
                contentDescription = dietOption.type.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = dietOption.type.diet,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * DietOptionList: A composable function for displaying a list of diet option items.
 *
 * This composable uses LazyVerticalGrid to create a grid layout with adaptive columns based on the provided
 * diet options. Each diet option is represented by a DietOptionItem, and clicking on an item triggers the
 * specified [onDietClick] action.
 *
 * @param dietOptions A list of diet options to be displayed in the list.
 * @param onDietClick A lambda function to be executed when a diet option item is clicked.
 *
 */
@Composable
fun DietOptionList(
    dietOptions: List<DietOption>,
    onDietClick: (DietOption) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        content = {
            items(count = dietOptions.size) { i ->
                DietOptionItem(
                    dietOptions[i],
                    onDietClick
                )
            }
        }
    )

}

/**
 * NavigationBarComponent: A composable function for creating a bottom navigation bar.
 *
 * This composable uses the Scaffold and NavigationBar components to create a bottom navigation bar
 * with items specified in the [items] parameter. Each item is represented by a NavigationBarItem, and
 * clicking on an item triggers navigation to the associated screen using the provided [navController].
 *
 * @param navController The NavController used for navigating between screens.
 * @param items A list of BottomNavigationItem objects representing navigation items.
 * @param selectedItemIndex The index of the currently selected navigation item.
 *
 */
@Composable
fun NavigationBarComponent(
    navController: NavController,
    items: List<BottomNavigationItem> = getBottomNavigationItems(),
    selectedItemIndex: Int
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { navController.navigate(bottomNavigationItem.screen.route) },
                        label = {
                            Text(bottomNavigationItem.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            if (selectedItemIndex == index) {
                                Image(
                                    painter = painterResource(id = bottomNavigationItem.selectedIcon),
                                    contentDescription = bottomNavigationItem.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = bottomNavigationItem.unselectedIcon),
                                    contentDescription = bottomNavigationItem.title,
                                    modifier = Modifier.size(24.dp)
                                )

                            }
                        }
                    )
                }
            }
        }
    )
    { it ->
        ScreenContent(innerPadding = it)

    }
}

/**
 * getBottomNavigationItems: A utility function to provide a list of default BottomNavigationItem objects.
 *
 * @return A list of BottomNavigationItem objects.
 */
@Composable
private fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home_icon_selected,
            unselectedIcon = R.drawable.home_icon_not_selected,
            selected = true,
            screen = Screen.Home
        ),
        BottomNavigationItem(
            title = "Pantry",
            selectedIcon = R.drawable.fridge_icon_selected,
            unselectedIcon = R.drawable.fridge_icon_not_selected,
            screen = Screen.Pantry
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_icon_selected,
            unselectedIcon = R.drawable.profile_icon_not_selected,
            screen = Screen.Profile
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = R.drawable.search_selected,
            unselectedIcon = R.drawable.search_not_selected,
            screen = Screen.Search
        )
    )
}

/**
 * ScreenContent: A composable function for displaying the main content of the screen.
 *
 * This composable is meant to be used as the content parameter of the Scaffold in the NavigationBarComponent.
 * It can be replaced with the actual content components of the screen.
 *
 * @param innerPadding The PaddingValues applied to the inner content, useful for applying padding to content components.
 *
 */
@Composable
fun ScreenContent(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // there would be content of the screen with bottom navigation
    }
}
