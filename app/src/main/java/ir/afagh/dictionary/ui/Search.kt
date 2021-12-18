package ir.afagh.dictionary.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ir.afagh.dictionary.R
import androidx.compose.ui.unit.dp
import ir.afagh.dictionary.common.clearFocusOnKeyboardDismiss
import ir.afagh.dictionary.common.mirroringCancelIcon
import ir.afagh.dictionary.ui.component.DicSurface
import ir.afagh.dictionary.ui.theme.DicTheme
import ir.afagh.dictionary.ui.theme.Neutral0
import ir.afagh.dictionary.ui.theme.Violet

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    onSearchFocusChange: (Boolean) -> Unit,
    enableClose: Boolean,
    modifier: Modifier = Modifier,
    downFavButton: () -> Unit
) {
    val ctx = LocalContext.current
    DicSurface(
        shape = RoundedCornerShape(20.dp),
        color = DicTheme.colors.uiFloated,
        contentColor = DicTheme.colors.textSecondary,
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.isEmpty()) {
                SearchHint()
            }
            Row(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AnimatedVisibility(visible = enableClose) {
                    IconButton(modifier = Modifier.padding(), onClick = onClearQuery) {
                        Icon(
                            imageVector = mirroringCancelIcon(),
                            tint = DicTheme.colors.iconPrimary,
                            contentDescription = stringResource(R.string.label_back)
                        )
                    }
                }

                val keyboardController = LocalSoftwareKeyboardController.current
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .clearFocusOnKeyboardDismiss()
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                            downFavButton.invoke()
                                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        },
                    )
                )
            }
        }
    }
}

@Composable
private fun SearchHint() {
    Row(
        modifier = Modifier
            .padding(start = 10.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(R.string.label_search),
            tint = Violet,
            modifier = Modifier
                .height(30.dp)
                .background(color = Neutral0, shape = RoundedCornerShape(5.dp))
        )

        Spacer(Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.search_word),
            color = DicTheme.colors.textHelp
        )
    }
}
//@ExperimentalComposeUiApi
//@ExperimentalAnimationApi
//@Preview("default")
//@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 1.5f)
//@Composable
//private fun SearchBarPreview() {
//    DicTheme {
//        DicSurface {
//            SearchBar(
//                query = "",
//                onQueryChange = { },
//                onSearchFocusChange = { },
//                onClearQuery = { },
//                enableClose = false,
//
//            )
//        }
//    }
//}