package me.androidbox.busbycoins.navigation

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import me.androidbox.presentation.coin_list.CoinListScreen
import me.androidbox.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

data object CoinListScreenRoute : Screen {

    @Composable
    override fun Content() {
        val coinListViewModel = koinViewModel<CoinListViewModel>()
        val paging = coinListViewModel.coinList.collectAsLazyPagingItems()
        val coinListState = coinListViewModel.coinDetailState

        CoinListScreen(
            coinList = paging,
            coinListState = coinListState,
            onCoinListAction = coinListViewModel::coinListAction
        )
    }
}