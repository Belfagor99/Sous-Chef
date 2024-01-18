package eu.mobcomputing.dima.registration.screens

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
import eu.mobcomputing.dima.registration.components.FirebaseMessagingNotificationPermissionDialog
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SearchBar
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
        } else {
            Log.d("Permission", "Requesting notification permission")
            showNotificationDialog.value = true
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
        contentAlignment = Alignment.Center
    ) {
        val isSmallScreen = maxWidth < 600.dp

        if (isSmallScreen) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        HeaderTextComponent(
                            "Hi "+ homeViewModel.username.value,
                            shouldBeCentered = false,
                            shouldBeRed = false
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        NormalTextComponent(
                            "What would you like to cook today?",
                            shouldBeCentered = false,
                            shouldBeRed = true
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .aspectRatio(1f)
                            .fillMaxWidth()
                    ) {
                        MyImageComponent(
                            R.drawable.souschef_logo,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier = Modifier.padding(25.dp)) {
                    NormalTextComponent(
                        "I am your sous chef and I am ready to help you with your cooking today.",
                        shouldBeCentered = false,
                    )
                }
                Box(modifier = Modifier.padding(25.dp)) {
                    NormalTextComponent(
                        value = "Are you searching an ingredient?",
                        shouldBeCentered = false,
                        shouldBeRed = true
                    )
                }
                Box(modifier = Modifier.padding(5.dp)) {
                    SearchBar(onSearchTextChange = {}, onSearch = {})
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier = Modifier.padding(25.dp)) {
                    NormalTextComponent(
                        value = "Have a look in your pantry, there are some ingredients that must be used.",
                        shouldBeCentered = false
                    )
                }

                Spacer(Modifier.weight(1f))
                NavigationBarComponent(
                    navController = navController,
                    selectedItemIndex = 0
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth()
                    ) {
                        MyImageComponent(
                            R.drawable.souschef_logo, modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {

                        HeaderTextComponent(
                            "Hi "+ homeViewModel.username.value,
                            shouldBeCentered = false,
                            shouldBeRed = false
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        NormalTextComponent(
                            "What would you like to cook today?",
                            shouldBeCentered = false,
                            shouldBeRed = true
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        NormalTextComponent(
                            "I am your sous chef and I am ready to help you with your cooking today.",
                            shouldBeCentered = false
                        )
                        NormalTextComponent(
                            value = "Are you searching an ingredient?",
                            shouldBeCentered = false,
                            shouldBeRed = true
                        )
                        SearchBar(onSearchTextChange = {}, onSearch = {})

                        Spacer(modifier = Modifier.height(20.dp))
                        NormalTextComponent(
                            value = "Have a look in your pantry, there are some ingredients that must be used.",
                            shouldBeCentered = false
                        )
                    }
                }
                NavigationBarComponent(
                    navController = navController,
                    selectedItemIndex = 0
                )
            }
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