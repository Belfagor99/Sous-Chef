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
import eu.mobcomputing.dima.registration.utils.checkNetworkConnectivity
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


    private var _connectionStatus = MutableLiveData<Boolean>(checkNetworkConnectivity(application.applicationContext))
    var connectionStatus : LiveData<Boolean> = _connectionStatus





    private val _ingredients = MutableLiveData<List<Ingredient>>(emptyList())
    val ingredients: LiveData<List<Ingredient>> = _ingredients



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