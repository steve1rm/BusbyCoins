package me.androidbox.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.androidbox.presentation.coin_list.CoinListState
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinDetailContent(
    coinListState: CoinListState,
    onOpenWebsiteClicked: (webUrl: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            KamelImage(
                modifier = Modifier.size(50.dp),
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = coinListState.description)

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    onOpenWebsiteClicked(coinListState.websiteUrl)
                },
            textAlign = TextAlign.Center,
            text = "go to website".uppercase())
    }
}

@Composable
@Preview
fun PreviewCoinDetailHeader() {
    BusbyCoinsTheme {
        CoinDetailContent(
            coinListState = CoinListState(
                imageUri = "https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg",
                name = "Bitcoin",
                symbol = "BTC",
                price = "54,6494.838",
                marketCap = "$2.07 trillion",
                description = "Bitcoin is a digital currency with a finite supply, allowing users to send/receive money without a central bank/government, often nicknamed \"Digital Gold\"."
            ),
            onOpenWebsiteClicked = {}
        )
    }
}