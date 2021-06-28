package com.akhil.weatherapp.presenter;

import com.akhil.weatherapp.view.adapter.MainActivityContract;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;


class MainActivityPresenterTest {

    private MainActivityContract.View view;
    private MainActivityPresenter presenter;

    @Test
    void initDataSuccess() {
        String location = "New York";
        presenter = new MainActivityPresenter(view);
        boolean result = presenter.initData(location);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void initDataFailure() {
        String location = "somerandomlocation";
        presenter = new MainActivityPresenter(view);
        boolean result = presenter.initData(location);
        assertThat(result).isEqualTo(false);
    }
}