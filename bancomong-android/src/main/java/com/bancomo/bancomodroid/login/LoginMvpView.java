package com.bancomo.bancomodroid.login;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.user.User;

/**
 * Created by Rajan Maurya on 4/6/16.
 */
public interface LoginMvpView extends MvpView {

    void showToastMessage(String message);

    void onLoginSuccessful(User user);

    void onLoginError(String error);

}
