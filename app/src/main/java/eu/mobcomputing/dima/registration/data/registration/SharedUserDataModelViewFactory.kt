package eu.mobcomputing.dima.registration.data.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating instances of [SharedUserDataModel].
 */
class SharedUserDataModelViewFactory : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified [ViewModel] class.
     *
     * @param modelClass The class of the [ViewModel] to create.
     * @return A newly created [ViewModel] of the given class.
     * @throws IllegalArgumentException if the [ViewModel] class is unknown.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedUserDataModel::class.java)) {
            return SharedUserDataModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}