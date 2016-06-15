package carduino.lzh.car;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import Adapters.DeviceAdapter;
import carduino.lzh.car.nets.ConnectThread;

public class BlueToothListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public final static String DEVICE_FOUND_LIST = "device.detail";
    BTListReciever reciever;
    List<HashMap<String, String>> devicesList = new ArrayList<HashMap<String, String>>();
    private ListView listview;
    DeviceAdapter adapter;
    BlueToothController controller = new BlueToothController();
    List<BluetoothDevice> bluebooths = new ArrayList<BluetoothDevice>();
    private Handler handler;
    private ProgressDialog dialog;
    private LinearLayout linear_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_list);
        listview = (ListView) findViewById(R.id.listview);
        linear_search= (LinearLayout) findViewById(R.id.linear_search);
        reciever = new BTListReciever();
        registerReceiver(reciever, new IntentFilter(DEVICE_FOUND_LIST));
        adapter = new DeviceAdapter(devicesList, this);
        listview.setAdapter(adapter);
        controller.turnOnBlueTooth();
        controller.findDevice();
        listview.setOnItemClickListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == Utils.CONNECT_FINISHED) {
                    dialog.cancel();
                    Utils.showToast(BlueToothListActivity.this, "连接成功");
                    setResult(RESULT_OK);
                    BlueToothListActivity.this.finish();
                } else if (what == Utils.CONNECT_FAILED) {
                    dialog.cancel();
                    Utils.showToast(BlueToothListActivity.this, msg.getData().getString(Utils.CONNECT_FAILED_REASON));
                }
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BluetoothDevice device = bluebooths.get(position);
        dialog = ProgressDialog.show(BlueToothListActivity.this, null, "正在连接蓝牙设备中。。。");
        controller.cancleFind();
           new ConnectThread(device,handler).start();
    }

    class BTListReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            Boolean isend;
            isend=bundle.getBoolean("end");
            if (isend){
                linear_search.setVisibility(View.INVISIBLE);
                return;
            }
            String macId = bundle.getString("mac");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", name);
            map.put("mac", macId);
            devicesList.add(map);
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            bluebooths.add(device);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciever);
        controller.cancleFind();
    }
}
