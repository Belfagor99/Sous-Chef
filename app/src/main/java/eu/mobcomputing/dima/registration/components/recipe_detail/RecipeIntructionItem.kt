package eu.mobcomputing.dima.registration.components.recipe_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
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

@Composable
fun RecipeInstructionItem(
    step_number : Int,
    content : String
){

    Column (
        modifier = Modifier.padding(8.dp)
    ){

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Step $step_number",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            )


            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )

        }
        Text(
            text = content,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Start),
        )
    }
}


@Composable
@Preview(showBackground = true)
fun RecipeInstructionItemPreview(){
    RecipeInstructionItem(1,"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
}