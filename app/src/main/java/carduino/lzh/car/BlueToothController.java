package carduino.lzh.car;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rex on 2015/5/27.
 */
public class BlueToothController {

    private BluetoothAdapter mAapter;

    public BlueToothController() {
        mAapter = BluetoothAdapter.getDefaultAdapter();
    }


    public void turnOnBlueTooth( ) {

        mAapter.enable();
    }
    public  Boolean isturnOn(){
        return  mAapter.isEnabled();
    }

    /**
     * 打开蓝牙可见性
     * @param context
     */
    public void enableVisibly(Context context) {
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(discoverableIntent);
    }

    /**
     * 查找设备
     */
    public void findDevice() {
        assert (mAapter != null);
        mAapter.startDiscovery();
    }

    /**
     * 获取绑定设备
     * @return
     */
    public List<BluetoothDevice> getBondedDeviceList() {
        return new ArrayList<>(mAapter.getBondedDevices());
    }

    public Boolean isSupportBlueTooth(){
        return  mAapter != null;
    }

    public void disableBluetooth(){
        mAapter.disable();
    }
    public void cancleFind()
    {
        mAapter.cancelDiscovery();
    }
}
