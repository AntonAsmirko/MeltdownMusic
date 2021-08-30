package anton.asmirko.meltmusic.dagger;

import androidx.lifecycle.ViewModelProvider;

import anton.asmirko.meltmusic.viewmodel.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory provideViewModelFactory(
            ViewModelProviderFactory viewModelProviderFactory
    );
}
