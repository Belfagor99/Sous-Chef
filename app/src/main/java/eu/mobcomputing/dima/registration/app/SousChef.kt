package eu.mobcomputing.dima.registration.app

import LogInScreen
import androidx.compose.material.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SousChefApp(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
        )
    {
        //SignUpScreen()
        LogInScreen()
    }

}