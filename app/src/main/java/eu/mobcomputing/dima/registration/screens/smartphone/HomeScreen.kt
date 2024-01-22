package eu.mobcomputing.dima.registration.screens.smartphone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.components.home.RecipeGrid
import eu.mobcomputing.dima.registration.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
){

    // Observe the LiveData containing the list of Ingredients
    val recipesList = homeViewModel.recipes.observeAsState()

    // Observe the LiveData containing the list of Ingredients
    val username = homeViewModel.name.observeAsState()



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

        /***** NAV BAR *****/
        NavigationBarComponent(
            navController = navController,
            selectedItemIndex = 0
        )
    }


}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){

    HomeScreen(
        navController = rememberNavController(),
        homeViewModel = HomeViewModel()
    )
}