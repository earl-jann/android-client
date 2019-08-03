/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.bancomo.bancomodroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bancomo.bancomodroid.R;
import com.bancomo.objects.db.BancoMoGroup;

import java.util.List;


public class BancoMoGroupListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<BancoMoGroup> groups;

    public BancoMoGroupListAdapter(Context context, List<BancoMoGroup> groups) {
        layoutInflater = LayoutInflater.from(context);
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return this.groups.size();
    }

    @Override
    public BancoMoGroup getItem(int i) {
        return this.groups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_group_list_item, viewGroup, false);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_group_name = (TextView) view.findViewById(R.id.tv_group_name);
        viewHolder.tv_staff_name = (TextView) view.findViewById(R.id.tv_staff_name);
        viewHolder.tv_level_name = (TextView) view.findViewById(R.id.tv_level_name);


        BancoMoGroup bancoMoGroup = groups.get(i);
        viewHolder.tv_group_name.setText(bancoMoGroup.getGroupName());
        viewHolder.tv_staff_name.setText(bancoMoGroup.getStaffName());
        viewHolder.tv_level_name.setText(bancoMoGroup.getLevelName());

        return view;
    }

    public static class ViewHolder {
        TextView tv_group_name;
        TextView tv_staff_name;
        TextView tv_level_name;
    }
}
