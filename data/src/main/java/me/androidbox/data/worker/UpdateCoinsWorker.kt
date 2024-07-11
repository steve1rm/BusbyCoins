package me.androidbox.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import me.androidbox.domain.repository.CoinListRepository
import me.androidbox.domain.utils.CheckResult
import me.androidbox.domain.utils.DataError

class UpdateCoinsWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinListRepository: CoinListRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if(this.runAttemptCount >= 5) {
            return Result.failure()
        }

        return when(val checkResult = coinListRepository.fetchCoinList()) {
            is CheckResult.Failure -> {
                when(checkResult.exceptionError) {
                    DataError.Local.DISK_FULL -> Result.failure()
                    DataError.Network.REQUEST_TIMEOUT -> Result.retry()
                    DataError.Network.UNAUTHORIZED -> Result.retry()
                    DataError.Network.TOO_MANY_REQUESTS -> Result.retry()
                    DataError.Network.NO_INTERNET -> Result.retry()
                    DataError.Network.PAYLOAD_TOO_LARGE -> Result.failure()
                    DataError.Network.SERVER_ERROR -> Result.retry()
                    DataError.Network.SERIALIZATION -> Result.failure()
                    DataError.Network.CONFLICT -> Result.failure()
                    DataError.Network.UNKNOWN -> Result.failure()
                    DataError.Network.NOTHING -> Result.failure()
                }
            }
            is CheckResult.Success -> Result.success()
        }
    }
}