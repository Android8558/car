package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import carduino.lzh.car.R;

/**
 * Created by Administrator on 2016/4/28 0028.
 */
public class DeviceAdapter extends BaseAdapter {
    List<HashMap<String, String>> devicesList;
    Context context;

    public DeviceAdapter(List<HashMap<String, String>> devicesList, Context context) {
        this.devicesList = devicesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return devicesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.dev_list_item, null);
            holder.tv_mac = (TextView) convertView.findViewById(R.id.text_mac);
            holder.tv_name = (TextView) convertView.findViewById(R.id.text_name);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        HashMap<String, String> map = devicesList.get(position);
        holder.tv_mac.setText(map.get("mac"));
        holder.tv_name.setText(map.get("name"));

        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
        private TextView tv_mac;
    }
}
