package me.androidbox.presentation.coin_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {  paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            item {
                Text(text = "Buy, sell and hold crypto")
            }
        }
    }
}

@Composable
@Preview
fun PreviewCoinListScreen() {
   BusbyCoinsTheme {
       CoinListScreen()
   }
}