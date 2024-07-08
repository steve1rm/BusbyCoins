package me.androidbox.presentation.coin_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import co.touchlab.kermit.Logger
import me.androidbox.domain.coin_list.models.CoinModel
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    coinList: LazyPagingItems<CoinModel>,
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

            items(
                count = coinList.itemCount) { index ->
                coinList[index]?.let {
                    Text(text = it.name, fontSize = 16.sp, color = Color.White)
                    Logger.d {
                        it.name
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewCoinListScreen() {
   BusbyCoinsTheme {
    //   CoinListScreen()
   }
}