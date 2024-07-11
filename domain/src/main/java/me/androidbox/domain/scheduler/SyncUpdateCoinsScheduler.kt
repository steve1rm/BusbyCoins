package me.androidbox.domain.scheduler

interface SyncUpdateCoinsScheduler {
    suspend fun scheduleUpdate()

    suspend fun cancelSync()
}