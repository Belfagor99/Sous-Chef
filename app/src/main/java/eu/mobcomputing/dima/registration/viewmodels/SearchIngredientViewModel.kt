package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.mobcomputing.dima.registration.models.Ingredient
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


/**
 * The reason why ViewModels shouldn't contain an instance of Context or anything like Views or other
 * objects that hold onto a Context is because it has a separate lifecycle than Activities and Fragments.
 *
 * What I mean by this is, let's say you do a rotation change on your app. This causes your Activity
 * and Fragment to destroy itself so it recreates itself. ViewModel is meant to persist during this state,
 * so there's chances of crashes and other exceptions happening if it's still holding a View or Context to
 * the destroyed Activity.
 */

@HiltViewModel
class SearchIngredientViewModel @Inject constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    //private var allIngredients: List<Ingredient> = emptyList()


    init {
        viewModelScope.launch {
            loadIngredientFromCsv()
        }

    }

    private fun loadIngredientFromCsv() {
        try {
            val inputStream = getApplication<Application>().assets.open("ingredients.csv")

            val reader = BufferedReader(InputStreamReader(inputStream))
            val csvData = reader.readLines()
            val ingredientList = convertCsvDataToIngredientList(csvData)
            _ingredients.value = ingredientList
        } catch (e: IOException) {
            // Handle the exception, e.g., log an error or show a message to the user
            e.printStackTrace()
        }
    }

    private fun convertCsvDataToIngredientList(csvData: List<String>): List<Ingredient> {
        return csvData.map { line ->
            val values = line.split(";")
            Ingredient(id = values[1].toInt(), name = values[0])
        }
    }


    // Function to filter ingredients based on search text
    fun filterIngredients(searchText: String) {

        if (searchText.isBlank()){
            loadIngredientFromCsv()
        }else{
            val filteredList = ingredients.value?.filter { ingredient ->
                ingredient.name.contains(searchText, ignoreCase = true)
            }
            _ingredients.value = filteredList
            Log.v("VIEW-MODEL", _ingredients.value.toString())

        }
    }



}