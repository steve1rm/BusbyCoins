package me.androidbox.presentation.coin_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.androidbox.presentation.coin_list.CoinListState
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinDetailHeader(
    coinListState: CoinListState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
       KamelImage(
            modifier = Modifier.size(40.dp),
            resource = asyncPainterResource(
                data = coinListState.imageUri
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            onLoading = {
                CircularProgressIndicator()
            }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Row {
                Text(text = coinListState.name)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "(${coinListState.symbol})")
            }
            Row {
                Text(text = "price".uppercase())
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = coinListState.price)
            }

            Row {
                Text(text = "market cap".uppercase())
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = coinListState.marketCap)
            }
        }
    }
}

@Composable
@Preview
fun PreviewCoinDetailHeader() {
    BusbyCoinsTheme {
        CoinDetailHeader(
            coinListState = CoinListState(
                imageUri = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                name = "Bitcoin",
                symbol = "BTC",
                price = "54,6494.838",
                marketCap = "$2.07 trillion"
            )
        )
    }
}