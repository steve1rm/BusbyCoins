package me.androidbox.data.scheduler

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.androidbox.data.worker.UpdateCoinsWorker
import me.androidbox.domain.scheduler.SyncUpdateCoinsScheduler
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

class SyncUpdateCoinsSchedulerImp(
    private val context: Context
) : SyncUpdateCoinsScheduler {

    private companion object {
        const val WORKER_TAG = "sync_update"
    }

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleUpdate() {
        scheduleUpdateCoinsWorker(10.milliseconds)
    }

    override suspend fun cancelSync() {
        WorkManager.getInstance(context)
            .cancelAllWork()
            .await()
    }

    private suspend fun scheduleUpdateCoinsWorker(interval: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag(WORKER_TAG)
                .get()
                .isNotEmpty()
        }

        if(isSyncScheduled) {
            val workRequest = PeriodicWorkRequestBuilder<UpdateCoinsWorker>(
                repeatInterval = interval.toJavaDuration()
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                .setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.EXPONENTIAL,
                    timeUnit = TimeUnit.MILLISECONDS,
                    backoffDelay = 10000L)
                .setInitialDelay(
                    duration = 10,
                    timeUnit = TimeUnit.SECONDS
                )
                .addTag(WORKER_TAG)
                .build()

            workManager
                .enqueue(workRequest)
                .await()
        }
    }
}