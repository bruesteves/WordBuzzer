package com.wordbuzzer.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wordbuzzer.WordBuzzerApplication
import com.wordbuzzer.model.game.PossibleTranslation
import com.wordbuzzer.model.game.Translation
import io.reactivex.Single
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import kotlin.random.Random

const val FILE_NAME = "words.json"
class GameRepositoryImpl() : GameRepository {

    override fun getTranslation(): Single<Translation> {
        val fileText = readFile()

        val type = object : TypeToken<ArrayList<Translation>>() {}.type
        val translationList : ArrayList<Translation?>? = Gson().fromJson(fileText, type)

        if(translationList != null)
        for (translation in translationList) {
            translation?.possibleTranslations = getPossibleTranslations(translationList)
        }

        return Single.just(translationList?.get(Random.nextInt(translationList.size)))
    }

    private fun getPossibleTranslations(translationList: ArrayList<Translation?>?) : ArrayList<PossibleTranslation?>? {
        val possibleTranslations: ArrayList<PossibleTranslation?>? = arrayListOf()

        if(translationList.isNullOrEmpty()) return arrayListOf()
        for (translation in translationList) {
            translation?.toValue?.let {
                possibleTranslations?.add(PossibleTranslation(it))
            }
        }

        return possibleTranslations
    }


    private fun readFile() : String {
        var tContents = ""

        try {
            val stream = WordBuzzerApplication.instance.resources.getAssets().open(FILE_NAME)

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            tContents = String(buffer)
        } catch (e: IOException) {
        }

        return tContents
    }
}