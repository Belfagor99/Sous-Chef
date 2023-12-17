package eu.mobcomputing.dima.registration.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.mobcomputing.dima.registration.data.registration.SharedUserDataModel

class SharedUserDataModelViewFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedUserDataModel::class.java)) {
            return SharedUserDataModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}