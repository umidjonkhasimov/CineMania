package uz.john.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import uz.john.ui.R
import uz.john.ui.theme.CineManiaTheme

private const val BACKGROUND_ALPHA = .8f

@Composable
fun CineManiaTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    isPasswordTextField: Boolean = false,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onBackground,
        unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = BACKGROUND_ALPHA)
    ),
    onValueChanged: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    val passwordVisibilityIcon = if (isPasswordVisible) {
        painterResource(R.drawable.ic_visibility_on)
    } else {
        painterResource(R.drawable.ic_visibility_off)
    }
    val visualTransformation = if (isPasswordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    OutlinedTextField(
        value = value,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        shape = shape,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        colors = colors,
        onValueChange = onValueChanged,
        visualTransformation = if (isPasswordTextField) {
            visualTransformation
        } else {
            VisualTransformation.None
        },
        trailingIcon = if (isPasswordTextField) {
            {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
                    Icon(
                        painter = passwordVisibilityIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else null
    )
}

@Preview
@Composable
private fun CineManiaTextFieldPreview() {
    CineManiaTheme {
        CineManiaTextField(
            value = "Email",
            label = "Password",
            onValueChanged = {

            }
        )
    }
}