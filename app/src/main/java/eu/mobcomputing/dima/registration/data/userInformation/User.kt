package eu.mobcomputing.dima.registration.data.userInformation

// Data class representing a user.
data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var dietType: DietType = DietType.NORMAL,
    var allergies: List<Allergen> = emptyList()
)