package eu.mobcomputing.dima.registration.screens

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.user.CharacteristicsEditorDialog
import eu.mobcomputing.dima.registration.components.user.UserCharacteristicsDisplay
import eu.mobcomputing.dima.registration.models.DietType
import eu.mobcomputing.dima.registration.navigation.Screen
import eu.mobcomputing.dima.registration.utils.Constants
import eu.mobcomputing.dima.registration.viewmodels.ProfileViewModel

/**
 * Composable function for displaying the user profile screen.
 *
 * @param navController Navigation controller for handling navigation within the app.
 * @param viewModel View model for managing the profile data.
 */

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {


    val isNetworkAvailable = viewModel.connectionStatus.observeAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
    ) {
        val isSmallScreen = maxWidth < 600.dp


        if (isSmallScreen) {
            SmartphoneProfileScreen(navController = navController, viewModel = viewModel)

        }
        // If the screen is wider, if it is a tablet
        else {
            TabletProfileScreen(navController = navController, viewModel = viewModel)
        }
    }


    if (isNetworkAvailable.value == false) {
        val context = LocalContext.current
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Connection Lost")
        builder.setMessage("We lost connection to the server. Please make sure your connection works and restart the app")

        builder.setPositiveButton("Ok") { _, _ ->
            (context as Activity).finishAffinity()
        }

        val dialog = builder.create()
        dialog.show()
    }
}


