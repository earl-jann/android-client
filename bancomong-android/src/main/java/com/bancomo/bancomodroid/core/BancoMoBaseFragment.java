/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.core;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bancomo.bancomodroid.R;
import com.bancomo.utils.Network;

/**
 * @author fomenkoo
 */
public class BancoMoBaseFragment extends Fragment {

    private BaseActivityCallback callback;
    private Activity activity;
    private InputMethodManager inputManager;
    private BancoMoProgressBarHandler mBancoMoProgressBarHandler;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        try {
            callback = (BaseActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "BaseActivityCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputManager = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mBancoMoProgressBarHandler = new BancoMoProgressBarHandler(getActivity());
    }

    public void showAlertDialog(String title, String message) {
        new MaterialDialog.Builder().init(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getStringMessage(R.string.dialog_action_ok), new
                        DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .createMaterialDialog()
                .show();
    }

    public Toolbar getToolbar() {
        return ((BancoMoBaseActivity) getActivity()).getToolbar();
    }

    protected void showBancoMoProgressDialog() {
        showBancoMoProgressDialog("Working...");
    }

    protected void showBancoMoProgressDialog(String message) {
        if (callback != null)
            callback.showProgress(message);
    }

    protected void hideBancoMoProgressDialog() {
        if (callback != null)
            callback.hideProgress();
    }

    protected void logout() {
        callback.logout();
    }

    protected void setToolbarTitle(String title) {
        callback.setToolbarTitle(title);
    }

    public void hideKeyboard(View view) {
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
                .RESULT_UNCHANGED_SHOWN);
    }

    protected void showBancoMoProgressBar() {
        mBancoMoProgressBarHandler.show();
    }

    protected void hideBancoMoProgressBar() {
        mBancoMoProgressBarHandler.hide();
    }

    protected String getStringMessage(int message) {
        return getResources().getString(message);
    }

    protected Boolean isOnline() {
        return Network.isOnline(getActivity());
    }
}
