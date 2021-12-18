package ir.afagh.dictionary.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.afagh.dictionary.common.sendArgument
import ir.afagh.dictionary.data.db.entity.LastSearchEntity
import ir.afagh.dictionary.data.interactor.define.DefineRepository
import ir.afagh.dictionary.data.interactor.lastSearch.LastSearchRepository
import ir.afagh.dictionary.data.model.EnglishModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DefineViewModel @Inject constructor(
    val rep: DefineRepository,
    savedStateHandle: SavedStateHandle,
    gson: Gson,
    val rep2: LastSearchRepository
) : ViewModel() {
    val stateExample = MutableStateFlow(DefineState())
    val _stateExample = stateExample.asStateFlow()

    init {
        gson.fromJson(savedStateHandle.sendArgument("details"), EnglishModel::class.java)
            ?.let { data ->
                handleEvent(ExampleEvent.ShowExampleWord(data.idEnglishWord))
                insertToHistory(
                    LastSearchEntity(
                        id = data.idEnglishWord,
                        persian_word = data.persianWord,
                        english_word = data.englishWord
                    )
                )
                stateExample.value = stateExample.value.copy(persianWord = data.persianWord)
            }

//        gson.fromJson(savedStateHandle.sendArgument("details"), LastSearchEntity::class.java)?.let {
//            handleEvent(ExampleEvent.ShowExampleWord(it.id))
//            stateExample.value = stateExample.value.copy(persianWord = it.persian_word)
//        }
    }

    private fun insertToHistory(lastSearchHistory: LastSearchEntity) {
        viewModelScope.launch {
            rep2.insert(lastSearchHistory)
        }
    }

    private fun giveDtails() {
        viewModelScope.launch {
            rep2.getAllLastSearchWord().collect {
                Log.e("giveDtails", "giveDtails:  $it")
            }
        }
    }

    private fun handleEvent(handleEvent: ExampleEvent) {
        when (handleEvent) {
            is ExampleEvent.ShowExampleWord -> {
                defineWords(handleEvent.id)
            }
        }
    }

    private fun defineWords(id: Int) {
        viewModelScope.launch {
            rep.defineWords(id).catch {
                Log.e("TAG", "example: ${it.message}")
            }.collect {
                stateExample.value = stateExample.value.copy(definition = it)
            }
        }
    }

    sealed class ExampleEvent() {
        data class ShowExampleWord(var id: Int) : ExampleEvent()
    }
}