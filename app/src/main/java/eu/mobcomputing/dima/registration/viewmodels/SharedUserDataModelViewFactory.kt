package eu.mobcomputing.dima.registration.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating instances of [SharedUserDataViewModel].
 */
@Suppress("UNCHECKED_CAST")
class SharedUserDataModelViewFactory : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified [ViewModel] class.
     *
     * @param modelClass The class of the [ViewModel] to create.
     * @return A newly created [ViewModel] of the given class.
     * @throws IllegalArgumentException if the [ViewModel] class is unknown.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedUserDataViewModel::class.java)) {
            return SharedUserDataViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}