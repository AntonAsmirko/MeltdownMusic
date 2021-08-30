package anton.asmirko.meltmusic.utils

import android.content.Context
import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.dagger.AppComponent

val Context.appComponent: AppComponent
    get() = when(this) {
        is MeltMusicApp -> appComponent
        else -> this.applicationContext.appComponent
    }