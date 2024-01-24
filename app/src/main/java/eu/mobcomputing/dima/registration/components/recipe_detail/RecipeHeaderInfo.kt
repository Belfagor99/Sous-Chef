package eu.mobcomputing.dima.registration.components.recipe_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeHeaderInfo(

    title : String,
    img_url : String,
    readyInMinutes : Int,
    servings : Int,
){
    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth()
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

                Box(modifier = Modifier.weight(1f).wrapContentHeight()){

                    GlideImage(
                        model = img_url ,
                        contentDescription = "recipe image",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Box(modifier = Modifier.weight(1f).wrapContentHeight()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {

                        Text(
                            text = title,
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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .weight(1f)
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                        ) {

                            InputChip(
                                modifier = Modifier.padding(4.dp).weight(1f),
                                onClick = { /*TODO*/ },
                                label = {
                                    Text(
                                        text = readyInMinutes.toString() + "\'\'",
                                        style = TextStyle(
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .weight(1f)
                                    )
                                },
                                selected = false,
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.AccessTime,
                                        contentDescription = "ready in minutes"
                                    )
                                }
                            )

                            InputChip(
                                modifier = Modifier.padding(4.dp).weight(1f),
                                onClick = { /*TODO*/ },
                                label = {
                                    Text(
                                        text = servings.toString(),
                                        style = TextStyle(
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .weight(1f)

                                    )
                                },
                                selected = false,
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.DinnerDining,
                                        contentDescription = "ready in minutes"
                                    )
                                }
                            )

                        }


                    }

                }
            }
        
    }
}




@Composable
@Preview (showBackground = true)
fun RecipeHeaderInfoPreview(){

    RecipeHeaderInfo("TITLE","img",45,4)
}