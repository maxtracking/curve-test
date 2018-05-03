package me.alexeygusev.curve.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.alexeygusev.curve.R;

/**
 * This is a base class for 2 'Main' activities. It contains common code for the test so actual activities
 * can focus on their subject areas (Data Binding and RxJava2 respectively).
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private static final int FLASHING_INTERVAL = 500;
    private static final String TEXT_COLOR_PROPERTY = "textColor";

    @BindView(R.id.main_total_sum)
    TextView mTotalSum;

    @BindColor(R.color.colorPrimaryDark)
    int mFullColor;
    @BindColor(android.R.color.transparent)
    int mNullColor;

    protected final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected int mFlashingMethod = 0;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            toggleTotalSumVisibility();
        }
    };

    private final Property<TextView, Integer> mProperty = new Property<TextView, Integer>(int.class, TEXT_COLOR_PROPERTY) {
        @Override
        public Integer get(TextView object) {
            return object.getCurrentTextColor();
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTextColor(value);
        }
    };

    private final TimerHandler mHandler = new TimerHandler(this);

    protected Disposable mTimerDisposable;

    private ObjectAnimator animator;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(BaseActivity.class.getSimpleName()));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);

        mCompositeDisposable.dispose();

        if (mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
            mTimerDisposable.dispose();
        }

        super.onDestroy();
    }

    /**
     * Set flashing method index.
     *
     * @param value - selected position in the predefined list.
     */
    protected void setFlashingMethod(int value) {
        mFlashingMethod = value;
    }

    /**
     * Handle {@link BaseActivity#mTotalSum} flashing by selected method.
     *
     * @param startFlashing - true to start or false stop flashing.
     */
    protected void handleFlashingMethodSelection(Boolean startFlashing) {
        switch (mFlashingMethod) {
            case 0:
                toggleFlashingAnimated(startFlashing);
                break;
            case 1:
                toggleFlashingTimerTask(startFlashing);
                break;
            case 2:
                toggleFlashingHandler(startFlashing);
                break;
            case 3:
                toggleFlashingLocalReceiver(startFlashing);
                break;
            case 4:
                toggleFlashingRxJava(startFlashing);
                break;
            default:
                break;
        }
    }

    private void toggleFlashingAnimated(boolean startFlashing) {
        if (startFlashing) {
            mTotalSum.setTextColor(mFullColor);
            animator = ObjectAnimator.ofInt(mTotalSum, mProperty, Color.TRANSPARENT);
            animator.setDuration(500);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setRepeatMode(ObjectAnimator.RESTART);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
            animator.start();
        } else {
            animator.end();
            mTotalSum.setTextColor(mFullColor);
        }
    }

    private void toggleFlashingTimerTask(Boolean startFlashing) {
        if (startFlashing) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> toggleTotalSumVisibility());
                }
            }, 0, FLASHING_INTERVAL);
        } else {
            mTimer.cancel();
            mTimer = null;
            mTotalSum.setVisibility(View.VISIBLE);
        }
    }

    private void toggleFlashingHandler(Boolean startFlashing) {
        if (startFlashing) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            }, 0, FLASHING_INTERVAL);
        } else {
            mTimer.cancel();
            mTimer = null;
            mTotalSum.setVisibility(View.VISIBLE);
        }
    }

    private void toggleFlashingLocalReceiver(Boolean startFlashing) {
        if (startFlashing) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LocalBroadcastManager.getInstance(BaseActivity.this)
                            .sendBroadcast(new Intent(BaseActivity.class.getSimpleName()));
                }
            }, 0, FLASHING_INTERVAL);
        } else {
            mTimer.cancel();
            mTimer = null;
            mTotalSum.setVisibility(View.VISIBLE);
        }
    }

    private void toggleFlashingRxJava(Boolean startFlashing) {
        if (startFlashing) {
            mTimerDisposable = Observable.interval(FLASHING_INTERVAL, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> toggleTotalSumVisibility());
        } else {
            if (mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
                mTimerDisposable.dispose();
            }
        }
    }

    private void toggleTotalSumVisibility() {
        if (mTotalSum.getVisibility() == View.VISIBLE) {
            mTotalSum.setVisibility(View.INVISIBLE);
        } else {
            mTotalSum.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Simple Handler class for timer messages.
     * Uses {@link WeakReference} to have an access to outer class and avoid leaks.
     */
    static class TimerHandler extends Handler {

        private final WeakReference<BaseActivity> mOuterClass;

        TimerHandler(BaseActivity activity) {
            mOuterClass = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final int what = msg.what;

            switch (what) {
                case 1:
                    if (mOuterClass.get() != null) {
                        mOuterClass.get().toggleTotalSumVisibility();
                    }
                    break;
                default:
                    // no-op
            }
        }
    }
}
