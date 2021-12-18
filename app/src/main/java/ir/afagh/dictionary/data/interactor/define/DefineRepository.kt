package ir.afagh.dictionary.data.interactor.define

import ir.afagh.dictionary.data.db.relations.EnglishWithDefinition
import kotlinx.coroutines.flow.Flow

interface DefineRepository {
    fun defineWords(id: Int): Flow<EnglishWithDefinition>
}