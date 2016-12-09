package cn.canlnac.onlinecourse.presentation.ui.fragment;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.presentation.presenter.RegisterPresenter;

/**
 * 注册fragment.
 */

public class RegisterFragment extends BaseFragment {
    @Inject
    RegisterPresenter registerPresenter;

    public void showError(String message) {
        this.showToastMessage(message);
    }
}
