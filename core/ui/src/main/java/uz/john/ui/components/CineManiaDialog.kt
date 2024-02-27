package uz.john.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.john.ui.R
import uz.john.ui.theme.CineManiaTheme

private const val DIALOG_INFLATION_DELAY = 300L
private val DIALOG_HEIGHT = 200.dp
private val DIALOG_PADDING = 24.dp

@Composable
fun CineManiaErrorDialog(
    errorText: String,
    onCancel: () -> Unit,
    onRetry: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            delay(DIALOG_INFLATION_DELAY)
            visible = true
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .height(DIALOG_HEIGHT)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(DIALOG_PADDING),
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.error),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = errorText,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Button(
                        onClick = onCancel,
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onRetry,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(R.string.retry),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CineManiaAlertDialog(
    titleText: String,
    alertText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            delay(DIALOG_INFLATION_DELAY)
            visible = true
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .height(DIALOG_HEIGHT)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(DIALOG_PADDING),
            ) {
                Column {
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = alertText,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Button(
                        onClick = onCancel,
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = onConfirm,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = stringResource(R.string.confirm),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun CineManiaErrorDialogPreview() {
    CineManiaTheme {
        CineManiaErrorDialog(
            errorText = "Error has been occurred!",
            onCancel = {},
            onRetry = {},
            onDismissRequest = {}
        )
    }
}