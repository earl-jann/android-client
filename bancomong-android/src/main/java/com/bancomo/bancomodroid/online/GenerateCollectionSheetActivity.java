/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.online;

import android.os.Bundle;
import android.view.Menu;

import com.bancomo.bancomodroid.R;
import com.bancomo.bancomodroid.core.BancoMoBaseActivity;
import com.bancomo.bancomodroid.online.generatecollectionsheet.GenerateCollectionSheetFragment;


public class GenerateCollectionSheetActivity extends BancoMoBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_container);
        replaceFragment(GenerateCollectionSheetFragment.newInstance(), false, R.id.container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.generate_collection_sheet, menu);
        return true;
    }
}
