package ir.afagh.dictionary.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ir.afagh.dictionary.data.db.relations.EnglishWithDefinition
import kotlinx.coroutines.flow.Flow

@Dao
interface DefineDao {
    @Transaction
    @Query("SELECT * FROM dbo_translate_words WHERE id_english_word = :id")
    fun defineWords(id: Int): Flow<EnglishWithDefinition>
}