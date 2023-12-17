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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.AllergenGrid
import eu.mobcomputing.dima.registration.components.MyRedHeadingComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.SmallButtonComponent
import eu.mobcomputing.dima.registration.components.StepperBar
import eu.mobcomputing.dima.registration.data.registration.SharedUserDataModel

@Composable
fun UserAllergiesScreen(
    navController: NavController,
    sharedUserDataModel: SharedUserDataModel) {

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
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                MyRedHeadingComponent(value = stringResource(id = R.string.create_account_text), shouldBeCentered = true)
                Spacer(modifier = Modifier.height(10.dp))
                StepperBar(
                    steps = sharedUserDataModel.steps,
                    currentStep = sharedUserDataModel.allergiesStep.value
                )
                NormalTextComponent(value = stringResource(id = R.string.allergies))
                Column {
                    AllergenGrid(
                        allergens = sharedUserDataModel.allergens,
                        onAllergenClick = {
                            sharedUserDataModel.allergenOnClick(it)
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
                            sharedUserDataModel.allergiesScreenNext(navController)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }


            }
        }

    }
}

@Preview
@Composable
fun PreviewUserInformationScreen() {
    UserAllergiesScreen(navController = rememberNavController(), sharedUserDataModel = SharedUserDataModel())
}