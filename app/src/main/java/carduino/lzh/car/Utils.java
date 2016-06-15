package carduino.lzh.car;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import carduino.lzh.car.nets.ConnectedThread;

/**
 * Created by Administrator on 2016/2/2 0002.
 */
public class Utils {
    /*
    存储电量的数据库
     */
    private final static String DB_NAME = "battery.db";

    private final int DB_VERSION = 1;

    private static Toast toast;
    /*
   连接蓝牙串口的UUID
    */
    public final static String UUID = "00001101-0000-1000-8000-00805F9B34FB";
    /*
    连接完成
     */
    public final static int CONNECT_FINISHED = 10010;
    /*
    连接失败
     */
    public final static int CONNECT_FAILED = 10011;
    public final static String CONNECT_FAILED_REASON = "reason_failed";

    public static void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else
            toast.setText(str);
        toast.show();
    }

    /*
    蓝牙发送数据网络层
     */
    public static ConnectedThread connectedThread;
    /*
    调试TAG
     */
    public final static String TAG = "lzh_car";
    /*
    控制命令
     */
    private final static String COMMAD_FORWORD = "f";
    private final static String COMMAD_BACK = "b";
    private final static String COMMAD_LEFT = "l";
    private final static String COMMAD_RIGHT = "r";
    private final static String COMMAD_STOP = "s";

    public static void GO_FOWRD() {
        connectedThread.write(COMMAD_FORWORD.getBytes());
    }

    public static void GO_BACK() {
        connectedThread.write(COMMAD_BACK.getBytes());

    }

    public static void GO_LEFT() {
        connectedThread.write(COMMAD_LEFT.getBytes());

    }

    public static void GO_RIGHT() {
        connectedThread.write(COMMAD_RIGHT.getBytes());

    }

    public static void GO_STOP() {
        connectedThread.write(COMMAD_STOP.getBytes());

    }

    /*
    读取消息的Handler
     */
    public static Handler readHandler;
    public final static int MESSAGE_READ = 20002;

    /*
    定义命令词库
     */
    private static final String[] TOP_COMMONDS = {"向前走", "前行", "top","t"};
    private static final String[] LEFT_COMMONDS = {"向左走", "左转", "left","l"};
    private static final String[] BACK_COMMONDS = {"向后走", "后退", "back","b"};
    private static final String[] RIGHT_COMMONDS = {"向右走", "右转", "right","r"};
    private static final String[] STOP_COMMONDS = {"停止运行", "stop","s"};

    /*
    HANDLE C
     */
    public static void HandlerCommand(String str, Context context) {
        if (connectedThread == null) {
            Utils.showToast(context, "小车未连接！");
            return;
        }
        for (int i = 0; i < TOP_COMMONDS.length; i++) {
            if (TOP_COMMONDS[i].contains(str)) {


                GO_FOWRD();
                return;
            }
        }

        for (int i = 0; i < LEFT_COMMONDS.length; i++) {
            if (LEFT_COMMONDS[i].contains(str)) {
                GO_LEFT();
                return;
            }
        }
        for (int i = 0; i < RIGHT_COMMONDS.length; i++) {
            if (RIGHT_COMMONDS[i].contains(str)) {
                GO_RIGHT();

                return;
            }
        }
        for (int i = 0; i < BACK_COMMONDS.length; i++) {
            if (BACK_COMMONDS[i].contains(str)) {
                GO_BACK();
                return;
            }
        }
        for (int i = 0; i < STOP_COMMONDS.length; i++) {
            if (STOP_COMMONDS[i].contains(str)) {
                GO_STOP();
                return;
            }
        }

    }
}
