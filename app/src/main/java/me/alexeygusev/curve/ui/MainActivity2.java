package me.alexeygusev.curve.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import me.alexeygusev.curve.R;
import timber.log.Timber;

public class MainActivity2 extends BaseActivity {

    @BindView(R.id.main_field_1)
    EditText mField01;
    @BindView(R.id.main_field_2)
    EditText mField02;
    @BindView(R.id.main_field_3)
    EditText mField03;
    @BindView(R.id.main_field_4)
    EditText mField04;
    @BindView(R.id.main_field_5)
    EditText mField05;
    @BindView(R.id.main_field_6)
    EditText mField06;

    private boolean mToggleState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mCompositeDisposable.addAll(
                RxTextView.afterTextChangeEvents(mField01)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum()),
                RxTextView.afterTextChangeEvents(mField02)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum()),
                RxTextView.afterTextChangeEvents(mField03)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum()),
                RxTextView.afterTextChangeEvents(mField04)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum()),
                RxTextView.afterTextChangeEvents(mField05)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum()),
                RxTextView.afterTextChangeEvents(mField06)
                        .subscribe(textViewAfterTextChangeEvent -> calculateTotalSum())
        );
    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.dispose();
        super.onDestroy();
    }

    @OnClick(R.id.main_total_sum)
    void onTotalSumClicked() {
        mToggleState = !mToggleState;
        handleFlashingMethodSelection(mToggleState);
    }

    @OnItemSelected(R.id.main_flashing_method)
    void onItemSelected(int position) {
        mFlashingMethod = position;
    }

    private void calculateTotalSum() {
        int total = getFieldValue(mField01) +
                getFieldValue(mField02) +
                getFieldValue(mField03) +
                getFieldValue(mField04) +
                getFieldValue(mField05) +
                getFieldValue(mField06);
        Timber.d("Total sum: %d", total);
        mTotalSum.setText(String.format(Locale.ENGLISH, "%d", total));
    }

    private int getFieldValue(EditText field) {
        return TextUtils.isEmpty(field.getEditableText().toString()) ? 0 : Integer.parseInt(field.getEditableText().toString());
    }
}
