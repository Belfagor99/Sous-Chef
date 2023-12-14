package eu.mobcomputing.dima.registration.data.registration

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore
import eu.mobcomputing.dima.registration.data.Allergen
import eu.mobcomputing.dima.registration.data.DietType
import eu.mobcomputing.dima.registration.data.User
import eu.mobcomputing.dima.registration.data.rules.Validator
import eu.mobcomputing.dima.registration.screens.Screen

class RegisterViewModel() : ViewModel() {
    private val TAG = RegisterViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidationPassed = mutableStateOf(false)
    var registrationInProgress = mutableStateOf(false)
    var readOnlyPassword = mutableStateOf(false)

    val currentStep: MutableState<Int> = mutableIntStateOf(0)

    val steps: List<String> = listOf("1", "2", "3")
    val user = mutableStateOf(User())
    val cameBack = mutableStateOf(false)
    val firstStepPassed = mutableStateOf(false)


    fun getSharedName(): String {
        return user.value.firstName
    }

    fun getSharedLastName(): String {
        return user.value.lastName
    }

    fun getSharedEmail(): String {
        return user.value.email
    }

    fun onEvent(event: RegistrationUIEvent, navController: NavController) {

        when (event) {
            is RegistrationUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
                //registrationSharedViewModel.updateNameOfUser(event.firstName)
            }

            is RegistrationUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
                //registrationSharedViewModel.updateLastOfUser(event.lastName)

            }

            is RegistrationUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
                //registrationSharedViewModel.updateEmailOfUser(event.email)
            }

            is RegistrationUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is RegistrationUIEvent.NextButtonClicked -> {
                nextStepOnClick(navController = navController)
            }
        }
        validateDataWithRules()
    }


    private fun register() {
        createFirebaseUser(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password,
        )
    }

    private fun validateDataWithRules() {
        val firstNameValidation = Validator.validateFirstName(
            registrationUIState.value.firstName
        )
        val lastNameValidation = Validator.validateLastName(
            registrationUIState.value.lastName
        )
        val emailValidation = Validator.validateEmail(
            registrationUIState.value.email
        )
        val passwordValidation = Validator.validatePassword(
            registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = firstNameValidation.status,
            lastNameError = lastNameValidation.status,
            emailError = emailValidation.status,
            passwordError = passwordValidation.status
        )

        allValidationPassed.value = firstNameValidation.status &&
                lastNameValidation.status &&
                emailValidation.status &&
                passwordValidation.status

    }

    private fun createUserInDatabase(
        email: String,
        firstName: String,
        lastName: String,
        userID: String
    ) {
        val db = Firebase.firestore
        val user = User(
            firstName, lastName, email
        )

        val userDocumentRef: DocumentReference = db.collection("users").document(userID)

        userDocumentRef.set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User document created successfully")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error creating user document", e)
            }


    }

    private fun createFirebaseUser(email: String, password: String) {
        registrationInProgress.value = true
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside on Complete Lister")
                Log.d(TAG, "is Successful = ${it.isSuccessful}")
                if (it.isSuccessful) {
                    registrationInProgress.value = false
//                    navController.navigate(route = Screen.Home.route,
//                        builder = {
//                            popUpTo(Screen.Home.route) {
//                                inclusive = true
//                            }
//                        })
                    val userID = auth.currentUser?.uid
                    if (userID != null) {
                        createUserInDatabase(
                            firstName = registrationUIState.value.firstName,
                            lastName = registrationUIState.value.lastName,
                            email = email,
                            userID = userID
                        )
                    }
                }

            }
            .addOnFailureListener {
                registrationInProgress.value = false
                Log.d(TAG, "Inside on Failure Lister")
                Log.d(TAG, "Exception = ${it.message}")

            }

    }


    fun redirectToLogInScreen(navController: NavController) {
        navController.navigate(route = Screen.LogIn.route)
    }

    fun logOut(navController: NavController) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener {
            if (it.currentUser == null) {
                navController.navigate(route = Screen.Welcome.route)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun nextStepOnClick(navController: NavController) {
        register()
        updateUser()
        readOnlyPassword.value = true
        navController.navigate(Screen.UserAllergies.route)
    }

    fun updateUser() {
        updateNameOfUser(registrationUIState.value.firstName)
        updateLastOfUser(registrationUIState.value.lastName)
        updateEmailOfUser(registrationUIState.value.email)
        updateFirstStepPassed()


    }


    fun updateNameOfUser(firstName: String) {
        user.value = user.value.copy(firstName = firstName)
    }

    fun updateLastOfUser(lastName: String) {
        user.value = user.value.copy(lastName = lastName)
    }

    fun updateEmailOfUser(email: String) {
        user.value = user.value.copy(email = email)
    }

    fun updateFirstStepPassed() {
        firstStepPassed.value = true
    }

    fun updateDietTypeOfUser(dietType: DietType) {
        user.value = user.value.copy(dietType = dietType)
    }

    fun addAllergen(allergen: Allergen) {
        user.value = user.value.copy(allergies = user.value.allergies + allergen)

    }


    fun removeAllergen(allergen: Allergen) {
        user.value = user.value.copy(allergies = user.value.allergies - allergen)

    }



}



