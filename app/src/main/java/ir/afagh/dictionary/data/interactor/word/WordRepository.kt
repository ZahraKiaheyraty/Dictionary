package ir.afagh.dictionary.data.interactor.word

import androidx.paging.PagingData
import ir.afagh.dictionary.data.db.entity.EnglishEntity
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun searchPersianWords(msg: String): Flow<PagingData<EnglishEntity>>
    fun searchEnglishWords(msg: String): Flow<PagingData<EnglishEntity>>
}