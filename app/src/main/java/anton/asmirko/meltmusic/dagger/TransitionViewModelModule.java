package anton.asmirko.meltmusic.dagger;

import androidx.lifecycle.ViewModel;

import anton.asmirko.meltmusic.viewmodel.TransitionViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class TransitionViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TransitionViewModel.class)
    public abstract ViewModel bindsAuthViewModel(TransitionViewModel viewModel);
}
