package com.bancomo.bancomodroid.online.datatable;

import com.bancomo.bancomodroid.base.MvpView;
import com.bancomo.objects.noncore.DataTable;

import java.util.List;

/**
 * Created by Rajan Maurya on 12/02/17.
 */

public interface DataTableMvpView extends MvpView {

    void showUserInterface();

    void showDataTables(List<DataTable> dataTables);

    void showEmptyDataTables();

    void showResetVisibility();

    void showError(int message);
}
