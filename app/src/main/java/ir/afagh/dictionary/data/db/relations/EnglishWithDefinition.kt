package ir.afagh.dictionary.data.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import ir.afagh.dictionary.data.db.entity.DefinitionEntity
import ir.afagh.dictionary.data.db.entity.EnglishEntity

data class EnglishWithDefinition(
    @Embedded
    var english: EnglishEntity? = null,
    @Relation(parentColumn = "id_english_word", entityColumn = "id_english_word")
    var definition: List<DefinitionEntity>? = null
)