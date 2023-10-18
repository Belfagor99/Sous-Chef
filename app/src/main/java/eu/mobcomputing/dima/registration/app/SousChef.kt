package eu.mobcomputing.dima.registration.app

import androidx.compose.material.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import eu.mobcomputing.dima.registration.screens.SignUpScreen

@Composable
fun PostOfficeApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
        )
    {
        SignUpScreen()
    }

}