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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.androidbox.presentation.R
import me.androidbox.presentation.coin_list.CoinListState
import me.androidbox.designsystem.ui.theme.BusbyCoinsTheme
import me.androidbox.presentation.utils.appendWithSuffix
import me.androidbox.presentation.utils.parseColor
import me.androidbox.presentation.utils.toFormattedPrice

@Composable
fun CoinDetailContent(
    coinListState: CoinListState,
    onOpenWebsiteClicked: (webUrl: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 24.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            if(!coinListState.hasError) {
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

                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Row {
                            Text(
                                text = coinListState.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = parseColor(coinListState.color)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "(${coinListState.symbol})",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Row {
                            Text(
                                text = "price".uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "$${coinListState.price.toFormattedPrice(decimalPlaces = 2)}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Row {
                            Text(
                                text = "market cap".uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "$${coinListState.marketCap.appendWithSuffix()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = coinListState.description.trim().ifBlank { stringResource(R.string.no_description) })
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        if(coinListState.websiteUrl.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onOpenWebsiteClicked(coinListState.websiteUrl)
                    },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = "go to website".uppercase(),
                color = Color(0xFF38A0FF)
            )
        }
    }
}

// light background: #EEEEEE;

// dark background: #555555;

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