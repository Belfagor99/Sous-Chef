package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Devices.PIXEL_TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.delay

// Constant defining length of visibility of the splash screen
private const val SPLASH_TIMEOUT = 1000L

/**
 * Composable function representing the Splash screen of the application.
 *
 * @param navController NavController for navigating between screens.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    splashScreenViewModel: SplashScreenViewModel = viewModel()
) {
    // LaunchedEffect to perform a side effect when this composable is first displayed
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        splashScreenViewModel.redirection(navController)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {
                MyImageComponent(
                    R.drawable.sous_chef,
                    modifier = Modifier.fillMaxSize()
                )
            }
            CircularProgressIndicator()
        }
    }
}

/**
 * Preview annotation for previewing the Splash Screen in Android Studio.
 */
@Preview(device = PIXEL_TABLET)
@Preview(device = Devices.PIXEL_6)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(
        navController = rememberNavController(),
        splashScreenViewModel = SplashScreenViewModel()
    )
}

