package com.bancomo.api.local;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Rajan Maurya on 23/06/16.
 */
@Database(name = BancoMoDatabase.NAME, version = BancoMoDatabase.VERSION, foreignKeysSupported = true)
public class BancoMoDatabase {

    // database name will be BancoMo.db
    public static final String NAME = "BancoMo";

    //Always Increase the Version Number
    public static final int VERSION = 1;
}
