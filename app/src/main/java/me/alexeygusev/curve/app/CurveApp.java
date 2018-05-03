package me.alexeygusev.curve.app;

import android.app.Application;

import me.alexeygusev.curve.BuildConfig;
import timber.log.Timber;

/**
 *
 */
public class CurveApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder().build();
        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
