package me.alexeygusev.curve.ui;

import javax.inject.Singleton;

import dagger.Component;
import me.alexeygusev.curve.viewmodel.MainViewModel;

/**
 * Basic Dagger Component
 */
@Singleton
@Component(modules = UiModule.class)
interface UiComponent {
    void inject(MainActivity activity);
    MainViewModel provideMainViewModel();
}
