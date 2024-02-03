package eu.mobcomputing.dima.registration.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.Allergen
import eu.mobcomputing.dima.registration.models.DietOption
import eu.mobcomputing.dima.registration.models.DietType
import eu.mobcomputing.dima.registration.navigation.Screen
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SharedUserDataViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: SharedUserDataViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = spyk(SharedUserDataViewModel())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `allergenOnClick should toggle allergen state and call appropriate functions`() {
        val allergen = Allergen("TestAllergen")
        allergen.selectedState.value = false
        viewModel.allergenOnClick(allergen)
        verify(exactly = 1) { viewModel.allergenOnClick(allergen) }
        assertTrue(allergen.selectedState.value)

        if (allergen.selectedState.value) {
            verify(exactly = 1) { viewModel.addAllergenToUser(allergen) }
            verify(exactly = 0) { viewModel.removeAllergenFromUser(allergen) }
        } else {
            verify(exactly = 0) { viewModel.addAllergenToUser(allergen) }
            verify(exactly = 1) { viewModel.removeAllergenFromUser(allergen) }
        }
    }

    @Test
    fun `allergiesScreenNext should navigate to UserDiet screen`() {
        val navController = mockk<NavController>(relaxed = true)
        viewModel.allergiesScreenNext(navController)
        verify(exactly = 1) { navController.navigate(Screen.UserDiet.route) }
    }

    @Test
    fun `backStepOnClick should pop back stack`() {
        val navController = mockk<NavController>(relaxed = true)
        viewModel.backStepOnClick(navController)
        verify(exactly = 1) { navController.popBackStack() }
    }

    @Test
    fun `dietOptionOnClick should toggle diet option and set diet type`() {
        val dietOption = DietOption(DietType.VEGAN, R.drawable.vegan_removebg_preview)
        dietOption.selected.value = false

        viewModel.dietOptionOnClick(dietOption)
        verify(exactly = 1) { viewModel.dietOptionOnClick(dietOption) }
        assertTrue(dietOption.selected.value)

        if (dietOption.selected.value) {
            verify(exactly = 1) { viewModel.setDietType(dietOption.type) }
        } else {
            verify(exactly = 0) { viewModel.setDietType(dietOption.type) }
        }
    }
}