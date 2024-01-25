package eu.mobcomputing.dima.registration.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.recipe_detail.CompletedRecipeDialog
import eu.mobcomputing.dima.registration.components.recipe_detail.IngredientRowList
import eu.mobcomputing.dima.registration.components.recipe_detail.RecipeHeaderInfo
import eu.mobcomputing.dima.registration.components.recipe_detail.RecipeInstructionList
import eu.mobcomputing.dima.registration.models.AnalyzedInstruction
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Instruction
import eu.mobcomputing.dima.registration.models.Recipe
import eu.mobcomputing.dima.registration.viewmodels.PantryViewModel

/**
 * Composable function for displaying the recipe details' screen.
 *
 * @param navController Navigation controller for handling navigation within the app.
 * @param recipe Recipe to display
 */

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipe: Recipe,
    viewModel : PantryViewModel = hiltViewModel(),
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val isSmallScreen = maxWidth < 600.dp

        if (isSmallScreen) {
            smartphoneRecipeDetailScreen(navController = navController,recipe = recipe,viewModel = viewModel,screenType = isSmallScreen)
        } else {
            tabletRecipeDetailScreen(navController = navController, recipe = recipe, viewModel = viewModel, screenType = isSmallScreen)
        }
    }

}


@Composable
fun smartphoneRecipeDetailScreen( navController: NavController, recipe: Recipe, viewModel: PantryViewModel, screenType: Boolean){
    val listState = rememberLazyListState()

    var showRecipeCompletedDialog by remember {
        mutableStateOf(0)
    }
    /*
    *   Get local context for Toast message
    * */
    val context = LocalContext.current


    Scaffold(
        modifier = Modifier
            .background(colorResource(id = R.color.pink_50)),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showRecipeCompletedDialog = 1 },
                icon = { Icon(Icons.Filled.Done, "Recipe Done.") },
                text = { Text(text = "Recipe completed") },
                expanded = !listState.isScrollInProgress
            )
        },
        bottomBar = {
            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 0
            )
        },
    ) {
        Surface(
            color = colorResource(id = R.color.pink_50),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(colorResource(id = R.color.pink_50))
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {

                /*RECIPE CARD HEADER INFO*/
                Box(modifier = Modifier
                    .weight(weight = 0.5f, fill = true)
                    .wrapContentSize()) {
                    RecipeHeaderInfo(title = recipe.title,
                        img_url = recipe.image ,
                        readyInMinutes = recipe.readyInMinutes ,
                        servings = recipe.servings
                    )

                }

                IngredientRowList(ingredients = recipe.ingredients!!,screenType)

                Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                    RecipeInstructionList(instructions = recipe.instructions!![0].steps, listState = listState)

                }
            }
        }


        if(showRecipeCompletedDialog == 1){
            CompletedRecipeDialog(
                onDismissRequest = { showRecipeCompletedDialog =0 },
                onConfirmation = { selected ->
                    viewModel.removeFromPantry(selected)
                    showRecipeCompletedDialog = 0
                    reloadScreen(navController)
                    Toast.makeText(context,"Yummy! I removed the ingredients from your digital pantry", Toast.LENGTH_SHORT).show()
                },
                dialogTitle = "Selects which ingredients are finished",
                ingredients = recipe.ingredients!!,
            )
        }



    }
}

@Composable
fun tabletRecipeDetailScreen( navController: NavController,recipe: Recipe,viewModel: PantryViewModel, screenType: Boolean){
    val listState = rememberLazyListState()

    var showRecipeCompletedDialog by remember {
        mutableStateOf(0)
    }
    /*
    *   Get local context for Toast message
    * */
    val context = LocalContext.current


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showRecipeCompletedDialog = 1 },
                icon = { Icon(Icons.Filled.Done, "Recipe Done.") },
                text = { Text(text = "Recipe completed") },
                expanded = !listState.isScrollInProgress
            )
        },
        bottomBar = {
            NavigationBarComponent(
                navController = navController,
                selectedItemIndex = 0
            )
        },
    ) {
        Surface(
            color = colorResource(id = R.color.pink_50),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(colorResource(id = R.color.pink_50))
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
            ) {

                Column (
                    modifier = Modifier.weight(1f)
                ){


                    /*RECIPE CARD HEADER INFO*/
                    Box(modifier = Modifier.weight(weight = 0.7f, fill = true).wrapContentSize()) {

                        Row {
                            RecipeHeaderInfo(
                                title = recipe.title,
                                img_url = recipe.image,
                                readyInMinutes = recipe.readyInMinutes,
                                servings = recipe.servings
                            )

                        }
                    }
                    Box(modifier = Modifier.weight(weight = 1f, fill = true).wrapContentSize()) {

                        IngredientRowList(ingredients = recipe.ingredients!!, screenType)
                    }
                }

                Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                    RecipeInstructionList(instructions = recipe.instructions!![0].steps, listState = listState)

                }
            }
        }


        if(showRecipeCompletedDialog == 1){
            CompletedRecipeDialog(
                onDismissRequest = { showRecipeCompletedDialog =0 },
                onConfirmation = { selected ->
                    viewModel.removeFromPantry(selected)
                    showRecipeCompletedDialog = 0
                    reloadScreen(navController)
                    Toast.makeText(context,"Yummy! I removed the ingredients from your digital pantry", Toast.LENGTH_SHORT).show()
                },
                dialogTitle = "Selects which ingredients are finished",
                ingredients = recipe.ingredients!!,
            )
        }


    }
}




