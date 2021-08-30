package anton.asmirko.meltmusic.dagger

import anton.asmirko.meltmusic.application.MeltMusicApp
import anton.asmirko.meltmusic.repository.dao.BlobDao
import anton.asmirko.meltmusic.repository.dao.MetadataDao
import anton.asmirko.meltmusic.repository.dao.TransitionDao
import anton.asmirko.meltmusic.services.SpotifyBroadcastReceiver
import anton.asmirko.meltmusic.ui.activities.MainActivity
import anton.asmirko.meltmusic.ui.adapters.TransitionViewHolder
import anton.asmirko.meltmusic.ui.fragments.TransitionsFragment
import anton.asmirko.meltmusic.viewmodel.ViewModelProviderFactory
import com.spotify.android.appremote.api.ConnectionParams
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SpotifyModule::class,
        RoomModule::class,
        ViewModelFactoryModule::class,
        TransitionViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(spotifyBroadcastReceiver: SpotifyBroadcastReceiver)

    fun inject(mainActivity: MainActivity)

    fun inject(spotifyBroadcastEntry: SpotifyBroadcastReceiver.SpotifyBroadcastEntry)

    fun inject(transitionViewHolder: TransitionViewHolder)

    fun inject(transitionsFragment: TransitionsFragment)

    fun inject(viewModelFactory: ViewModelProviderFactory)

}

@Module
object SpotifyModule {

    @Provides
    fun provideConnectionParams(): ConnectionParams {
        return ConnectionParams.Builder(MeltMusicApp.CLIENT_ID)
            .setRedirectUri(MeltMusicApp.REDIRECT_URI)
            .showAuthView(true)
            .build()
    }
}

@Module
object RoomModule {

    @Provides
    fun provideBlobDao(): BlobDao = MeltMusicApp.INSTANCE.databse.blobDao()

    @Provides
    fun provideTransitionDao(): TransitionDao = MeltMusicApp.INSTANCE.databse.transitionDao()

    @Provides
    fun provideMetadataDao(): MetadataDao = MeltMusicApp.INSTANCE.databse.metadataDao()
}