@Composable
fun SmartphoneProfileScreen(navController: NavController, viewModel: ProfileViewModel) {

    // Observe the LiveData from the view model
    val allergies = viewModel.userAllergies.observeAsState()
    val diet = viewModel.userDiet.observeAsState()
    val name = viewModel.name.observeAsState()
    val surname = viewModel.surname.observeAsState()
    val email = viewModel.email.observeAsState()

    /*
    * Variable for managing the dialog to show:
    * = 0 -> do not show dialog
    * = 1 -> show dialog for edit diet
    * = 2 -> show dialog for edit allergies
    */
    var showEditCharacteristics by remember {
        mutableStateOf(0)
    }

    /*
    *   Get local context for Toast message
    * */
    val context = LocalContext.current





    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBarComponent(
                navController = navController, selectedItemIndex = 2
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(colorResource(id = R.color.pink_50)),
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f)
                    .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                HeaderTextComponent(
                    value = stringResource(R.string.here_is_all_the_information_i_know_about_you),
                    shouldBeCentered = false,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 20.dp
                        )
                        .border(
                            5.dp, colorResource(id = R.color.pink_900)
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = "user img",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .weight(1f)
                                    .fillMaxSize()
                            )

                            /*  LOGOUT BUTTON */
                            OutlinedButton(
                                onClick = {
                                    viewModel.showLogoutConfirmationDialog(
                                        context = context, navController = navController
                                    )
                                },
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "Logout",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                    ),
                                    modifier = Modifier
                                        .wrapContentSize(),
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Text(
                                text = stringResource(R.string.your_name),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                    color = Color.Red
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .wrapContentSize(),
                            )
                            Text(
                                text = "${name.value} ${surname.value}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize(),
                            )

                            Text(
                                text = stringResource(R.string.your_e_mail),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                    color = Color.Red
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .wrapContentSize(),
                            )
                            Text(
                                text = "${email.value}",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize(),
                            )
                        }
                    }
                }
            }
            /***** USR DIET AND ALLERGIES *****/
            Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                Column {
                    /***** USR DIET *****/
                    UserCharacteristicsDisplay(headerString = "Diet",
                        userCharacteristics = diet.value,
                        clickOnEdit = { showEditCharacteristics = 1 })

                    /***** USR ALLERGIES *****/

                    UserCharacteristicsDisplay(headerString = "Allergies",
                        userCharacteristics = allergies.value,
                        clickOnEdit = { showEditCharacteristics = 2 })


                    /** Dialog for editing user's diet*/
                    if (showEditCharacteristics == 1) {
                        CharacteristicsEditorDialog(onDismissRequest = {
                            showEditCharacteristics = 0
                        },
                            onConfirmation = {
                                viewModel.setNewDiet(it[0])
                                showEditCharacteristics = 0
                                reloadScreen(navController)
                                Toast.makeText(
                                    context,
                                    "Allergies have been modified! ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            dialogTitle = "Select your diet: ",
                            userCharacteristics = DietType::class.java.enumConstants?.map { it.diet }
                                ?: emptyList(),
                            singleSelection = true)
                    }
                    /** Dialog for editing user's allergies*/
                    else if (showEditCharacteristics == 2) {
                        CharacteristicsEditorDialog(
                            onDismissRequest = {
                                showEditCharacteristics = 0
                            },
                            onConfirmation = {
                                viewModel.setNewAllergies(it)
                                showEditCharacteristics = 0
                                reloadScreen(navController)
                                Toast.makeText(
                                    context,
                                    "Allergies have been modified! ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            dialogTitle = "Select your allergies: ",
                            userCharacteristics = Constants.allergies,
                            singleSelection = false
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TabletProfileScreen(navController: NavController, viewModel: ProfileViewModel) {


    // Observe the LiveData from the view model
    val allergies = viewModel.userAllergies.observeAsState()
    val diet = viewModel.userDiet.observeAsState()
    val name = viewModel.name.observeAsState()
    val surname = viewModel.surname.observeAsState()
    val email = viewModel.email.observeAsState()

    /*
    * Variable for managing the dialog to show:
    * = 0 -> do not show dialog
    * = 1 -> show dialog for edit diet
    * = 2 -> show dialog for edit allergies
    */
    var showEditCharacteristics by remember {
        mutableStateOf(0)
    }

    /*
    *   Get local context for Toast message
    * */
    val context = LocalContext.current




    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        //.verticalScroll(state = rememberScrollState()),
        bottomBar = {
            NavigationBarComponent(
                navController = navController, selectedItemIndex = 2
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(colorResource(id = R.color.pink_50))
            //.verticalScroll(state = rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                HeaderTextComponent(
                    value = stringResource(R.string.here_is_all_the_information_i_know_about_you),
                    shouldBeCentered = false,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 20.dp
                        )
                        .border(
                            5.dp, colorResource(id = R.color.pink_900)
                        ),
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = "user img",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .weight(1f)
                                    .fillMaxSize()
                            )

                            /*  LOGOUT BUTTON */
                            OutlinedButton(
                                onClick = {
                                    viewModel.showLogoutConfirmationDialog(
                                        context = context, navController = navController
                                    )
                                },
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "Logout",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontStyle = FontStyle.Normal,
                                    ),
                                    modifier = Modifier
                                        .wrapContentSize(),
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {

                            Text(
                                text = stringResource(R.string.your_name),
                                style = TextStyle(
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                    color = Color.Red
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .wrapContentSize(),
                            )
                            Text(
                                text = "${name.value} ${surname.value}",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize(),
                            )

                            Text(
                                text = stringResource(R.string.your_e_mail),
                                style = TextStyle(
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                    color = Color.Red
                                ),
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .wrapContentSize(),
                            )
                            Text(
                                text = "${email.value}",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Normal,
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize(),
                            )
                        }
                    }
                }
            }
            /***** USR DIET AND ALLERGIES *****/
            Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                Column {
                    /***** USR DIET *****/
                    UserCharacteristicsDisplay(headerString = "Diet",
                        userCharacteristics = diet.value,
                        clickOnEdit = { showEditCharacteristics = 1 })

                    /***** USR ALLERGIES *****/

                    UserCharacteristicsDisplay(headerString = "Allergies",
                        userCharacteristics = allergies.value,
                        clickOnEdit = { showEditCharacteristics = 2 })


                    /** Dialog for editing user's diet*/
                    if (showEditCharacteristics == 1) {
                        CharacteristicsEditorDialog(onDismissRequest = {
                            showEditCharacteristics = 0
                        },
                            onConfirmation = {
                                viewModel.setNewDiet(it[0])
                                showEditCharacteristics = 0
                                reloadScreen(navController)
                                Toast.makeText(
                                    context,
                                    "Allergies have been modified! ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            dialogTitle = "Select your diet: ",
                            userCharacteristics = DietType::class.java.enumConstants?.map { it.diet }
                                ?: emptyList(),
                            singleSelection = true)
                    }
                    /** Dialog for editing user's allergies*/
                    else if (showEditCharacteristics == 2) {
                        CharacteristicsEditorDialog(
                            onDismissRequest = {
                                showEditCharacteristics = 0
                            },
                            onConfirmation = {
                                viewModel.setNewAllergies(it)
                                showEditCharacteristics = 0
                                reloadScreen(navController)
                                Toast.makeText(
                                    context,
                                    "Allergies have been modified! ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            dialogTitle = "Select your allergies: ",
                            userCharacteristics = Constants.allergies,
                            singleSelection = false
                        )
                    }
                }
            }
        }
    }

}


/**
 * This navigate to home screen so that the user needs to click again on the profile screen
 * so that he can see the changes bcz for now the screen doesn't seems to update the diet
 * and allergies without a full reload of the page
 * (Maybe there is a better way to achieve this!! )
 *
 * - mutableStateOf on a observered variable doesn't work..
 */
fun reloadScreen(navController: NavController) {

    navController.navigate(Screen.Home.route)

}

/**
 * Preview annotation for previewing the ProfileScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController(), ProfileViewModel(Application()))
}