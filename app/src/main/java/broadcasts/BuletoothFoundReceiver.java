package broadcasts;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import carduino.lzh.car.BlueToothListActivity;
import carduino.lzh.car.Utils;

public class BuletoothFoundReceiver extends BroadcastReceiver {
    public BuletoothFoundReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.i("nini", device.getName() + "\t" + device.getAddress());
            Bundle bundle=new Bundle();
            bundle.putBoolean("end",false);
            bundle.putString("name",device.getName())            ;
            bundle.putString("mac",device.getAddress());
             Intent inten=new Intent(BlueToothListActivity.DEVICE_FOUND_LIST);
            inten.putExtras(bundle);
            inten.putExtra(BluetoothDevice.EXTRA_DEVICE,device);

            context.sendBroadcast(inten);

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Log.i(Utils.TAG, "find over");
            Bundle bundle=new Bundle();
            bundle.putBoolean("end",true);
            Intent inten=new Intent(BlueToothListActivity.DEVICE_FOUND_LIST);
            inten.putExtras(bundle);
            context.sendBroadcast(inten);

        }
    }
}
