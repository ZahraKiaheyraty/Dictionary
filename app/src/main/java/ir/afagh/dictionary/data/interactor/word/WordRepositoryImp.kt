package ir.afagh.dictionary.data.interactor.word

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ir.afagh.dictionary.data.db.dao.WordsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class WordRepositoryImp constructor(private val local: WordsDao) : WordRepository {
    override fun searchPersianWords(msg: String) = Pager(
        PagingConfig(
            pageSize = 100, maxSize = 500,
            enablePlaceholders = false
        )
    ) {
        local.searchPersianWords(msg)
    }.flow.flowOn(Dispatchers.IO)

    override fun searchEnglishWords(msg: String) = Pager(
        PagingConfig(
            pageSize = 100, maxSize = 500,
            enablePlaceholders = false
        )
    ) {
        local.searchEnglishWords(msg)
    }.flow.flowOn(Dispatchers.IO)
}