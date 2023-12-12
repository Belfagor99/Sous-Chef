package eu.mobcomputing.dima.registration.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val dietType: DietType = DietType.NORMAL,
    val allergies: List<String> = emptyList()
)
