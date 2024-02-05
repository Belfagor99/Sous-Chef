package eu.mobcomputing.dima.registration.viewmodels


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.lifecycle.Observer
import eu.mobcomputing.dima.registration.models.Ingredient
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchIngredientViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule() // This is needed when testing LiveData

    @Mock
    lateinit var application: Application
    @Mock
    private val mockApplicationContext: Context? = null

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(application.applicationContext).thenReturn(mockApplicationContext)
    }

    @Test
    fun `searchIngredient should populate ingredients when successful`() = runTest {
        // Setup
        val viewModel = SearchIngredientViewModel(application)


        val observer = Observer<List<Ingredient>> {}
        viewModel.ingredients.observeForever(observer)
        assertTrue(viewModel.ingredients.value!!.isEmpty())

        // When
        viewModel.searchIngredient("Chicken")

        // Then
        assertTrue(viewModel.ingredients.value!!.isNotEmpty())

    }

    @Test
    fun `getSelectedIngredientInfo should return correct ingredient info on successful API response`() = runTest {
        // Given
        val viewModel = SearchIngredientViewModel(application)

        // When
        val result = viewModel.getSelectedIngredientInfo(18350)

        // Then
        assertNotNull(result)
        assertEquals(Ingredient::class.java, result.javaClass)
        assertEquals(18350, result.id)
    }
}