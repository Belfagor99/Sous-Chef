package eu.mobcomputing.dima.registration.components.add_ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.core.text.isDigitsOnly
import eu.mobcomputing.dima.registration.components.user.CharacteristicsEditorDialog
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Composable function representing a quantity selector with a text field and buttons to increase or decrease the quantity.
 *
 * @param modifier The [Modifier] to apply to the quantity selector.
 * @param initialQuantity The initial quantity value. Default is 1.
 * @param onQuantityChange Callback function invoked when the quantity changes. It provides the updated quantity.
 */
@Composable
fun QuantitySelector(
    initialQuantity: Double = 1.0,
    onQuantityChange: (Double) -> Unit,
    onUnitChange: (String) -> Unit,
    listOfUnits : List<String>,
) {
    var quantity by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    /*
     * State variables for the date picker
     */
    var selectedUnit by remember {
        mutableStateOf(listOfUnits[0])
    }

    var showUnitSelectorDialog by remember {
        mutableStateOf(false)
    }



    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

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
                if(it.isNotEmpty() && it.isDigitsOnly())
                    quantity = it
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    onQuantityChange(quantity.toDouble())
                }
            ),
            modifier = Modifier
                .padding(8.dp)
                //.width(80.dp)
                .fillMaxHeight()
                .weight(1f),
        )

        //--------------- PLUS BUTTON -------------------
        OutlinedButton(
            onClick = {
                showUnitSelectorDialog = true
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            shape = RoundedCornerShape(corner = CornerSize(4.dp))

        ) {
            Text(
                text = selectedUnit,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                ),
            )
        }

    }

    if (showUnitSelectorDialog) {
        CharacteristicsEditorDialog(
            onDismissRequest = { showUnitSelectorDialog = false },
            dialogTitle = "Select  the unit type",
            userCharacteristics = listOfUnits,
            singleSelection = true,
            onConfirmation = { it ->
                selectedUnit = it[0]
                onUnitChange(selectedUnit)
                showUnitSelectorDialog = false
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun QuantitySelectorPreview(){
   QuantitySelector(

       onQuantityChange = {},
       onUnitChange = {},
       listOfUnits = listOf("cup","tablespoon")
   )
}