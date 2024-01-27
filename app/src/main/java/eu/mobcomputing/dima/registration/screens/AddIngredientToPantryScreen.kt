package eu.mobcomputing.dima.registration.screens

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.add_ingredients.DatePickerView
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.navigation.Screen
import eu.mobcomputing.dima.registration.viewmodels.AddIngredientToPantryViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import eu.mobcomputing.dima.registration.utils.Constants


/**
 * Composable function for adding an ingredient to the fridge with options for quantity and expiration date.
 *
 * @param navController The NavController for navigation within the application.
 * @param ingredient The ingredient to be added to the fridge.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AddIngredientToPantry(
    navController: NavController,
    ingredient: Ingredient,
    addIngredientToPantryViewModel: AddIngredientToPantryViewModel = hiltViewModel()

) {

    val isNetworkAvailable = addIngredientToPantryViewModel.connectionStatus.observeAsState()



    /*
     * State variables for the date picker
     */
    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }


    /*
    *   Get local context for Toast message
    * */
    val context = LocalContext.current


    /*
     * UI composition
     */
    Surface(
        color = colorResource(id = R.color.pink_50),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(colorResource(id = R.color.pink_50))
        ){
            /*
             * Back button row
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Navigate back button
                IconButton(
                    onClick = {
                        navController.navigate(Screen.SearchIngredientScreen.route) {
                        launchSingleTop = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                }
            }

            /*
             * Main content column
             */
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly

            ){

                ElevatedCard (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(10.dp),

                    shape = CardDefaults.shape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                    ),
                ) {
                    Row{

                        Box(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)){

                            GlideImage(
                                model = Constants.BASE_IMG_URL_500+ingredient.image ,
                                contentDescription = "recipe image",
                                modifier = Modifier
                                    .wrapContentSize().align(Alignment.Center),
                                //contentScale = ContentScale.FillBounds,
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {

                                Text(
                                    text = ingredient.name,
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
                }

                /*
                 * Expiration date row
                 */
                Row (
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Add the expiring date: ",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )


                    Button(
                        onClick = { showDatePicker = true },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.pink_900)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = date,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.pink_100),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        )
                    }


                    if (showDatePicker) {
                        DatePickerView(
                            onDismiss = { showDatePicker = false },
                            onDateSelected = {
                                date = it
                                ingredient.expiringDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    .parse(it)
                            },
                        )
                    }
                }
            }


            /*
             * Button to add to pantry
             */
            ButtonComponent(

                value = "Add to pantry",
                onClickAction = {
                    // be sure to put a quantity at least  = 1
                    /*if (ingredient.userQuantity==0.0){
                        ingredient.userQuantity=1.0
                    }
                    if (ingredient.unit==""){
                        ingredient.unit=ingredient.possibleUnits[0]
                    }*/
                    Log.e("ADDING", ingredient.toString())
                    val exit = addIngredientToPantryViewModel.addIngredientToPantry(ingredient)

                    if (exit){
                        //notify user
                        Toast.makeText(context,"Ingredient added to your pantry!",Toast.LENGTH_SHORT).show()
                    }else{
                        //notify user
                        Toast.makeText(context,"Ingredient already in your pantry!",Toast.LENGTH_SHORT).show()
                    }
                    //go back to home
                    navController.navigate(Screen.SearchIngredientScreen.route)
                },
                isEnabled = true)

        }

        if(isNetworkAvailable.value==false){
            val myContext = LocalContext.current
            val builder = AlertDialog.Builder(myContext)
            builder.setTitle("Connection Lost")
            builder.setMessage("We lost connection to the server. Please make sure your connection works and restart the app")

            builder.setPositiveButton("Ok") { _, _ ->
                (myContext as Activity).finishAffinity()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}


@Preview
@Composable
fun AddIngredientToPantryPreview() {
    val ingredient = Ingredient(
        id= 1,
        name = "Egg",
        image = "",
        )
    AddIngredientToPantry(navController = rememberNavController(),ingredient= ingredient)
}