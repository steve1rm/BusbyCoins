package me.androidbox.busbycoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.data.coin_list.remote_data_source.imp.CoinListRemoteDataSourceImp
import me.androidbox.data.network_client.HttpKtorClient
import me.androidbox.domain.utils.CheckResult
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val coinListRemoteDataSourceImp = koinInject<CoinListRemoteDataSource>()

            lifecycleScope.launch {
                val result = coinListRemoteDataSourceImp.fetchCoinList()
                when(result) {
                    is CheckResult.Failure -> println(result.responseError)
                    is CheckResult.Success -> {
                        println(result.data)
                    }
                }
            }


            BusbyCoinsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BusbyCoinsTheme {
        Greeting("Android")
    }
}