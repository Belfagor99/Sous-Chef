package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.DietOptionList
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SmallButtonComponent
import eu.mobcomputing.dima.registration.components.StepperBar
import eu.mobcomputing.dima.registration.viewmodels.SharedUserDataViewModel

/**
 * Composable function representing the UserDiet screen of the application.
 *
 * @param navController NavController for navigating between screens.
 * @param sharedUserDataViewModel ViewModel managing the logic for the UserDiet screen.
 */
@Composable
fun UserDietScreen(
    navController: NavController,
    sharedUserDataViewModel: SharedUserDataViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(id = R.color.pink_50)
            ),
        contentAlignment = Alignment.Center
    )
    {
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
                Spacer(modifier = Modifier.height(5.dp))
                MyRedHeadingComponent(value = stringResource(id = R.string.create_account_text), shouldBeCentered = true)
                Spacer(modifier = Modifier.height(4.dp))
                StepperBar(
                    steps = sharedUserDataViewModel.steps,
                    currentStep = sharedUserDataViewModel.dietTypeStep.value
                )
                NormalTextComponent(value = stringResource(id = R.string.diet_type))

                DietOptionList(
                    dietOptions = sharedUserDataViewModel.dietOptions,
                    onDietClick = {
                        sharedUserDataViewModel.dietOptionOnClick(it)
                    },

                    )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    SmallButtonComponent(
                        value = stringResource(id = R.string.previous_step),
                        onClickAction = {
                            sharedUserDataViewModel.backStepOnClick(navController)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f) // Adjust the weight and fillMaxWidth accordingly

                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    SmallButtonComponent(
                        value = stringResource(id = R.string.finish),
                        onClickAction = {
                            sharedUserDataViewModel.finishRegistration(navController)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.5f) // Adjust the weight and fillMaxWidth accordingly
                    )

                }
            }
        }
    }

}

/**
 * Preview annotation for previewing the UserDietScreen in Android Studio.
 */
@Preview
@Composable
fun PreviewUserDietScreen() {
    UserDietScreen(navController = rememberNavController(), sharedUserDataViewModel = SharedUserDataViewModel())
}