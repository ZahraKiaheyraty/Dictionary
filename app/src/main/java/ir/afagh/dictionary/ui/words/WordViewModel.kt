package ir.afagh.dictionary.ui.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.afagh.dictionary.data.interactor.lastSearch.LastSearchRepository
import ir.afagh.dictionary.data.interactor.word.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(val rep: WordRepository,    val rep2: LastSearchRepository) : ViewModel() {

    private val stateWords = MutableStateFlow(WordsState())
    val _stateWords = stateWords.asStateFlow()
    init {
        getListHistory()
    }

    fun searchPersian(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stateWords.value = stateWords.value.copy(words = rep.searchPersianWords(value))
        }
    }

    fun searchEnglish(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            stateWords.value = stateWords.value.copy(words = rep.searchEnglishWords(value))
        }
    }

    fun deleteHistory(){
        viewModelScope.launch {
            rep2.deleteHistory()
        }
    }

    private fun getListHistory(){
        viewModelScope.launch {
            rep2.getAllLastSearchWord().collect {
                stateWords.value=stateWords.value.copy(listHistory = it)
            }
        }
    }
}