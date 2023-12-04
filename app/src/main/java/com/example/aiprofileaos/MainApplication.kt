package com.example.aiprofileaos

import android.content.Context
import android.content.SharedPreferences
import android.os.Process
import android.util.Log
import androidx.multidex.MultiDexApplication
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class MainApplication: MultiDexApplication() {
    companion object {
        var sharedPref: SharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        setUncaughtExceptionHandler()
    }

    private fun setUncaughtExceptionHandler() {
        val defaultHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.d(thread.name, getStackTrace(throwable))

            defaultHandler?.uncaughtException(thread, throwable)
            Process.killProcess(Process.myPid())
            exitProcess(0)
        }
    }

    private fun getStackTrace(e: Throwable?): String {
        val result = StringWriter()
        val printWriter = PrintWriter(result)

        var th: Throwable? = e
        while(th != null) {
            th.printStackTrace(printWriter)
            th = th.cause
        }

        val stackTraceAsString = result.toString()
        printWriter.close()

        return stackTraceAsString
    }
}