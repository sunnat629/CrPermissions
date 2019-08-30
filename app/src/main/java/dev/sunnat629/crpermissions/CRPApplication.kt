package dev.sunnat629.crpermissions

import android.app.Application
import timber.log.Timber

class CRPApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.asTree()
        }
    }
}