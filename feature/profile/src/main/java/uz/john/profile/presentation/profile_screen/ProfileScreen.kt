package uz.john.profile.presentation.profile_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.Language
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.UiMode
import uz.john.domain.model.UserPreferences
import uz.john.domain.model.profile.UserAccountDetails
import uz.john.profile.R
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.SideEffect
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.UiAction
import uz.john.profile.presentation.profile_screen.ProfileScreenContract.UiState
import uz.john.ui.components.CineManiaAlertDialog
import uz.john.ui.components.CineManiaDialog
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CoilImage
import uz.john.ui.theme.CineManiaIcons

private val SCREEN_PADDING = 16.dp
private val SPACE_HEIGHT = 32.dp
private val ITEM_HEIGHT = 56.dp
private val IMAGE_SIZE = 100.dp

@Composable
fun ProfileScreen(
    onSignInClick: () -> Unit
) {
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreenScreenContent(
        uiState = uiState,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onSignInClick = onSignInClick
    )
}

@Composable
fun ProfileScreenScreenContent(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onSignInClick: () -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect {
            when (it) {
                is SideEffect.ShowErrorDialog -> {
                    shouldShowErrorDialog = true
                    errorMessage = it.errorMessage
                }
            }
        }
    }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorMessage,
            onCancel = {
                shouldShowErrorDialog = false
            },
            onRetry = {
                shouldShowErrorDialog = false
                onUiAction(UiAction.GetAccountDetails)
            },
            onDismissRequest = {
                shouldShowErrorDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        when (uiState.isLoading) {
            true -> {
                ProfileScreenShimmerEffect(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            false -> {
                uiState.userPreferences?.let {
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(state = rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

                        AccountDetails(
                            userPreferences = uiState.userPreferences,
                            accountDetails = uiState.accountDetails,
                            onSignInClick = onSignInClick
                        )

                        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

                        Settings(
                            uiState = uiState,
                            onUiAction = onUiAction,
                            modifier = Modifier.padding(SCREEN_PADDING)
                        )

                        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

                        Others(
                            onGithubClick = { openGithubPage(context) },
                            modifier = Modifier.padding(SCREEN_PADDING)
                        )

                        Spacer(modifier = Modifier.height(SPACE_HEIGHT))

                        if (uiState.userPreferences.isLoggedIn) {
                            SignOutButton(
                                onSignOutClick = {
                                    onUiAction(UiAction.SignOut)
                                },
                                modifier = Modifier.padding(SCREEN_PADDING)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountDetails(
    userPreferences: UserPreferences?,
    accountDetails: UserAccountDetails?,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (userPreferences == null || !userPreferences.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SCREEN_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier
                    .size(IMAGE_SIZE)
                    .clip(MaterialTheme.shapes.extraLarge),
                imagePath = null,
                imageSize = NetworkImageSizes.SMALL
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.sign_in_to_get_account_details),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSignInClick
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
        }
    } else {
        accountDetails?.let {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(SCREEN_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    modifier = Modifier
                        .size(IMAGE_SIZE)
                        .clip(MaterialTheme.shapes.extraLarge),
                    imagePath = accountDetails.avatar.tmdbAvatar.avatarPath,
                    imageSize = NetworkImageSizes.MEDIUM
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = accountDetails.username,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun Settings(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    uiState.userPreferences?.let {
        var shouldShowLanguageDialog by remember { mutableStateOf(false) }
        var shouldShowUiModeDialog by remember { mutableStateOf(false) }
        val onCheckedChanged: () -> Unit = {
            onUiAction(UiAction.SetIncludeAdult(!uiState.userPreferences.includeAdult))
        }

        if (shouldShowUiModeDialog) {
            CineManiaDialog(
                onDismissRequest = {
                    shouldShowUiModeDialog = false
                }
            ) {
                Column {
                    UiMode.entries.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onUiAction(UiAction.SetUiMode(it))
                                }
                                .padding(horizontal = SCREEN_PADDING, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = when (it) {
                                    UiMode.SYSTEM -> stringResource(R.string.system)
                                    UiMode.LIGHT -> stringResource(R.string.light)
                                    UiMode.DARK -> stringResource(R.string.dark)
                                }
                            )
                            RadioButton(
                                selected = uiState.userPreferences.uiMode == it,
                                onClick = { onUiAction(UiAction.SetUiMode(it)) }
                            )
                        }
                    }
                }
            }
        }

        if (shouldShowLanguageDialog) {
            CineManiaDialog(
                onDismissRequest = {
                    shouldShowLanguageDialog = false
                }
            ) {
                Column {
                    Language.entries.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onUiAction(UiAction.SetLanguage(it))
                                }
                                .padding(horizontal = SCREEN_PADDING, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it.languageName)
                            RadioButton(
                                selected = uiState.userPreferences.language == it,
                                onClick = {
                                    onUiAction(UiAction.SetLanguage(it))
                                }
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ITEM_HEIGHT)
                        .clickable {
                            shouldShowUiModeDialog = true
                        }
                        .padding(horizontal = SCREEN_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.ui_mode),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.alpha(.6f),
                            text = when (uiState.userPreferences.uiMode) {
                                UiMode.SYSTEM -> stringResource(R.string.system)
                                UiMode.LIGHT -> stringResource(R.string.light)
                                UiMode.DARK -> stringResource(R.string.dark)
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            modifier = Modifier.alpha(.5f),
                            painter = painterResource(CineManiaIcons.RightArrow),
                            contentDescription = null
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.background
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            shouldShowLanguageDialog = true
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.language),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.alpha(.6f),
                            text = uiState.userPreferences.language.languageName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            modifier = Modifier.alpha(.5f),
                            painter = painterResource(CineManiaIcons.RightArrow),
                            contentDescription = null
                        )
                    }
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.background
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ITEM_HEIGHT)
                        .clickable {
                            onCheckedChanged()
                        }
                        .padding(horizontal = SCREEN_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.include_adult_content),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        checked = uiState.userPreferences.includeAdult,
                        onCheckedChange = { onCheckedChanged() }
                    )
                }
            }
        }
    }
}

@Composable
fun Others(
    onGithubClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowDeveloperContactDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (shouldShowDeveloperContactDialog) {
        CineManiaDialog(
            onDismissRequest = {
                shouldShowDeveloperContactDialog = false
            }
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.contact_us),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = rememberRipple(bounded = false),
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                emailDeveloper(context = context)
                            },
                        painter = painterResource(CineManiaIcons.GoogleLogo),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = rememberRipple(bounded = false),
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                openDevelopersTelegram(context = context)
                            },
                        painter = painterResource(CineManiaIcons.Telegram),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(
                                indication = rememberRipple(bounded = false),
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                openDevelopersInstagram(context = context)
                            },
                        painter = painterResource(CineManiaIcons.Instagram),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.others),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ITEM_HEIGHT)
                    .clickable {
                        onGithubClick()
                    }
                    .padding(horizontal = SCREEN_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(CineManiaIcons.GitHubIcon),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.github_page),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.background
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ITEM_HEIGHT)
                    .clickable {
                        shouldShowDeveloperContactDialog = true
                    }
                    .padding(horizontal = SCREEN_PADDING),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(CineManiaIcons.Contact),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.developer_contact),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun SignOutButton(
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowSignOutDialog by remember { mutableStateOf(false) }

    if (shouldShowSignOutDialog) {
        CineManiaAlertDialog(
            titleText = stringResource(R.string.sign_out),
            alertText = stringResource(R.string.do_you_want_to_sign_out),
            onCancel = { shouldShowSignOutDialog = false },
            onConfirm = { onSignOutClick() },
            onDismissRequest = { shouldShowSignOutDialog = false }
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .height(ITEM_HEIGHT)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                shouldShowSignOutDialog = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.sign_out),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

fun emailDeveloper(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, "umidjon.khasimov@gmail.com")
    }
    context.startActivity(intent)
}

fun openDevelopersTelegram(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("http://t.me/umidjon_khasimov")
    }
    context.startActivity(intent)
}

fun openDevelopersInstagram(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://www.instagram.com/umidjon_khasimov?igsh=MTF5emUxMGQ4ejJ6ag==")
    }
    context.startActivity(intent)
}

private fun openGithubPage(context: Context) {
    val url = "https://github.com/umidjonkhasimov/CineMania"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}