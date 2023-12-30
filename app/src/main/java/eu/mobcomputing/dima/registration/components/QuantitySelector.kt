package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantitySelector(
    initialQuantity: Int = 1,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var quantity by remember { mutableStateOf(initialQuantity) }
    val keyboardController = LocalSoftwareKeyboardController.current



    Row(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        //--------------- MINUS BUTTON -------------------
        IconButton(
            onClick = {
                if (quantity > 1) {
                    quantity--
                    onQuantityChange(quantity)
                }
            }
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Remove")
        }

        //--------------- TEXT FIELD -------------------
        OutlinedTextField(
            textStyle =  TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
            ),

            value = quantity.toString(),
            onValueChange = {
                val newQuantity = it.toIntOrNull() ?: 1
                quantity = if (newQuantity > 0) newQuantity else 1
                onQuantityChange(quantity)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .padding(8.dp)
                .width(80.dp),
        )

        //--------------- PLUS BUTTON -------------------
        IconButton(

            onClick = {
                quantity++
                onQuantityChange(quantity)
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

    }
}


@Preview
@Composable
fun QuantitySelectorPreview(){
   QuantitySelector(
       onQuantityChange = {}
   )
}