package ir.afagh.dictionary.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ir.afagh.dictionary.data.db.relations.EnglishWithDefinition

data class DefineState(
    var definition: EnglishWithDefinition = EnglishWithDefinition(),
    var persianWord:String? = " ",
    var search: MutableState<String> = mutableStateOf("")
)