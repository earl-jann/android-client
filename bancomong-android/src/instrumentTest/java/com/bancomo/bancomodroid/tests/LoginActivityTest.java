/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.tests;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.test.suitebuilder.annotation.Suppress;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bancomo.bancomodroid.login.LoginActivity;
import com.bancomo.bancomodroid.R;
import com.bancomo.utils.Constants;
import com.bancomo.utils.PrefManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by ishankhanna on 12/08/14.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public static final String TEST_URL_1 = "demo.com.bancomo.org";
    public static final String TEST_URL_2 = "www.google.com";
    public static final String TEST_URL_3 = "this.is.valid.url";
    public static final String TEST_URL_4 = "yahoo.in";
    public final String TEST_URL_5 = getActivity().getString(R.string.test_ip);

    LoginActivity loginActivity;
    EditText et_bancomo_domain;
    EditText et_username;
    TextView tv_constructed_instance_url;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        loginActivity = getActivity();
        et_bancomo_domain = (EditText) loginActivity.findViewById(R.id.et_instanceURL);
        et_username = (EditText) loginActivity.findViewById(R.id.et_username);
        tv_constructed_instance_url = (TextView) loginActivity.findViewById(R.id
                .tv_constructed_instance_url);
    }

    @SmallTest
    public void testEditTextsAreNotNull() {
        assertNotNull(et_bancomo_domain);
        assertNotNull(et_username);
    }

    @SmallTest
    public void testAllEditTextsAreVisible() {
        assertEquals(View.VISIBLE, et_bancomo_domain.getVisibility());
    }

    @SuppressWarnings("deprecation")
    @SmallTest
    public void testURLInstance1() {
        //Test if TextView has been instantiated
        assertNotNull(tv_constructed_instance_url);

        // Set URL and check the color of the message, it turns green
        // only if the URL matches the pattern specified
        enterBancoMoInstanceDomain(TEST_URL_1);
        assertEquals(loginActivity.getResources().getColor(R.color.green_light),
                tv_constructed_instance_url.getCurrentTextColor());
    }

    @SuppressWarnings("deprecation")
    @SmallTest
    public void testURLInstance2() {
        //Test if TextView has been instantiated
        assertNotNull(tv_constructed_instance_url);
        enterBancoMoInstanceDomain(TEST_URL_2);
        assertEquals(loginActivity.getResources().getColor(R.color.green_light),
                tv_constructed_instance_url.getCurrentTextColor());
    }


    @SuppressWarnings("deprecation")
    @SmallTest
    public void testURLInstance3() {
        //Test if TextView has been instantiated
        assertNotNull(tv_constructed_instance_url);

        enterBancoMoInstanceDomain(TEST_URL_3);
        assertEquals(loginActivity.getResources().getColor(R.color.green_light),
                tv_constructed_instance_url.getCurrentTextColor());
    }

    @SuppressWarnings("deprecation")
    @SmallTest
    public void testURLInstance4() {
        //Test if TextView has been instantiated
        assertNotNull(tv_constructed_instance_url);
        enterBancoMoInstanceDomain(TEST_URL_4);
        assertEquals(loginActivity.getResources().getColor(R.color.green_light),
                tv_constructed_instance_url.getCurrentTextColor());
    }

    @SuppressWarnings("deprecation")
    @SmallTest
    public void testURLInstance5() {
        //Test if TextView has been instantiated
        assertNotNull(tv_constructed_instance_url);
        enterBancoMoInstanceDomain(TEST_URL_5);
        assertEquals(loginActivity.getResources().getColor(R.color.green_light),
                tv_constructed_instance_url.getCurrentTextColor());
    }

    @MediumTest
    public void testSaveLastAccessedInstanceDomainNameSavesProvidedString() {
        PrefManager.setInstanceDomain(TEST_URL_1);
        assertEquals(TEST_URL_1, PrefManager.getInstanceDomain());

        PrefManager.setInstanceDomain(TEST_URL_2);
        assertEquals(TEST_URL_2, PrefManager.getInstanceDomain());
    }

    @MediumTest
    @Suppress // TODO: Fix ComparisonFailure: expected:<[demo.com.bancomo.org]> but was:<[www.google.com]>
    public void testValidateUserInputsSavesValidDomainToSharedProperties() {
        saveLastAccessedInstanceDomainName(TEST_URL_2);
        enterBancoMoInstanceDomain(TEST_URL_1);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().validateUserInputs();
            }
        });
        getInstrumentation().waitForIdleSync();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences
                (getInstrumentation().getTargetContext());
        assertEquals(TEST_URL_1, sharedPreferences.getString(Constants.INSTANCE_URL_KEY, ""));
    }

    @MediumTest
    @Suppress // TODO: Fix expected:<https://demo.[com.bancomo.org:80]/bancomong-provider/ap...>
    // but was:<https://demo.[openmf.org]/bancomong-provider/ap...>
    public void testValidateUserInputsSetsAPIinstanceUrl() {
        saveLastAccessedInstanceDomainName(TEST_URL_2);
        enterBancoMoInstanceDomain(TEST_URL_1);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().validateUserInputs();
            }
        });
        getInstrumentation().waitForIdleSync();
    }

    @SmallTest
    public void testMoreOptionsDisplaysOfflineMenuItem() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText(is(startsWith("Offline")))).check(matches(isDisplayed()));
    }

    private void enterBancoMoInstanceDomain(final String domain) {
        clearBancoMoDomainTextInputField();
        getInstrumentation().sendStringSync(domain);
        getInstrumentation().waitForIdleSync();
    }

    private void clearBancoMoDomainTextInputField() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_bancomo_domain.setText("");
                et_bancomo_domain.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
    }

    private void saveLastAccessedInstanceDomainName(final String domain) {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                PrefManager.setInstanceDomain(domain);
            }
        });
        getInstrumentation().waitForIdleSync();
    }

}
