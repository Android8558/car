package fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import carduino.lzh.car.R;
import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/5/3 0003.
 * 重力感应模块
 */
public class Fg_Gravity extends Fragment implements SensorEventListener {
    private TextView tv_gravity;
    private TextView tv_state;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int mX, mY, mZ;
    Calendar mCalendar;
    private long lasttimestamp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gravity_xml, null);
        tv_gravity = (TextView) rootView.findViewById(R.id.tv_gravity);
        tv_state = (TextView) rootView.findViewById(R.id.tv_state);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
        if (null == mSensorManager) {
            Log.d(Utils.TAG, "deveice not support SensorManager");
        }
        // 参数三，检测的精准度
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME 200ms

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 1000l;// 1393844912

            tv_gravity.setText("x" + String.valueOf(x) + "\n");
            tv_gravity.append("y" + String.valueOf(y) + "\n");
            tv_gravity.append("z" + String.valueOf(z) + "\n");

            int second = mCalendar.get(Calendar.SECOND);// 53

            int px = Math.abs(mX - x);
            int py = Math.abs(mY - y);
            int pz = Math.abs(mZ - z);
            Log.d(Utils.TAG, "pX:" + px + "  pY:" + py + "  pZ:" + pz + "    stamp:"
                    + stamp + "  second:" + second);
            int maxvalue = getMaxValue(px, py, pz);
            if (maxvalue > 2 && (stamp - lasttimestamp) > 30) {
                lasttimestamp = stamp;
                Log.d(Utils.TAG, " sensor isMoveorchanged....");
             }


            mX = x;
            mY = y;
            mZ = z;
            String str = null;
            if (Utils.connectedThread == null)
                return;

            if (mX == 0 && mY == 0) {
                Utils.GO_STOP();
                str="停止";
            }
            if (mY < -3) {
                Utils.GO_BACK();
                str="后退";

            }
            if (mY > 3) {
                Utils.GO_FOWRD();
                str="前进";

            }
            if (mX < -3) {
                Utils.GO_RIGHT();
                str="右转";

            }
            if (mX > 3) {
                Utils.GO_LEFT();
                str="左转";

            }
            tv_state.setText(str);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 获取一个最大值
     *
     * @param px
     * @param py
     * @param pz
     * @return
     */
    public int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
        } else if (py > px && py > pz) {
            max = py;
        } else if (pz > px && pz > py) {
            max = pz;
        }

        return max;
    }

    @Override
    public void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);

    }
}
