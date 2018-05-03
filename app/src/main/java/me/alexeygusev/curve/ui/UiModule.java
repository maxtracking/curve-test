package me.alexeygusev.curve.ui;

import dagger.Module;
import dagger.Provides;
import me.alexeygusev.curve.viewmodel.MainViewModel;

/**
 * Basic Dagger Module
 */
@Module
abstract class UiModule {

    @Provides
    static MainViewModel provideMainViewModel() {
        return new MainViewModel();
    }
}
