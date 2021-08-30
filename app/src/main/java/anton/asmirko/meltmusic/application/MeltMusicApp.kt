package anton.asmirko.meltmusic.application

import android.app.Application
import androidx.room.Room
import androidx.room.RoomWarnings
import anton.asmirko.meltmusic.dagger.AppComponent
import anton.asmirko.meltmusic.dagger.DaggerAppComponent
import anton.asmirko.meltmusic.repository.AppDatabase

class MeltMusicApp: Application(){
    companion object {
        private const val DATABASE_NAME = "database"
        const val CLIENT_ID = "3121a31719054591b2b7805bb7a788e4"
        const val REDIRECT_URI = "http://com.yourdomain.yourapp/callback"
        lateinit var INSTANCE: MeltMusicApp
    }

    lateinit var appComponent: AppComponent
    lateinit var databse: AppDatabase

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        databse = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
        INSTANCE = this
    }
}