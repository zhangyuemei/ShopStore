package com.hswt.shopstore.shopstore.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hswt.shopstore.shopstore.R;
import com.hswt.shopstore.shopstore.bean.MyDevice;

import java.util.List;

/**
 * 选择用户适配器
 */
public class SelectDeviceAdapter extends BaseAdapter {

    private Context context;
    private List<BluetoothDevice> deviceList;

    public SelectDeviceAdapter(Context context, List<BluetoothDevice> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }


    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutinflater;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            layoutinflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutinflater.inflate(R.layout.item_select_hall, null);

            viewHolder.selectCb = convertView.findViewById(R.id.select_user_cb);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final BluetoothDevice device = deviceList.get(position);

        viewHolder.selectCb.setText(device.getName() + "  " + device.getAddress());

        return convertView;
    }


    class ViewHolder {
        TextView selectCb;
    }
}
