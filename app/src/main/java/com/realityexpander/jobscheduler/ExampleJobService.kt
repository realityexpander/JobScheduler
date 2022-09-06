package com.realityexpander.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.util.Log
import android.widget.Toast


class ExampleJobService : JobService() {
    private var jobCancelled = false

    var handler: Handler = Handler()

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started")
        doBackgroundWork(params)
        return true
    }

    private fun doBackgroundWork(params: JobParameters) {
        Thread( Runnable {

            for (i in 0..9) {
                Log.d(TAG, "run: $i")
                if (jobCancelled) {
                    return@Runnable
                }

                handler.post(Runnable {
                    Toast.makeText(this, "Job Service running $i", Toast.LENGTH_SHORT).show()
                })

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.d(TAG, "Job finished")


            jobFinished(params, false)
        }).start()
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobCancelled = true
        return true
    }

    companion object {
        private const val TAG = "ExampleJobService"
    }
}