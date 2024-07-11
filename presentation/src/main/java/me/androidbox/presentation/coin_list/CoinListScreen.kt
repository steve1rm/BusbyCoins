@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.presentation.coin_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import co.touchlab.kermit.Logger
import me.androidbox.presentation.coin_list.components.CoinDetailContent
import me.androidbox.presentation.coin_list.components.CoinDetailVerticalCard
import me.androidbox.presentation.coin_list.components.CoinListCard
import me.androidbox.presentation.coin_list.components.InviteFriendCard
import me.androidbox.presentation.coin_list.components.TopBarSearch
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme

@Composable
fun CoinListScreen(
    coinListPager: LazyPagingItems<CoinListState>,
    coinListState: CoinListState,
    coinTopRankedState: List<CoinListState>,
    onCoinListAction: (action: CoinListAction) -> Unit,
    onOpenWebsiteClicked: (webUrl: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    val pullToRefreshState = rememberPullToRefreshState()
    val rememberWindowInfo = rememberWindowInfo()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarSearch(
                search = text,
                onValueChange = { searchTerm ->
                    text = searchTerm
                    onCoinListAction(CoinListAction.SearchTermInput(text))
                    Logger.d {
                        "search term $text"
                    }
                },
                onCloseIconClicked = {
                    text = ""
                })
        }
    ) {  paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {

            if (rememberWindowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddingValues
                ) {

                    if(text.isBlank()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                text = "Top 3 rank crypto",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center) {
                                if(coinListState.isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.Blue
                                    )
                                }
                                else {
                                    LazyRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        items(
                                            items = coinTopRankedState
                                        ) { coin ->
                                            CoinDetailVerticalCard(coin) {
                                                /** no-op */
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    item {
                        if(coinListPager.loadState.refresh is LoadState.Error) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                text = "Could not load data")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coinListPager.retry()
                                    },
                                textAlign = TextAlign.Center,
                                color = Color(0xFF38A0FF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Please try again")
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "Buy, sell and hold crypto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground)
                    }

                    /** Top progress indicator */
                    item {
                        if(coinListPager.loadState.refresh is LoadState.Loading) {
                            CircularProgressIndicator(
                                color = Color.Blue
                            )
                            if(pullToRefreshState.isRefreshing) {
                                pullToRefreshState.endRefresh()
                            }
                        }
                    }

                    items(
                        key = { coinListPager ->
                            coinListPager
                        },
                        count = coinListPager.itemCount) { index ->

                        /** Insert the invite friend here by using multiples i.e. 5, 10,  20, 40, 80, 160 */
                        if(isInvitePosition(index + 1)) {
                            InviteFriendCard { webUrl ->
                                onOpenWebsiteClicked(webUrl)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        coinListPager[index]?.let { coinListState ->
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

                    item {
                        if(coinListPager.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                color = Color.Blue
                            )
                        }
                    }

                    item {
                        if(coinListPager.loadState.append is LoadState.Error) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                text = "Could not load data")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coinListPager.retry()
                                    },
                                textAlign = TextAlign.Center,
                                color = Color(0xFF38A0FF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Please try again")
                        }
                    }
                }
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = paddingValues
                ) {

                    item {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Top 3 rank crypto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground)

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                        ) {
                            items(
                                items = coinTopRankedState
                            ) { coin ->
                                CoinDetailVerticalCard(coin) {
                                    /** no-op */
                                }
                            }
                        }
                    }

                    /** Top progress indicator */
                    item {
                        if(coinListPager.loadState.refresh is LoadState.Loading) {
                            CircularProgressIndicator(
                                color = Color.Blue
                            )
                            if(pullToRefreshState.isRefreshing) {
                                pullToRefreshState.endRefresh()
                            }
                        }
                    }

                    item {
                        if(coinListPager.loadState.refresh is LoadState.Error) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                text = "Could not load data")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coinListPager.retry()
                                    },
                                textAlign = TextAlign.Center,
                                color = Color(0xFF38A0FF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Please try again")
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Buy, sell and hold crypto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground)
                    }

                    item {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .height(500.dp)
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            columns = GridCells.Fixed(3),
                            contentPadding = paddingValues
                        ) {
                           /** Not the best way to insert a different item as part of the grid */
                            val combinedList = mutableListOf<Any?>()
                            for (i in 0 until coinListPager.itemCount) {
                                if (isInvitePosition(i + 1)) {
                                    combinedList.add("InviteFriendCard")
                                }
                                combinedList.add(coinListPager[i])
                            }

                            items(combinedList.size) { index ->
                                val item = combinedList[index]
                                when (item) {
                                    is String -> {
                                        InviteFriendCard { webUrl ->
                                            onOpenWebsiteClicked(webUrl)
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    else -> {
                                        CoinListCard(
                                            coinListState = item as CoinListState,
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
                        }
                    }

                    item {
                        if(coinListPager.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                color = Color.Blue
                            )
                        }
                    }

                    item {
                        if(coinListPager.loadState.append is LoadState.Error) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                text = "Could not load data")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coinListPager.retry()
                                    },
                                textAlign = TextAlign.Center,
                                color = Color(0xFF38A0FF),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Please try again")
                        }
                    }
                }
            }

            if(pullToRefreshState.isRefreshing) {
                coinListPager.refresh()
            }

            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    if(isBottomSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                isBottomSheetOpen = false
            },
            dragHandle = null,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                if(coinListState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.Blue,
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

fun isInvitePosition(index: Int): Boolean {
    if (index < 5) return false
    val n = (index / 5)
    return index % 5 == 0 && (n and (n - 1)) == 0
}

@Composable
@Preview
fun PreviewCoinListScreen() {
    BusbyCoinsTheme {
        // CoinListScreen()
    }
}
