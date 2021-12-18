package ir.afagh.dictionary.ui.words

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import ir.afagh.dictionary.data.db.entity.EnglishEntity
import ir.afagh.dictionary.data.db.entity.LastSearchEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WordsState(
    var words: Flow<PagingData<EnglishEntity>> = emptyFlow(),
    var listHistory: List<LastSearchEntity> = emptyList(),
    var search: MutableState<String> = mutableStateOf("")
)