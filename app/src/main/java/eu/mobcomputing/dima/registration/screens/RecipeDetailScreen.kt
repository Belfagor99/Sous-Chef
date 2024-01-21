package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.NavigationBarComponent
import eu.mobcomputing.dima.registration.components.recipe_detail.IngredientRowList
import eu.mobcomputing.dima.registration.components.recipe_detail.RecipeHeaderInfo
import eu.mobcomputing.dima.registration.components.recipe_detail.RecipeInstructionList
import eu.mobcomputing.dima.registration.models.AnalyzedInstruction
import eu.mobcomputing.dima.registration.models.Ingredient
import eu.mobcomputing.dima.registration.models.Instruction
import eu.mobcomputing.dima.registration.models.Recipe

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
) {

    val listState = rememberLazyListState()


    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { },
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
                Box(modifier = Modifier.weight(weight = 0.4f, fill = true)) {
                    RecipeHeaderInfo(title = recipe.title,
                        img_url = recipe.image ,
                        readyInMinutes = recipe.readyInMinutes ,
                        servings = recipe.servings
                    )
                    Spacer(modifier = Modifier.height(200.dp))
                }

                IngredientRowList(ingredients = recipe.ingredients!!)

                Box(modifier = Modifier.weight(weight = 1f, fill = true)) {

                    RecipeInstructionList(instructions = recipe.instructions!![0].steps, listState = listState)

                }
            }
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
                userQuantity=0
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
                userQuantity=0),
            Ingredient(
                id=1123,
                name="egg",
                image="https://spoonacular.com/cdn/ingredients_100x100/egg.png",
                original="1 egg, beaten in a bowl",
                amount=1.0,
                unit="",
                consistency="",
                expiringDate=null,
                userQuantity=0),
            Ingredient(
                id=10511282,
                name="onion",
                image="https://spoonacular.com/cdn/ingredients_100x100/brown-onion.png",
                original="1 small yellow onion",
                amount=1.0,
                unit="small",
                consistency="",
                expiringDate=null,
                userQuantity=0)
        )
        , missedIngredientCount=0
    )



    RecipeDetailScreen(
        navController = rememberNavController(),
        recipe,
    )
}
