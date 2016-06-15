package carduino.lzh.car.nets;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/4/29 0029.
 */

public class ConnectedThread extends Thread {

    private final BluetoothSocket mmSocket;

    private final InputStream mmInStream;

    private final OutputStream mmOutStream;


    public ConnectedThread(BluetoothSocket socket) {

        mmSocket = socket;

        InputStream tmpIn = null;

        OutputStream tmpOut = null;


        // Get the input and output streams, using temp objects because

        // member streams are final

        try {

            tmpIn = socket.getInputStream();

            tmpOut = socket.getOutputStream();

        } catch (IOException e) {
        }


        mmInStream = tmpIn;

        mmOutStream = tmpOut;

    }


    public void run() {

        while (true) {
            try {
                int bytes = mmInStream.available();//网络通讯往往是间断性的，一串字节往往分几批进行发送
                if (bytes == 0) {
                    continue;
                }
                Thread.sleep(500);
                bytes = mmInStream.available();

                byte[] buffer = new byte[bytes];
                int i = mmInStream.read(buffer);
                Log.i(Utils.TAG, new String(buffer, "GB2312"));
                if (Utils.readHandler != null)
                    Utils.readHandler.obtainMessage(Utils.MESSAGE_READ, 0, 0, bytes).sendToTarget();
            } catch (IOException e) {


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }



    /* Call this from the main Activity to send data to the remote device */

    public void write(byte[] bytes) {

        try {

            mmOutStream.write(bytes);
        } catch (IOException e) {
            Log.i(Utils.TAG, e.getMessage());
        }

    }



    /* Call this from the main Activity to shutdown the connection */

    public void cancel() {

        try {

            mmSocket.close();

        } catch (IOException e) {
        }

    }

}