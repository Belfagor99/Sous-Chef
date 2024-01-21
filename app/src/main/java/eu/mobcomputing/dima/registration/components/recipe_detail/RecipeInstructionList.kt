package eu.mobcomputing.dima.registration.components.recipe_detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.models.Instruction

@Composable
fun RecipeInstructionList(
    instructions : List<Instruction>,
    listState :LazyListState
){
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        state = listState,
    ) {
        items(instructions) { it ->
            // Grid item Composable
            RecipeInstructionItem(
                step_number = it.step_num,
                content = it.content
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun RecipeInstructionListPreview(){
    val x = listOf(
        Instruction(1,"dsdasdada"),
        Instruction(2,"dsdasdada"),
        Instruction(3,"dsdasdada"),
        Instruction(4,"dsdasdada"),
        Instruction(5,"dsdasdada"),
    )

    RecipeInstructionList(instructions = x, rememberLazyListState())
}