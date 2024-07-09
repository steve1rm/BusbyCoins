@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.presentation.coin_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import co.touchlab.kermit.Logger
import me.androidbox.presentation.coin_list.components.CoinDetailContent
import me.androidbox.presentation.coin_list.components.CoinListCard
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    coinList: LazyPagingItems<CoinListState>,
    coinListState: CoinListState,
    onCoinListAction: (action: CoinListAction) -> Unit,
    onOpenWebsiteClicked: (webUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

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
                    CoinListCard(
                        coinListState = coinListState,
                        onCardClicked = { uuid: String ->
                            isBottomSheetOpen = true
                            onCoinListAction(CoinListAction.CoinListCardClicked(uuid = uuid))
                            Logger.d {
                                "uuid: $uuid"
                            }
                        })
                }
            }
        }

        if(isBottomSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isBottomSheetOpen = false
                },
                dragHandle = null
            ) {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    if(coinListState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                        )
                    }
                    else {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CoinDetailContent(
                                coinListState = coinListState,
                                onOpenWebsiteClicked = { webSiteUrl ->
                                    onOpenWebsiteClicked(webSiteUrl)
                                    /** Do we close or leave it open to navigate back to, I think better to close */
                                    isBottomSheetOpen = false
                                }
                            )
                        }
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
      // CoinListScreen()
   }
}