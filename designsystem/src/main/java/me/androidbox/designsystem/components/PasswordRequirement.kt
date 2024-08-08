package me.androidbox.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.designsystem.ui.theme.BusbyDarkRed
import me.androidbox.designsystem.ui.theme.BusbyGreen
import me.androidbox.designsystem.ui.theme.CheckIcon
import me.androidbox.designsystem.ui.theme.CrossIcon
import me.androidbox.designsystem.ui.theme.LoginAppTheme

@Composable
fun PasswordRequirement(
    isValid: Boolean,
    text: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) BusbyGreen else BusbyDarkRed
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPasswordMetric() {
    LoginAppTheme() {
        PasswordRequirement(
            isValid = true,
            text = "Password should be at least 9 characters",
            modifier = Modifier
        )
    }
}
