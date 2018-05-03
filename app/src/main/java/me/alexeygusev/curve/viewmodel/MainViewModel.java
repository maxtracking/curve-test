package me.alexeygusev.curve.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.alexeygusev.curve.model.Fields;
import timber.log.Timber;

/**
 * Simple {@link CurveViewModel}.
 */
public class MainViewModel extends BaseObservable implements CurveViewModel {

    private final PublishSubject<Boolean> mToggleSubject  = PublishSubject.create();
    private final PublishSubject<Integer> mFlashingMethodSubject  = PublishSubject.create();
    private final Fields mFields = new Fields();

    private boolean mToggleState = false;

    public MainViewModel() {
        // no-op here
    }

    @Override
    public void destroy() {
        // no-op here
    }

    @Bindable
    public Fields getFields() {
        return mFields;
    }

    public void onClickTotal(View view) {
        Timber.d("Toggle total sum flashing");
        mToggleState = !mToggleState;
        mToggleSubject.onNext(mToggleState);
    }

    public Observable<Boolean> getToggleObservable() {
        return mToggleSubject;
    }

    public Observable<Integer> getFlashingMethodObservable() {
        return mFlashingMethodSubject;
    }

    public boolean isFlashing() {
        return mToggleState;
    }
}
