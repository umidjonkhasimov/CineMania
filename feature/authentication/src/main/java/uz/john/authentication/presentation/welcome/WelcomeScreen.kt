package uz.john.authentication.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.john.authentication.R
import uz.john.ui.components.CineManiaAlertDialog
import uz.john.ui.components.CineManiaButton
import uz.john.ui.theme.CineManiaTheme

private val LOGO_SIZE = 200.dp

@Composable
internal fun WelcomeScreen(
    onSignUpClick: () -> Unit,
    onContinueWithoutAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val alertDialogTitleRes = stringResource(R.string.attention)
    val alertDialogTextRes = stringResource(R.string.you_will_be_redirected)
    var shouldShowAlertDialog by remember { mutableStateOf(false) }
    val alertDialogTitle by remember { mutableStateOf(alertDialogTitleRes) }
    val alertDialogText by remember { mutableStateOf(alertDialogTextRes) }

    if (shouldShowAlertDialog) {
        CineManiaAlertDialog(
            titleText = alertDialogTitle,
            alertText = alertDialogText,
            onCancel = { shouldShowAlertDialog = false },
            onConfirm = onSignUpClick,
            onDismissRequest = { shouldShowAlertDialog = false }
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(LOGO_SIZE),
                painter = painterResource(R.drawable.cinemania_logo),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.enter_email),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(56.dp))

            CineManiaButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                onClick = { shouldShowAlertDialog = true }
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CineManiaButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                onClick = onContinueWithoutAccountClick
            ) {
                Text(
                    text = stringResource(R.string.continue_without_account),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Text(
                    text = stringResource(R.string.already_have),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            onLoginClick()
                        }
                        .padding(horizontal = 4.dp),
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
                )

                Text(
                    text = stringResource(R.string.sign_up_with),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = null,
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_apple),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .size(56.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_facebook),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    CineManiaTheme {
        WelcomeScreen(
            onLoginClick = {},
            onSignUpClick = {},
            onContinueWithoutAccountClick = {}
        )
    }
}