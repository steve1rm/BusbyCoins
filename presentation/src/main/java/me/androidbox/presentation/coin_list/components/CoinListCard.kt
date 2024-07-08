package me.androidbox.presentation.coin_list.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.presentation.R
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListCard(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(size = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(82.dp)
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.bitcoin),
                    contentDescription = null
                )

                Spacer(modifier.width(16.dp))

                Column {
                    Text(text = "BitCoin")

                    Text(text = "BTC")
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(text = "$56,671.41180")

                Row {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                    Text(text = "1.07")
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewCoinListCard() {
    BusbyCoinsTheme {
        CoinListCard()
    }
}