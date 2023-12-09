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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.res.painterResource


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
    labelValue: String, iconPainterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean = false
) {

    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(modifier = Modifier
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
        isError = !errorStatus
    )
}


@Composable
fun MyPasswordFieldComponent(
    labelValue: String, iconPainterResource: Painter, onTextSelected: (String) -> Unit, errorStatus: Boolean = false
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
        label = { Text(text = labelValue) },
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

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun ButtonComponent(value: String, onClickAction: () -> Unit, isEnabled: Boolean = false ) {
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
                text = value, fontSize = 18.sp, color = colorResource(id = R.color.pink_100), fontWeight = FontWeight.Bold
            )

        }

    }
}

@Composable
fun MyRedHeadingComponent(value: String) {
    Text(
        text = value, modifier = Modifier
            .fillMaxWidth()
            .heightIn(), style = TextStyle(
            fontSize = 30.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.pink_900), textAlign = TextAlign.Left
    )

}

@Composable
fun ClickableLoginTextComponent() {
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
                        // To Do
                    }
                }

        },
    )
}


@Composable
fun ClickableForgottenPasswordTextComponent() {
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
                        // To Do
                    }
                }

        },
    )
}

@Composable
fun SousChefImageComponent(){
    Image(
        painter = painterResource(id = R.drawable.sous_chef),
        contentDescription = "Sous Chef Image",
        modifier = Modifier.width(280.dp).height(280.dp),
        alignment = Alignment.Center
        )
}



