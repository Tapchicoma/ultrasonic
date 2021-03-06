package org.moire.ultrasonic.app

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.moire.ultrasonic.BuildConfig
import org.moire.ultrasonic.di.appPermanentStorage
import org.moire.ultrasonic.di.baseNetworkModule
import org.moire.ultrasonic.di.directoriesModule
import org.moire.ultrasonic.di.featureFlagsModule
import org.moire.ultrasonic.di.mediaPlayerModule
import org.moire.ultrasonic.di.musicServiceModule
import org.moire.ultrasonic.log.FileLoggerTree
import org.moire.ultrasonic.log.timberLogger
import org.moire.ultrasonic.util.Util
import timber.log.Timber
import timber.log.Timber.DebugTree

class UApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        if (Util.getDebugLogToFile(this)) {
            FileLoggerTree.plantToTimberForest(this)
        }

        startKoin {
            // TODO Current version of Koin has a bug, which forces the usage of Level.ERROR
            timberLogger(Level.ERROR)
            // declare Android context
            androidContext(this@UApp)
            // declare modules to use
            modules(
                directoriesModule,
                appPermanentStorage,
                baseNetworkModule,
                featureFlagsModule,
                musicServiceModule,
                mediaPlayerModule
            )
        }
    }
}
