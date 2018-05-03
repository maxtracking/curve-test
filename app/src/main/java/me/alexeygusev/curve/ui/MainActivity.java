package me.alexeygusev.curve.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.alexeygusev.curve.R;
import me.alexeygusev.curve.databinding.ActivityMainBinding;
import me.alexeygusev.curve.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    @Inject
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UiComponent appComponent = DaggerUiComponent.builder().build();
        appComponent.inject(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setCurveModel(mViewModel);

        ButterKnife.bind(this);

        mCompositeDisposable.addAll(
                mViewModel.getToggleObservable()
                        .subscribe(this::handleFlashingMethodSelection),
                mViewModel.getFlashingMethodObservable()
                        .subscribe(this::setFlashingMethod)
        );
    }

    @Override
    protected void onDestroy() {
        mViewModel.destroy();

        super.onDestroy();
    }
}
