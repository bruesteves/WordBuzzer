package com.wordbuzzer.model.game

import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("text_spa") val fromValue: String,
    @SerializedName("text_eng") val toValue: String,
    var possibleTranslations: ArrayList<PossibleTranslation?>? = null
)

data class PossibleTranslation(val toValue: String)