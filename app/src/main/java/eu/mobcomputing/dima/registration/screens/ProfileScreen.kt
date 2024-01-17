package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.user.UserCharacteristicsDisplay
import eu.mobcomputing.dima.registration.viewmodels.ProfileViewModel

/**
 * Composable function representing the Profile screen of the application.
 *
 * @param navController NavController for navigating between screens.
 */
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    ){



    // Observe the LiveData containing the list of Ingredients
    val allergies = viewModel.userAllergies.observeAsState()
    val diet = viewModel.userDiet.observeAsState()
    val name = viewModel.name.observeAsState()
    val surname = viewModel.surname.observeAsState()
    val email = viewModel.email.observeAsState()



    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.pink_50))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            //verticalArrangement = Arrangement.SpaceBetween
        ) {


            /***** USR IMAGE NAME AND EMAIL *****/
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(
                        top = 40.dp,
                        bottom = 20.dp
                    ),
            ) {
                Box(modifier = Modifier.weight(1f)){
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "user img",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Box(modifier = Modifier.weight(1f)){
                    Column (
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp),

                    ){

                        Text(
                            text = "${name.value} ${surname.value}",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .wrapContentSize()
                                .align(Alignment.Start),


                        )
                        Text(
                            text = "${email.value}",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .wrapContentSize()
                                .align(Alignment.Start),
                        )
                    }
                }
            }


            Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                Column {

                    /***** USR DIET *****/
                    UserCharacteristicsDisplay(
                        headerString = "Diets",
                        userCharacteristics = diet.value,
                        clickOnEdit = {}
                    )

                    /***** USR ALLERGIES *****/

                    UserCharacteristicsDisplay(
                        headerString = "Allergies",
                        userCharacteristics = allergies.value,
                        clickOnEdit = {}
                    )
                }

            }



            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 2
            )
        }

    }
}


/**
 * Preview annotation for previewing the ProfileScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewProfileScreen(){
    ProfileScreen(navController = rememberNavController(), ProfileViewModel())
}