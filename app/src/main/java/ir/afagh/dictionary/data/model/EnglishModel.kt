package ir.afagh.dictionary.data.model

import ir.afagh.dictionary.data.db.entity.EnglishEntity
import ir.afagh.dictionary.data.db.entity.LastSearchEntity

data class EnglishModel(
    var idEnglishWord: Int,
    var persianWord: String,
    var englishWord: String,
)

fun EnglishEntity.mapEnglishEntityToEnglishModel(): EnglishModel {
    return EnglishModel(
        idEnglishWord = idEnglishWord,
        persianWord = persianWord,
        englishWord = englishWord
    )
}

fun LastSearchEntity.mapLastSearchEntityToEnglishModel(): EnglishModel {
    return EnglishModel(
        idEnglishWord = id,
        persianWord = persian_word,
        englishWord = english_word
    )
}