@Preview
@Composable
fun RecipeDetailScreenPreview(){

    val recipe  = Recipe(
        id=636207,
        image="https://spoonacular.com/recipeImages/636207-312x231.jpg",
        instructions = listOf(AnalyzedInstruction(
            name="name",
            steps= listOf(
            Instruction(1,"Wash and steam the broccoli rabe for a few minutes (3 or"),
            Instruction(2,"in a pressure cooker.Thinly slice the onion. Peel the garlic clove, cut in half and remove the stem."),
            Instruction(3,"Heat up 1 or 2 tbsp olive oil in a deep pan.Saut the onion and garlic for a few minutes on medium heat until translucent."),
            Instruction(4,"Add the broccoli rabe, close with a lid and cook for a few minutes."),
            Instruction(5,"Cut the tomatoes in 4 or 8 wedges depending on their size, add to the pan and cook several more minutes.Season with salt and pepper and serve.Proceed as follows with each scalopini:Season with salt and pepper.Dip in beaten egg.Dip in bread crumb and cover evenly.Pan fry in a good amount of butter (add more butter before flipping as the bread crumb will absorb it), 1 or 2 minutes on each side over high heat."),
        ))),
        readyInMinutes=45,
        servings=4,
        //summary="Broccoli Rabe and Breaded Veal Scallopini could be just the <b>gluten free, lacto ovo vegetarian, and primal</b> recipe you've been looking for. One serving contains <b>224 calories</b>, <b>6g of protein</b>, and <b>21g of fat</b>. This recipe serves 4. For <b>\$1.16 per serving</b>, this recipe <b>covers 18%</b> of your daily requirements of vitamins and minerals. 3 people were glad they tried this recipe. This recipe from Foodista requires onion, butter, egg, and salt and pepper. From preparation to the plate, this recipe takes about <b>45 minutes</b>. It works well as an affordable side dish. With a spoonacular <b>score of 60%</b>, this dish is good. Similar recipes are <a href=\\\"https://spoonacular.com/recipes/sauteed-veal-with-lemon-on-a-bed-of-broccoli-rabe-337931\\\">Sauteed Veal with Lemon on a Bed of Broccoli Rabe</a>, <a href=\\\"https://spoonacular.com/recipes/best-veal-scallopini-446991\\\">Best Veal Scallopini</a>, and <a href=\\\"https://spoonacular.com/recipes/veal-scallopini-415460\\\">Veal Scallopini</a>.",//null,
        title="Broccoli Rabe and Breaded Veal Scallopini",
        cuisines=listOf("mediterrean"),//null,
        diets=listOf("gluten free",
            "lacto ovo vegetarian",
            "primal"),//null,
        ingredients= listOf(
            Ingredient(
                id=11096,
                name="broccoli rabe",
                image="https://spoonacular.com/cdn/ingredients_100x100/broccoli-rabe.jpg",
                original="1 pound (500 g) broccoli rabe",
                amount=500.0,
                unit="g",
                consistency="",
                expiringDate=null,
                userQuantity=0.0
            ),
            Ingredient(
                id=1001,
                name="butter",
                image="https://spoonacular.com/cdn/ingredients_100x100/butter-sliced.jpg",
                original="2 tablespoons butter",
                amount=2.0,
                unit="tablespoons",
                consistency="",
                expiringDate=null,
                userQuantity=0.0),
            Ingredient(
                id=1123,
                name="egg",
                image="https://spoonacular.com/cdn/ingredients_100x100/egg.png",
                original="1 egg, beaten in a bowl",
                amount=1.0,
                unit="",
                consistency="",
                expiringDate=null,
                userQuantity=0.0),
            Ingredient(
                id=10511282,
                name="onion",
                image="https://spoonacular.com/cdn/ingredients_100x100/brown-onion.png",
                original="1 small yellow onion",
                amount=1.0,
                unit="small",
                consistency="",
                expiringDate=null,
                userQuantity=0.0)
        )
        , missedIngredientCount=0
    )



    RecipeDetailScreen(
        navController = rememberNavController(),
        recipe,
    )
}
