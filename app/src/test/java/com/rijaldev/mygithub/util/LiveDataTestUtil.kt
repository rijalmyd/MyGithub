package com.rijaldev.mygithub.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object LiveDataTestUtil {
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this@getOrAwaitValue.observeForever(observer)
        try {
            afterObserve.invoke()
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveDate value was never set.")
            }
        } finally {
            this@getOrAwaitValue.removeObserver(observer)
        }
        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}