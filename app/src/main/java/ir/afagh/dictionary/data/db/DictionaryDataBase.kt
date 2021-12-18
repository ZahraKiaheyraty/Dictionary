package ir.afagh.dictionary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.afagh.dictionary.data.db.dao.DefineDao
import ir.afagh.dictionary.data.db.dao.LastSearchDao
import ir.afagh.dictionary.data.db.dao.WordsDao
import ir.afagh.dictionary.data.db.entity.*

@Database(
    entities = [EnglishEntity::class, DefinitionEntity::class, LastSearchEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DictionaryDataBase : RoomDatabase() {
    abstract fun wordDao(): WordsDao
    abstract fun defineDao(): DefineDao
    abstract fun lastSearchDao(): LastSearchDao
}