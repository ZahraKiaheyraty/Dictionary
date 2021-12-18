package ir.afagh.dictionary.data.interactor.define

import ir.afagh.dictionary.data.db.dao.DefineDao
import ir.afagh.dictionary.data.db.relations.EnglishWithDefinition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DefineRepositoryImp constructor(var local: DefineDao) : DefineRepository {
    override fun defineWords(id: Int): Flow<EnglishWithDefinition> {
        return local.defineWords(id).flowOn(Dispatchers.IO)
    }
}