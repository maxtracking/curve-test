package me.alexeygusev.curve.app;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Basic Dagger Component
 */
@Singleton
@Component(modules = AppModule.class)
interface AppComponent {
    void inject(CurveApp app);
}
