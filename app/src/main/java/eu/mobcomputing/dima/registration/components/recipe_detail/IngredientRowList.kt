package eu.mobcomputing.dima.registration.components.recipe_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.mobcomputing.dima.registration.models.Ingredient

@Composable
fun IngredientRowList(
    ingredients : List<Ingredient>,
    isSmallScreen: Boolean,
){
    Column(
       modifier = Modifier.padding(start = 10.dp)
    ) {

        Text(
            text = "Ingredients: " ,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Start)
                .padding(4.dp),
        )

        if (isSmallScreen) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                items(ingredients){
                    IngredientRowItem(ingredient = it)
                }
            }
        } else {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
            ) {
                items(ingredients) { it ->
                    // Grid item
                    IngredientRowItem(ingredient = it)
                }
            }

        }


    }

}

@Composable
@Preview(showBackground = true)
fun IngredientRowListPreview(){
    val ingrs = listOf(
        Ingredient(id=0, name = "egg",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "milk",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "chocolate",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "cornstarch",image="", amount = 150.0, unit = "g"),
        Ingredient(id=0, name = "egg",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "milk",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "chocolate",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "cornstarch",image="", amount = 150.0, unit = "g"),
        Ingredient(id=0, name = "egg",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "milk",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "chocolate",image="", amount = 10.0, unit = "g"),
        Ingredient(id=0, name = "cornstarch",image="", amount = 150.0, unit = "g")
    )
    IngredientRowList(ingrs, false)

}