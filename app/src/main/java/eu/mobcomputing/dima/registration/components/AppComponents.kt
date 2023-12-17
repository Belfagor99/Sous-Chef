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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import eu.mobcomputing.dima.registration.data.Allergen
import eu.mobcomputing.dima.registration.data.DietOption


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

@Composable
fun MyTextFieldComponent(
    labelValue: String,
    iconPainterResource: Painter,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false,
    shouldPreFill: Boolean,
    preFilledText: String = ""
) {

    var textValue = remember {
        mutableStateOf("")
    }
    if (shouldPreFill) {
        textValue = remember {
            mutableStateOf(preFilledText)
        }

        // Handle changes to prefilledText
        if (textValue.value != preFilledText) {
            textValue.value = preFilledText
        }
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

@Composable
fun MyImageComponent(imageResource: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(imageResource),
        contentDescription = imageResource.toString(),
        modifier = modifier,
        alignment = Alignment.Center
    )
}

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

@Composable
fun StepperItem(text: String, isCompleted: Boolean, isCurrent: Boolean) {
    val color = when {
        isCompleted -> colorResource(id = R.color.light_green)
        isCurrent -> colorResource(id = R.color.pink_600)
        else -> colorResource(id = R.color.pink_200) // Use your desired color for incomplete steps
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = when {
                isCompleted -> Icons.Default.Check
                else -> Icons.Default.Circle
            },
            contentDescription = null, // Decorative content
            tint = color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = color)
    }
}


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


@Composable
fun DietOptionItem(
    dietOption: DietOption,
    onDietOptionClick: (DietOption) -> Unit
) {
    val isSelected =  dietOption.selected.value
    //var isSelected by remember { mutableStateOf(dietOption.selected.value) }

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
                    /*color = if (dietOption.selected.value) colorResource(id = R.color.pink_900) else colorResource(
                        id = R.color.pink_200
                    )*/
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
            //if (dietOption.selected.value) {
             if (isSelected){
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
