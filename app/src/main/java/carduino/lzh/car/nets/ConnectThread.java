package carduino.lzh.car.nets;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.UUID;


import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/4/29 0029.
 * 客户端配对并连接
 */
public class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;

    private final BluetoothDevice mmDevice;
    private Handler mhandler;


    public ConnectThread(BluetoothDevice device,Handler mhandler) {
    this.mhandler=mhandler;
        // Use a temporary object that is later assigned to mmSocket,

        // because mmSocket is final

        BluetoothSocket tmp = null;

        mmDevice = device;



        // Get a BluetoothSocket to connect with the given BluetoothDevice

        try {

            // MY_UUID is the app's UUID string, also used by the server code

            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(Utils.UUID));

        } catch (IOException e) { }

        mmSocket = tmp;

    }



    public void run() {




        try {

            // Connect the device through the socket. This will block

            // until it succeeds or throws an exception

            mmSocket.connect();

        } catch (IOException connectException) {

            // Unable to connect; close the socket and get out
            Message msg=new Message();
            msg.what=Utils.CONNECT_FAILED;
            Bundle bundle=new Bundle();
            bundle.putString(Utils.CONNECT_FAILED_REASON,connectException.getMessage());
            msg.setData(bundle);
            mhandler.sendMessage(msg);
            try {

                mmSocket.close();

            } catch (IOException closeException) { }

            return;

        }

        Message msg=new Message();
        msg.what=Utils.CONNECT_FINISHED;
        mhandler.sendMessage(msg);
        // Do work to manage the connection (in a separate thread)

        Utils.connectedThread=new ConnectedThread(mmSocket);
        Utils.connectedThread.start();

    }



    /** Will cancel an in-progress connection, and close the socket */

    public void cancel() {

        try {

            mmSocket.close();

        } catch (IOException e) { }

    }

}