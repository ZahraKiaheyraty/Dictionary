package ir.afagh.dictionary.data.interactor.lastSearch

import ir.afagh.dictionary.data.db.entity.LastSearchEntity
import kotlinx.coroutines.flow.Flow

interface LastSearchRepository {
    suspend fun insert(word: LastSearchEntity)
    fun getAllLastSearchWord(): Flow<List<LastSearchEntity>>
    suspend fun deleteHistory()
}