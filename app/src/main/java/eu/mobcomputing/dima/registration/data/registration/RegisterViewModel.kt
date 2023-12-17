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
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.data.Allergen
import eu.mobcomputing.dima.registration.data.DietOption
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

    val userInfoStep: MutableState<Int> = mutableIntStateOf(0)
    val allergiesStep: MutableState<Int> = mutableIntStateOf(1)
    val dietTypeStep: MutableState<Int> = mutableIntStateOf(2)

    val steps: List<String> = listOf("1", "2", "3")
    val user = mutableStateOf(User())
    val cameBack = mutableStateOf(false)
    val firstStepPassed = mutableStateOf(false)

    val allergens = listOf(
        Allergen("Eggs"),
        Allergen("Fish"),
        Allergen("Almond"),
        Allergen("Gluten"),
        Allergen("Chocolate"),
        Allergen("Avocado"),
        Allergen("Mustard"),
        Allergen("Peach"),
        Allergen("Peanuts"),
        Allergen("Soy"),
        Allergen("Milk"),
        Allergen("Soybeans"),
        Allergen("Walnuts"),
        Allergen("Berries"),
        Allergen("Dairy"),

        )

    var selectedDietOption = { mutableStateOf<DietOption?>(null) }

    val dietOptions = listOf(
        DietOption(DietType.VEGAN, R.drawable.vegan_removebg_preview),
        DietOption(DietType.VEGETARIAN, R.drawable.vegetarian_removebg_preview),
        DietOption(DietType.GLUTEN_FREE, R.drawable.glutenfree_removebg_preview),
        DietOption(DietType.LACTOSE_FREE, R.drawable.lactofree_removebg_preview),
        DietOption(DietType.NORMAL, R.drawable.normal_removebg_preview),
        DietOption(DietType.PESCETARIAN, R.drawable.pescetarian_preview),
    )

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
                nextStepOnClickInfoUser(navController = navController)
            }
        }
        validateDataWithRules()
    }


    private fun register(navController: NavController) {
        createFirebaseUser(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password,
            navController = navController
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

    private fun createUserInDatabaseWithData(user: MutableState<User>) {
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            val userDocumentRef: DocumentReference =
                db.collection("users").document(uid)
            userDocumentRef.set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "User document created successfully")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error creating user document", e)
                }

        }
        else{
            Log.d(TAG, "Uzivatel nie je prihlaseny, alebo nema??? UID")
        }
    }

    private fun performWhenClickedNextButtonRegister(navController: NavController) {
        registrationInProgress.value = false
        updateUser()
        readOnlyPassword.value = true
        navController.navigate(Screen.UserAllergies.route)
    }

    private fun createFirebaseUser(email: String, password: String, navController: NavController) {
        registrationInProgress.value = true
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside on Complete Lister")
                Log.d(TAG, "is Successful = ${it.isSuccessful}")
                if (it.isSuccessful) {
                    performWhenClickedNextButtonRegister(navController)
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

    fun nextStepOnClickInfoUser(navController: NavController) {
        if (FirebaseAuth.getInstance().currentUser == null) {
            register(navController)
        } else {
            performWhenClickedNextButtonRegister(navController)
        }
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

    fun addDietType(dietType: DietType) {
        user.value = user.value.copy(dietType = dietType)
    }

    fun backStepOnClick(navController: NavController) {
        navController.popBackStack()
    }

    fun addAllergenToUser(
        allergen: Allergen
    ) {
        addAllergen(allergen)
    }

    fun removeAllergenFromUser(
        allergen: Allergen
    ) {
        removeAllergen(allergen)
    }

    fun allergenOnClick(
        allergen: Allergen
    ) {
        if (allergen.selectedState.value) {
            removeAllergenFromUser(allergen)
        } else {
            addAllergenToUser(allergen)
        }
        allergen.selectedState.value = !allergen.selectedState.value
        Log.d(TAG, "allergen clicked")
        Log.d(TAG, allergens.toString())
    }

    fun allergiesScreenNext(navController: NavController) {
        navController.navigate(Screen.UserDietScreen.route)
    }

    fun dietOptionOnClick(dietOption: DietOption) {
        for (dietOpt in dietOptions) {
            if (dietOpt.selected.value) {
                dietOpt.selected.value = false
                break
            }
        }
        dietOption.selected.value = true
        addDietType(dietOption.type)
        Log.d(TAG, "dietOpt clicked")
        Log.d(TAG, dietOption.toString())

    }

    fun finishRegistration(navController: NavController) {
        createUserInDatabaseWithData(user)
    }
}



