package ir.afagh.dictionary.ui.intro

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import ir.afagh.dictionary.R
import ir.afagh.dictionary.common.Loader
import ir.afagh.dictionary.common.getVersionName
import ir.afagh.dictionary.common.utilFont
import ir.afagh.dictionary.ui.component.DicCard
import ir.afagh.dictionary.ui.navigation.NavRoute
import ir.afagh.dictionary.ui.theme.DicTheme
import kotlinx.coroutines.delay

@Composable
fun Intro(navController: NavController) {
    val context = LocalContext.current
    val scale = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate(NavRoute.WordRoute.route) {
            popUpTo(NavRoute.IntroRoute.route) { inclusive = true }
            launchSingleTop = true
        }
    }
    DicTheme() {
        DicCard {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (version, appName, loader) = createRefs()
                Loader(R.raw.dic_anim, loader)
                Text(
                    modifier = Modifier
                        .constrainAs(version) {
                            bottom.linkTo(parent.bottom, 5.dp)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                    text = context.getVersionName(),
                    fontFamily = utilFont,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.constrainAs(appName) {
                        top.linkTo(loader.bottom, 5.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    text = stringResource(id = R.string.app_name), fontFamily = utilFont,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}