package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyPasswordFieldComponent
import eu.mobcomputing.dima.registration.components.MyTextFieldComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent

@Composable
fun SignUpScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    )
    {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {
            NormalTextComponent(value = stringResource(id = R.string.no_account_text))
            HeaderTextComponent(value = stringResource(id = R.string.create_account_text))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.first_name),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_person_24
                )
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.last_name),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_person_24
                )
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_alternate_email_24
                )
            )

            MyPasswordFieldComponent(
                labelValue = stringResource(id = R.string.password),
                iconPainterResource = painterResource(
                    id = R.drawable.baseline_lock_24
                )            )

            Spacer(modifier = Modifier.height(80.dp))
            ButtonComponent(value = stringResource(id = R.string.create_account_text))
            Spacer(modifier = Modifier.height(30.dp))
            NormalTextComponent(value = stringResource(id = R.string.or))
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    SignUpScreen()
}