package uz.john.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.john.ui.components.CineManiaButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()
    val onBoardingImages = listOf(
        R.drawable.onboarding_page_1,
        R.drawable.onboarding_page_2,
        R.drawable.onboarding_page_3,
        R.drawable.onboarding_page_4,
    )

    val onBoardingTitles = listOf(
        R.string.onboarding_text_title_page_1,
        R.string.onboarding_text_title_page_2,
        R.string.onboarding_text_title_page_3,
        R.string.onboarding_text_title_page_4
    )

    val onBoardingSubtitles = listOf(
        R.string.onboarding_text_subtitle_page_1,
        R.string.onboarding_text_subtitle_page_2,
        R.string.onboarding_text_subtitle_page_3,
        R.string.onboarding_text_subtitle_page_4
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageImagesDrawable = onBoardingImages
        )

        ControlBox(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            pagerState = pagerState,
            scope = scope,
            titles = onBoardingTitles,
            subtitles = onBoardingSubtitles,
            onLoginClick = onLoginClick,
            onContinueClick = onContinueClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageImagesDrawable: List<Int>
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = pageImagesDrawable[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ControlBox(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    scope: CoroutineScope,
    titles: List<Int>,
    subtitles: List<Int>,
    onLoginClick: () -> Unit,
    onContinueClick: () -> Unit,

    ) {
    val isLastPage = pagerState.currentPage + 1 == pagerState.pageCount

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .height(350.dp)
            .fillMaxWidth(.9f)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .9f))
            .padding(24.dp),
    ) {
        AnimatedContent(
            targetState = pagerState.currentPage,
            label = "contentAnimation"
        ) { targetState ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = titles[targetState]),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = subtitles[targetState]),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f),
                    textAlign = TextAlign.Center
                )
            }
        }

        AnimatedContent(
            modifier = Modifier.align(Alignment.BottomCenter),
            targetState = isLastPage,
            label = "",
            transitionSpec = {
                (slideInVertically { height ->
                    height
                } + fadeIn()).togetherWith(
                    slideOutVertically { height ->
                        -height
                    } + fadeOut()).using(
                    SizeTransform(clip = false)
                )
            }
        ) { isLastPage ->
            if (isLastPage) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CineManiaButton(
                        onClick = onLoginClick
                    ) {
                        Text(
                            text = stringResource(R.string.login),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    CineManiaButton(
                        onClick = onContinueClick
                    ) {
                        Text(
                            text = stringResource(R.string.continue_without_account),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DotIndicators(
                        pagerState = pagerState
                    )
                    NextButton(
                        pagerState = pagerState
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(500)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotIndicators(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    animationDuration: Int = 300,
    dotSize: Dp = 10.dp,
    expandedDotSize: Dp = 26.dp,
    spaceBetweenDots: Dp = 12.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { selectedPage ->
            val isSelected = pagerState.currentPage == selectedPage
            val animatedWidth = animateDpAsState(
                targetValue = if (isSelected) expandedDotSize else dotSize,
                label = "animatedWidth",
                animationSpec = tween(animationDuration)
            )

            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .width(animatedWidth.value)
                    .height(dotSize)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.primary.copy(alpha = .3f)
                    )
            )
            if (selectedPage != pagerState.pageCount - 1)
                Spacer(modifier = Modifier.width(spaceBetweenDots))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    animationDuration: Int = 300,
    buttonSize: Dp = 64.dp,
    circleWidth: Dp = 3.dp,
    progressWidth: Dp = 4.dp,
    onClick: () -> Unit
) {
    val progress = (pagerState.currentPage + 1).toFloat() / pagerState.pageCount

    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        label = "arcAnimation",
        animationSpec = tween(animationDuration)
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val onBackground = MaterialTheme.colorScheme.onBackground

    Box(
        modifier = modifier.size(buttonSize),
        contentAlignment = Alignment.Center
    ) {
        // Draw the progress circle
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = onBackground.copy(alpha = .05f),
                style = Stroke(width = circleWidth.toPx())
            )
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = animatedProgress.value * 360,
                useCenter = false,
                style = Stroke(
                    width = progressWidth.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }

        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(primaryColor)
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = onPrimaryColor
            )
        }
    }
}

@Preview(device = "id:Nexus One")
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        onLoginClick = {},
        onContinueClick = {}
    )
}