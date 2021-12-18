package ir.afagh.dictionary.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.PagingSource
import ir.afagh.dictionary.data.db.entity.EnglishEntity

@Dao
interface WordsDao {

    @Query("SELECT * FROM dbo_translate_words  where   persian_word like :msg   || '%'  Order By persian_word limit 50 ")
    fun searchPersianWords(msg: String): PagingSource<Int, EnglishEntity>

    @Query("SELECT * FROM dbo_translate_words  where   english_word like :msg   || '%'  Order By english_word limit 50 ")
    fun searchEnglishWords(msg: String): PagingSource<Int, EnglishEntity>
}