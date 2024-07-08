package me.androidbox.presentation.coin_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = Modifier
    ) {  paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {

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