package eu.mobcomputing.dima.registration.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.mobcomputing.dima.registration.api.APIService
import eu.mobcomputing.dima.registration.models.Ingredient
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject


/**
 * ViewModel class for handling ingredient data in the search feature.
 *
 * This ViewModel is responsible for loading and filtering a list of ingredients
 * from a CSV file. It utilizes Hilt for dependency injection and extends
 * AndroidViewModel to manage the application context.
 *
 * @property application The application context provided through dependency injection.
 * @property _ingredients MutableLiveData holding the list of ingredients.
 * @property ingredients LiveData exposing the list of ingredients.
 */

@HiltViewModel
class SearchIngredientViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    val ingredients: LiveData<List<Ingredient>> = _ingredients

    //private var allIngredients: List<Ingredient> = emptyList()

    /**
     * Initializes the ViewModel by launching a coroutine to load ingredients from the CSV file.
     */
    init {
        viewModelScope.launch {
            //loadIngredientFromCsv()
        }

    }

    /**
     * Loads ingredient data from the "ingredients.csv" file in the assets folder.
     *
     * @throws IOException If an error occurs while reading the CSV file.
     */
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

    /**
     * Converts CSV data to a list of Ingredient objects.
     *
     * @param csvData List of strings representing CSV data.
     * @return List of Ingredient objects parsed from the CSV data.
     */
    private fun convertCsvDataToIngredientList(csvData: List<String>): List<Ingredient> {
        return csvData.map { line ->
            val values = line.split(";")
            Ingredient(id = values[1].toInt(), name = values[0])
        }
    }


    /**
     * Filters the list of ingredients based on the provided search text.
     *
     * If the search text is blank, the original list is reloaded from the CSV file.
     * Otherwise, the list is filtered based on whether the ingredient's name
     * contains the search text (case-insensitive).
     *
     * @param searchText The text to use for filtering ingredients.
     */
    fun filterIngredients(searchText: String) {

        if (searchText.isBlank()){
            loadIngredientFromCsv()
        }else{
            val filteredList = ingredients.value?.filter { ingredient ->
                ingredient.name.contains(searchText, ignoreCase = true)
            }
            _ingredients.value = filteredList!!
            Log.v("VIEW-MODEL", _ingredients.value.toString())

        }
    }




    suspend fun searchIngredient( toSearch : String ){

        val response = APIService().api.searchIngredient(
            query = toSearch,
            number = 30
        )

        if (response.isSuccessful){
            _ingredients.value = response.body()?.results
        }

    }












    suspend fun getSelectedIngredientInfo(id : Int): Ingredient{
        val response = APIService().api.getIngredientInfoById(id = id)

        return if(response.isSuccessful){
            response.body()!!
        }else{
            Ingredient()
        }
    }




}