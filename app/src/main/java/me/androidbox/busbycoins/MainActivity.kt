package me.androidbox.busbycoins

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import me.androidbox.busbycoins.navigation.CoinListScreenRoute
import me.androidbox.data.coin_list.remote_data_source.CoinListRemoteDataSource
import me.androidbox.domain.utils.CheckResult
import me.androidbox.presentation.ui.theme.BusbyCoinsTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

          /*  val coinListRemoteDataSourceImp = koinInject<CoinListRemoteDataSource>()

            lifecycleScope.launch {
                val result = coinListRemoteDataSourceImp.fetchCoinDetail(uuid = "Qwsogvtv82FCd")
                when(result) {
                    is CheckResult.Failure -> println(result.responseError)
                    is CheckResult.Success -> {
                        println(result.data)
                    }
                }
            }*/

            BusbyCoinsTheme {
               Navigator(screen = CoinListScreenRoute)
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