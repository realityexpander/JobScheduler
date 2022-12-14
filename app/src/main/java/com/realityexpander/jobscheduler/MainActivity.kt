package com.realityexpander.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun scheduleJob(v: View?) {
        val componentName = ComponentName(this, ExampleJobService::class.java)
        val info = JobInfo.Builder(123, componentName)
            //.setRequiresCharging(true) // will only run when the device is charging (not just plugged in)
            //.setRequiresBatteryNotLow(true) //
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .setRequiresDeviceIdle(false)
            //.setPeriodic((15 * 60 * 1000).toLong()) // minimum is 15 minutes (forced internally)
            .setMinimumLatency(1000)
            .build()
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }


    }

    fun cancelJob(v: View?) {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Log.d(TAG, "Job cancelled")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}