package me.androidbox.presentation.coin_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import me.androidbox.presentation.coin_list.components.CoinListCard
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    coinList: LazyPagingItems<CoinListState>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) {  paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = paddingValues
        ) {
            item {
                Text(text = "Buy, sell and hold crypto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground)
            }

            items(
                key = {
                    it
                },
                count = coinList.itemCount) { index ->
                coinList[index]?.let { coinListState ->
                    CoinListCard(coinListState = coinListState)
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