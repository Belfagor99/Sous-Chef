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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.SousChefImageComponent

@Composable
fun WelcomeScreen() {
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
            .padding(28.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            SousChefImageComponent()
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = stringResource(id = R.string.create_account_text))
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent(value = stringResource(id = R.string.log_in_text))

        }
    }
}

@Preview
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen()
}

