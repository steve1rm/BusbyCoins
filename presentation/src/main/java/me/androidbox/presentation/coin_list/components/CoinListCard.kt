package me.androidbox.presentation.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import me.androidbox.presentation.R
import me.androidbox.presentation.coin_list.CoinListState
import me.androidbox.designsystem.ui.theme.BusbyCoinsTheme
import me.androidbox.presentation.utils.toFormattedPrice

@Composable
fun CoinListCard(
    coinListState: CoinListState,
    modifier: Modifier = Modifier,
    onCardClicked: (uuid: String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(82.dp)
            .clickable {
                onCardClicked(coinListState.uuid)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                KamelImage(
                    modifier = Modifier.size(40.dp),
                    resource = asyncPainterResource(
                        data = coinListState.imageUri
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    onLoading = {
                        CircularProgressIndicator(
                            color = Color.Blue
                        )
                    }
                )

                Spacer(modifier.width(16.dp))

                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = coinListState.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Text(
                        text = coinListState.symbol,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$${coinListState.price.toFormattedPrice()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = if(coinListState.change.isChangeUp()) { painterResource(R.drawable.arrow_up) } else { painterResource(R.drawable.arrow_down) },
                        contentDescription = null,
                        tint = if(coinListState.change.isChangeUp()) { Color(0xFF13BC24) } else { Color(0xFFF82D2D)}
                    )
                    Text(
                        text = coinListState.change.replaceFirst("-", ""),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if(coinListState.change.isChangeUp()) { Color(0xFF13BC24) } else { Color(0xFFF82D2D)}
                    )
                }
            }
        }
    }
}

private fun String.isChangeUp(): Boolean {
    return this.toDoubleOrNull()?.let { value ->
        value > 0
    } ?: false
}

@Composable
@Preview
fun PreviewCoinListCard() {
    BusbyCoinsTheme {
        CoinListCard(
            coinListState = CoinListState(
                "",
                "Bitcoin",
                "BIT",
                "45.5869",
                "-5.0d"
            ),
            onCardClicked = {}
        )
    }
}