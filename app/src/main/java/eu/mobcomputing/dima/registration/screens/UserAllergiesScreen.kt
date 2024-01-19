package eu.mobcomputing.dima.registration.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.AllergenGrid
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SmallButtonComponent
import eu.mobcomputing.dima.registration.components.StepperBar
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel

/**
 * Composable function representing the UserAllergies screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param sharedUserDataViewModel ViewModel managing the logic for the UserAllergies screen.
 */
@Composable
fun UserAllergiesScreen(
    navController: NavController,
    sharedUserDataViewModel: SharedUserDataViewModel
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
        contentAlignment = Alignment.Center
    )
    {
        val isSmallScreen = maxWidth < 600.dp
        if (isSmallScreen) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                HeaderTextComponent(
                    value = stringResource(id = R.string.create_account_text),
                    shouldBeCentered = true,
                    shouldBeRed = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                StepperBar(
                    steps = sharedUserDataViewModel.steps,
                    currentStep = sharedUserDataViewModel.allergiesStep.value
                )
                NormalTextComponent(value = stringResource(id = R.string.allergies))
                Column {
                    AllergenGrid(
                        allergens = sharedUserDataViewModel.allergens,
                        onAllergenClick = {
                            sharedUserDataViewModel.allergenOnClick(it)
                            it.selectedState.value = !it.selectedState.value
                            it.selectedState.value = !it.selectedState.value
                        }

                    )
                }


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    SmallButtonComponent(
                        value = stringResource(id = R.string.next_step),
                        onClickAction = {
                            sharedUserDataViewModel.allergiesScreenNext(navController)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }


            }
        }
        // If the screen is wider, if it is a tablet
        else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
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
                        R.drawable.sous_chef, modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeaderTextComponent(
                        value = stringResource(id = R.string.create_account_text),
                        shouldBeCentered = true,
                        shouldBeRed = true
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    StepperBar(
                        steps = sharedUserDataViewModel.steps,
                        currentStep = sharedUserDataViewModel.allergiesStep.value
                    )
                    NormalTextComponent(value = stringResource(id = R.string.allergies))
                    Column {
                        AllergenGrid(
                            allergens = sharedUserDataViewModel.allergens,
                            onAllergenClick = {
                                sharedUserDataViewModel.allergenOnClick(it)
                                it.selectedState.value = !it.selectedState.value
                                it.selectedState.value = !it.selectedState.value
                            }

                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        SmallButtonComponent(
                            value = stringResource(id = R.string.next_step),
                            onClickAction = {
                                sharedUserDataViewModel.allergiesScreenNext(navController)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }


                }
            }
        }
    }
}


/**
 * Preview annotation for previewing the UserInformationScreen in Android Studio.
 */
@Preview(device = Devices.PIXEL_TABLET)
@Preview(device = Devices.PIXEL_6)
@Composable
fun PreviewUserInformationScreen() {
    UserAllergiesScreen(
        navController = rememberNavController(),
        sharedUserDataViewModel = SharedUserDataViewModel()
    )
}