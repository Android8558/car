package broadcasts;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import carduino.lzh.car.Utils;

/**
 * 监听蓝牙开关的广播
 */
public class BluetoothSwitchReceiver extends BroadcastReceiver {
    public BluetoothSwitchReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                Utils.showToast(context, "蓝牙已关闭");
                break;
            case BluetoothAdapter.STATE_ON:
                Utils.showToast(context, "蓝牙已开启");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Utils.showToast(context, "正在开启蓝牙");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Utils.showToast(context, "正在关闭蓝牙");
                break;
            default:
                Utils.showToast(context, "未知状态！！！");
                break;
        }
    }
}
