package eu.mobcomputing.dima.registration.screens

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.FirebaseMessagingNotificationPermissionDialog
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.utils.Constants
import eu.mobcomputing.dima.registration.viewmodels.HomeViewModel

/**
 * Composable function representing the Home screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param homeViewModel ViewModel managing the logic for the Home screen.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val notificationPermissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )
    val showNotificationDialog = remember { mutableStateOf(false) }
    if (showNotificationDialog.value) FirebaseMessagingNotificationPermissionDialog(
        showNotificationDialog = showNotificationDialog,
        notificationPermissionState = notificationPermissionState
    )

    // Check the notification permission status and launch corresponding dialog if necessary
    LaunchedEffect(key1 = Unit) {
        Log.d("Permission", "Notification permission status: ${notificationPermissionState.status}")
        Log.d("Permission", "Is granted: ${notificationPermissionState.status.isGranted}")

        if (notificationPermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        ) {
            Firebase.messaging.subscribeToTopic(Constants.TOPIC)
        } else{
            Log.d("Permission", "Requesting notification permission")
            showNotificationDialog.value = true
        }
    }
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderTextComponent(value = stringResource(id = R.string.information_text_logged_in))
            ButtonComponent(
                value = stringResource(id = R.string.log_out),
                onClickAction = { homeViewModel.logOut(navController) },
                isEnabled = true
            )

            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 0
            )
        }

    }
}


/**
 * Preview annotation for previewing the HomeScreen in Android Studio.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}