package me.androidbox.busbycoins.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import me.androidbox.presentation.coin_list.CoinListScreen
import me.androidbox.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

data object CoinListScreenRoute : Screen {

    @Composable
    override fun Content() {
        val coinListViewModel = koinViewModel<CoinListViewModel>()

        coinListViewModel.fetchCoinList()

        CoinListScreen()
    }
}