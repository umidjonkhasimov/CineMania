package uz.john.authentication.presentation.sign_in_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.john.authentication.R
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.SideEffect
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.UiAction
import uz.john.authentication.presentation.sign_in_screen.SignInScreenContract.UiState
import uz.john.authentication.presentation.sign_in_screen.viewmodel.SignInScreenViewModel
import uz.john.ui.components.AnimatedProgressIndicator
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTextField
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.theme.CineManiaTheme

private val SCREEN_PADDING = 16.dp

@Composable
fun SignInScreen(
    onBackClick: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    val viewModel: SignInScreenViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    SignInScreen(
        uiState = uiState.value,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onBackClick = onBackClick,
        onNavigateToMainScreen = onNavigateToMainScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInScreen(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var shouldShowDialog by remember { mutableStateOf(false) }
    var dialogErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect {
            when (it) {
                SideEffect.NavigateToMainScreen -> {
                    onNavigateToMainScreen()
                }

                is SideEffect.ShowErrorDialog -> {
                    shouldShowDialog = true
                    dialogErrorMessage = it.errorMessage
                }
            }
        }
    }

    if (shouldShowDialog) {
        CineManiaErrorDialog(
            errorText = dialogErrorMessage,
            onCancel = { shouldShowDialog = false },
            onRetry = { },
            onDismissRequest = { shouldShowDialog = false }
        )
    }

    Scaffold(
        topBar = {
            CineManiaTopBar(
                title = stringResource(R.string.login),
                leadingContent = {
                    CineManiaBackButton(
                        onClick = onBackClick
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = SCREEN_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.hello_movie_fan),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(64.dp))

            CineManiaTextField(
                value = usernameText,
                label = stringResource(R.string.username),
                onValueChanged = { usernameText = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CineManiaTextField(
                value = passwordText,
                label = stringResource(R.string.password),
                onValueChanged = {
                    passwordText = it
                },
                isPasswordTextField = true,
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(MaterialTheme.shapes.small)
                    .clickable {

                    }
                    .padding(4.dp),
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(64.dp))

            CineManiaButton(
                onClick = {
                    onUiAction(
                        UiAction.OnSignInClick(
                            username = usernameText,
                            password = passwordText
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
    AnimatedProgressIndicator(visible = uiState.isLoading, modifier = Modifier.fillMaxSize())
}

@Preview
@Composable
private fun SignInScreenPreview() {
    CineManiaTheme {
        SignInScreen(
            uiState = UiState(true),
            sideEffect = flow { },
            onUiAction = {},
            onBackClick = {},
            onNavigateToMainScreen = {}
        )
    }
}