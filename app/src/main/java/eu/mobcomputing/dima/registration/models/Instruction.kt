package eu.mobcomputing.dima.registration.models

import com.google.gson.annotations.SerializedName

data class Instruction(
    @SerializedName("number") val step_num: Int ,
    @SerializedName("step") val content: String,
)