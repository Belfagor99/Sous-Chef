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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import eu.mobcomputing.dima.registration.components.home.RecipeGrid
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
            smartphoneHomeScreen(navController = navController,homeViewModel)
        } else {
            tabletHomeScreen(navController = navController, homeViewModel = homeViewModel )
        }
    }
}




@Composable
fun smartphoneHomeScreen( navController: NavController,homeViewModel: HomeViewModel){


    // Observe the LiveData containing the list of Ingredients
    val recipesList = homeViewModel.recipes.observeAsState()

    // Observe the LiveData containing the list of Ingredients
    val username = homeViewModel.name.observeAsState()



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50)),

        bottomBar = {NavigationBarComponent(
            navController = navController,
            selectedItemIndex = 0
        )}

    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                /***** HEADER *****/
                Column (
                    modifier = Modifier.padding(25.dp)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            HeaderTextComponent(
                                "Hi ${username.value}",
                                shouldBeCentered = false,
                                shouldBeRed = false
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            NormalTextComponent(
                                "I am your sous chef and I am ready to help you with your cooking today.",
                                shouldBeCentered = false,
                                shouldBeRed = true
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .fillMaxWidth()
                        ) {
                            MyImageComponent(
                                R.drawable.souschef_logo,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    NormalTextComponent(
                        "These are the recipes we can make with the ingredients in your pantry :",
                        shouldBeCentered = false,
                    )

                }

                /***** LIST OF RECIPE *****/
                Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                    if(recipesList.value.isNullOrEmpty()){
                        Box(modifier = Modifier
                            .align(Alignment.Center)
                            .padding(40.dp)
                        ){
                            HeaderTextComponent(value = "Seems there is nothing we can do :( ")
                        }
                    }else{
                        RecipeGrid(recipes = recipesList.value!!, navController = navController)
                    }

                }

            }

        }
    }
}




@Composable
fun tabletHomeScreen( navController: NavController,homeViewModel: HomeViewModel){


    // Observe the LiveData containing the list of Ingredients
    val recipesList = homeViewModel.recipes.observeAsState()

    // Observe the LiveData containing the list of Ingredients
    val username = homeViewModel.name.observeAsState()



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50)),

        bottomBar = {NavigationBarComponent(
            navController = navController,
            selectedItemIndex = 0
        )}

    ) {
        Surface(
            modifier = Modifier.padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
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
                            "Hi ${username.value}",
                            shouldBeCentered = false,
                            shouldBeRed = false
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        NormalTextComponent(
                            "I am your sous chef and I am ready to help you with your cooking today.",
                            shouldBeCentered = false,
                            shouldBeRed = true
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        NormalTextComponent(
                            "These are the recipes we can make with the ingredients in your pantry :",
                            shouldBeCentered = false,
                        )

                        Spacer(modifier = Modifier.height(15.dp))

                        /***** LIST OF RECIPE *****/
                        Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                            if(recipesList.value.isNullOrEmpty()){
                                Box(modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(
                                        top = 40.dp,
                                        bottom= 0.dp,
                                        start = 40.dp,
                                        end=40.dp,)
                                ){
                                    HeaderTextComponent(value = "Seems there is nothing we can do :( ")
                                }
                            }else{
                                RecipeGrid(recipes = recipesList.value!!, navController = navController)
                            }

                        }
                    }
                }
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