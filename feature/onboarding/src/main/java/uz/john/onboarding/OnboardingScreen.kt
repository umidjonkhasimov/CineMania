package uz.john.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
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
import uz.john.ui.components.DotsIndicator

private const val ONBOARDING_PAGE_COUNT = 4
private const val ANIMATION_DURATION = 300
private const val SCROLL_ANIMATION_DURATION = 500
private val CONTROL_BOX_PADDING = 24.dp
private val CONTROL_BOX_HEIGHT = 350.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun OnboardingScreen(
    onStartClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })
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
                .padding(bottom = CONTROL_BOX_PADDING),
            pagerState = pagerState,
            scope = scope,
            titles = onBoardingTitles,
            subtitles = onBoardingSubtitles,
            onStartClick = onStartClick
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
    onStartClick: () -> Unit
) {
    val isLastPage = pagerState.currentPage + 1 == pagerState.pageCount

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .height(CONTROL_BOX_HEIGHT)
            .fillMaxWidth(.9f)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .9f))
            .padding(CONTROL_BOX_PADDING),
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
                        onClick = onStartClick
                    ) {
                        Text(
                            text = stringResource(R.string.start),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
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
                    DotsIndicator(
                        pagerState = pagerState
                    )
                    NextButton(
                        pagerState = pagerState
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1,
                                animationSpec = tween(SCROLL_ANIMATION_DURATION)
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
fun NextButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    animationDuration: Int = ANIMATION_DURATION,
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
        onStartClick = {},
    )
}