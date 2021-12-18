package ir.afagh.dictionary.ui.words

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.*
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import ir.afagh.dictionary.R
import ir.afagh.dictionary.common.textPersian
import ir.afagh.dictionary.common.utilFont
import ir.afagh.dictionary.data.db.entity.EnglishEntity
import ir.afagh.dictionary.data.db.entity.LastSearchEntity
import ir.afagh.dictionary.data.model.EnglishModel
import ir.afagh.dictionary.data.model.mapEnglishEntityToEnglishModel
import ir.afagh.dictionary.data.model.mapLastSearchEntityToEnglishModel
import ir.afagh.dictionary.ui.SearchBar
import ir.afagh.dictionary.ui.component.DicCard
import ir.afagh.dictionary.ui.component.SetFlagCard
import ir.afagh.dictionary.ui.theme.*

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun WordScreen(navigateToDetailsScreen: (id: EnglishModel) -> Unit, viewModel: WordViewModel) {
    DicTheme {
        viewModel._stateWords.collectAsState().let { data ->
            var expanded by remember { mutableStateOf(false) }
            val paging = data.value.words.collectAsLazyPagingItems()
            val listHistory = data.value.listHistory
            var value by rememberSaveable { mutableStateOf("") }
            BackHandler(enabled = expanded, onBack = {
                expanded = false
            })
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Neutral0)
            ) {
                Card(
                    shape = RoundedCornerShape(40.dp),
                    backgroundColor = Violet,
                    elevation = 10.dp,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp, bottom = 80.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 80.dp),
                            text = stringResource(R.string.app_name),
                            color = Color.White,
                            fontFamily = utilFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 35.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 15.dp),
                            text = stringResource(R.string.title_second_app_name),
                            color = Color.White,
                            fontFamily = utilFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                            ,horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            SetFlagCard("English",R.drawable.en_flag)
                            SetFlagCard("Persian",R.drawable.ir_flag)
                        }

                        SearchBar(
                            query = value,
                            onQueryChange = {
                                value = it
                                if (value.textPersian()) {
                                    viewModel.searchPersian(it)
                                } else {
                                    viewModel.searchEnglish(it)
                                }
                            },
                            onSearchFocusChange = { },
                            onClearQuery = {
                                value = ""
                            },
                            enableClose = value != "",
                            downFavButton = {
                                expanded = false
                            }
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            if (value != "") {
                                items(paging) {
                                    WordItem(it!!, navigateToDetailsScreen)
                                }
                            }
                        }
                    }
                }
                val fab = createRef()
                Card(
                    onClick = { expanded = !expanded },
                    shape = RoundedCornerShape(40.dp),
                    backgroundColor = Neutral0,
                    modifier = Modifier
                        .padding(20.dp)
                        .constrainAs(fab) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    ContentWithIconAnimation(
                        expanded = expanded,
                        list = listHistory,
                        navigateToDetailsScreen,
                        deleteHistory = {
                            viewModel.deleteHistory()
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun WordItem(data: EnglishEntity, navigateToDetailsScreen: (id: EnglishModel) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                navigateToDetailsScreen(data.mapEnglishEntityToEnglishModel())
            }
            .background(Neutral0, shape = RoundedCornerShape(20.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 5.dp),
            text = data.englishWord, fontFamily = utilFont,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 8.dp),
            text = data.persianWord, fontFamily = utilFont,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            color = DicTheme.colors.textHelp,
            fontSize = 12.sp
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ContentWithIconAnimation(
    expanded: Boolean,
    list: List<LastSearchEntity>,
    navigateToDetailsWithHistory: (id: EnglishModel) -> Unit,
    deleteHistory: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    AnimatedContent(targetState = expanded, transitionSpec = {
        fadeIn(
            animationSpec = tween(
                150, 150
            )
        ) with fadeOut(animationSpec = tween(150)) using SizeTransform { initialSize, targetSize ->
            if (targetState) {
                keyframes {
                    // Expand horizontally first.
                    IntSize(targetSize.width, initialSize.height) at 150
                    durationMillis = 300
                }
            } else {
                keyframes {
                    // Shrink vertically first.
                    IntSize(initialSize.width, targetSize.height) at 150
                    durationMillis = 300
                }
            }
        }
    }) { targetExpanded ->
        if (targetExpanded) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 2)
                    .background(Neutral0)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 20.dp),
                    text = stringResource(R.string.title_history),
                    fontFamily = utilFont,
                    fontWeight = FontWeight.Medium,
                    color = Violet,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                if (list.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                deleteHistory()
                            }
                            .padding(end = 30.dp, top = 5.dp, bottom = 10.dp),
                        text = stringResource(R.string.clear_history),
                        fontFamily = utilFont,
                        fontWeight = FontWeight.Medium,
                        color = VioletHistory,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Right
                    )
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(list) {
                            HistoryItem(it, navigateToDetailsWithHistory)

                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.not_exist_history),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = screenHeight / 5f),
                        color = Violet,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Image(
                painter = painterResource(R.drawable.history),
                contentDescription = "",
                Modifier.size(50.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HistoryItem(data: LastSearchEntity, navigateToDetailsWithHistory: (id: EnglishModel) -> Unit) {
    DicCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 2.dp)
                .background(VioletHistory, shape = RoundedCornerShape(20.dp))
                .clickable {
                    data.apply {
                        navigateToDetailsWithHistory(data.mapLastSearchEntityToEnglishModel())
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 2.dp),
                text = data.english_word, fontFamily = utilFont,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, bottom = 10.dp),
                text = data.persian_word, fontFamily = utilFont,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {

                currentOnBack()
            }
        }
    }
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}