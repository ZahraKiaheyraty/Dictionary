package ir.afagh.dictionary.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "dbo_translate_words")
data class EnglishEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_persian_word")
    var id: Int,
    @ColumnInfo(name = "id_english_word")
    var idEnglishWord: Int,
    @ColumnInfo(name = "id_kind_word")
    var idKindWord: Int,
    @ColumnInfo(name = "persian_word")
    var persianWord: String,
    @ColumnInfo(name = "english_word")
    var englishWord: String,
    @ColumnInfo(name = "kind_word_english_name")
    var kindEnglishWord: String,
    @ColumnInfo(name = "kind_word_persian_name")
    var kindPersianWord: String,
) : Parcelable