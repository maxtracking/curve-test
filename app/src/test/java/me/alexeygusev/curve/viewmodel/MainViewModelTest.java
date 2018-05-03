package me.alexeygusev.curve.viewmodel;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Run basic test for the model.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @Mock
    private View mView;

    private MainViewModel mMainViewModel;

    @Before
    public void setUp() {
        mMainViewModel = new MainViewModel();
    }

    @After
    public void tearDown() {
        mMainViewModel.destroy();
    }

    @Test
    public void onClickTotalOnce() {
        mMainViewModel.onClickTotal(mView);
        assertEquals(true, mMainViewModel.isFlashing());
    }

    @Test
    public void onClickTotalTwice() {
        mMainViewModel.onClickTotal(mView);
        mMainViewModel.onClickTotal(mView);
        assertEquals(false, mMainViewModel.isFlashing());
    }
}
