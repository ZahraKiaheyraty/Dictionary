package ir.afagh.dictionary.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "dbo_definition_examples")
data class DefinitionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_definition_examples")
    var id: Int,
    @ColumnInfo(name = "id_english_word")
    var idEnglishWord: Int,
    @ColumnInfo(name = "id_kind_word")
    var idKindWord: Int,
    @ColumnInfo(name = "definition")
    var definition: String,
    @ColumnInfo(name = "example")
    var example: String
) : Parcelable