package eu.mobcomputing.dima.registration.screens

import android.app.Activity
import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SignalCellularConnectedNoInternet0Bar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.ButtonComponent
import eu.mobcomputing.dima.registration.components.MyAlertDialog
import eu.mobcomputing.dima.registration.components.add_ingredients.DatePickerView
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.navigation.Screen
import eu.mobcomputing.dima.registration.utils.Constants
import eu.mobcomputing.dima.registration.viewmodels.AddIngredientToPantryViewModel
import java.text.SimpleDateFormat
import java.util.Locale


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

    val datePicketText = "Open date picker dialog"

    // State variables for the date picker
    var date by remember {
        mutableStateOf(datePicketText)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    // Get local context for Toast message
    val context = LocalContext.current


    // UI composition
    Surface(
        color = colorResource(id = R.color.pink_50), modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(colorResource(id = R.color.pink_50))
        ) {

            // Back button row
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
            ) {
                // Navigate back button
                IconButton(onClick = {
                    navController.navigate(Screen.SearchIngredientScreen.route) {
                        launchSingleTop = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back)
                    )
                }
            }

            // Main content column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly

            ) {

                ElevatedCard(
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
                    Row {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {

                            GlideImage(
                                model = Constants.BASE_IMG_URL_500 + ingredient.image,
                                contentDescription = stringResource(R.string.recipe_image_desc),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center),
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            Column(
                                modifier = Modifier.align(Alignment.Center)
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

                // Expiration date row
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.add_the_expiring_date), style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal,
                        ), modifier = Modifier
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
                                ingredient.expiringDate =
                                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(it)
                            },
                        )
                    }
                }
            }

            // Button to add to pantry
            ButtonComponent(
                value = stringResource(R.string.add_to_pantry), onClickAction = {
                    if ((!addIngredientToPantryViewModel.dialogOpened.value) and (ingredient.expiringDate == null)) {
                        addIngredientToPantryViewModel.openAlertDialogNoDate.value = true
                    } else {
                        addIngredientToUser(
                            ingredient, addIngredientToPantryViewModel, context, navController
                        )

                    }

                }, isEnabled = true
            )

        }

        if (addIngredientToPantryViewModel.openAlertDialogNoDate.value) {
            MyAlertDialog(
                onDismissRequest = {
                    addIngredientToPantryViewModel.dialogOpened.value = true
                    addIngredientToPantryViewModel.openAlertDialogNoDate.value = false
                },
                onConfirmation = {
                    addIngredientToUser(
                        ingredient = ingredient,
                        addIngredientToPantryViewModel = addIngredientToPantryViewModel,
                        context = context,
                        navController = navController
                    )
                },
                dialogTitle = stringResource(id = R.string.ingredient_without_due_date_to_be_added),
                dialogText = stringResource(id = R.string.add_ingredient_message),
                icon = Icons.Default.QuestionMark,
                confirmationText = stringResource(id = R.string.add_ingredient_yes),
                dismissText = stringResource(id = R.string.add_ingredient_no)
            )

        }

        if (isNetworkAvailable.value == false) {
            MyAlertDialog(
                onDismissRequest = {
                    addIngredientToPantryViewModel.openAlertDialogNoDate.value = false
                },
                onConfirmation = { (context as Activity).finishAffinity() },
                dialogTitle = stringResource(id = R.string.connection_not_available),
                dialogText = stringResource(id = R.string.i_need_conection_txt),
                icon = Icons.Default.SignalCellularConnectedNoInternet0Bar,
                confirmationText = stringResource(id = R.string.ok),
                dismissText = ""
            )
        }
    }
}

/** Function to add trigger the view model to add ingredient to user
 *
 * @param ingredient ingredient to be added
 * @param addIngredientToPantryViewModel view model
 * @param context context to show toast messages
 * @param navController to navigate to a different class
 */

fun addIngredientToUser(
    ingredient: Ingredient,
    addIngredientToPantryViewModel: AddIngredientToPantryViewModel,
    context: Context,
    navController: NavController
) {
    Log.e("ADDING", ingredient.toString())

    when (addIngredientToPantryViewModel.addIngredientToPantry(ingredient)) {
        0 -> {
            Toast.makeText(
                context,
                context.getString(R.string.i_ve_stored_the_ingredient_to_our_pantry),
                Toast.LENGTH_LONG
            ).show()
            navController.navigate(Screen.SearchIngredientScreen.route)
        }

        -1 -> {
            Toast.makeText(
                context,
                "I've updated your ingredient", Toast.LENGTH_LONG
            ).show()
        }

        else -> {
            Toast.makeText(
                context,
                context.getString(R.string.i_am_sorry_an_error_occurred), Toast.LENGTH_SHORT
            ).show()
        }
    }

}


@Preview
@Composable
fun AddIngredientToPantryPreview() {
    val ingredient = Ingredient(
        id = 1,
        name = "Egg",
        image = "",
    )
    AddIngredientToPantry(navController = rememberNavController(), ingredient = ingredient)
}