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
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.DietOptionList
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SmallButtonComponent
import eu.mobcomputing.dima.registration.components.StepperBar
import eu.mobcomputing.dima.registration.data.registration.SharedUserDataModel

@Composable
fun UserDietScreen(
    navController: NavController,
) {
    val sharedUserDataModel: SharedUserDataModel = viewModel()
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
                HeaderTextComponent(value = stringResource(id = R.string.create_account_text))
                Spacer(modifier = Modifier.height(4.dp))
                StepperBar(
                    steps = sharedUserDataModel.steps,
                    currentStep = sharedUserDataModel.dietTypeStep.value
                )
                NormalTextComponent(value = stringResource(id = R.string.diet_type))

                DietOptionList(
                    dietOptions = sharedUserDataModel.dietOptions,
                    onDietClick = {
                        sharedUserDataModel.dietOptionOnClick(it)
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
                            sharedUserDataModel.backStepOnClick(navController)
                        },
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth(0.5f) // Adjust the weight and fillMaxWidth accordingly

                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    SmallButtonComponent(
                        value = stringResource(id = R.string.finish),
                        onClickAction = {
                            sharedUserDataModel.finishRegistration(navController)
                        },
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth(0.5f) // Adjust the weight and fillMaxWidth accordingly
                    )

                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewUserDietScreen() {
    UserDietScreen(navController = rememberNavController())
}