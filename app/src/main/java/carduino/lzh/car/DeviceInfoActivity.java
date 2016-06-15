package carduino.lzh.car;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceInfoActivity extends AppCompatActivity {
    private TextView textView;
    private BlueToothController mController = new BlueToothController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_state_bluetooth);
        textView = (TextView) findViewById(R.id.f_s_textview);

        StringBuilder builder = new StringBuilder();
        boolean ret = mController.isSupportBlueTooth();
        builder.append("设备是否支持蓝牙：" + ret + "\n" + "\n");
        builder.append("蓝牙是否已打开：" + mController.isturnOn() + "\n" + "\n");
        builder.append("小车是否已连接：" + !(Utils.connectedThread == null) + "\n" + "\n");
        builder.append("设备型号：" + android.os.Build.MODEL + "\n" + "\n" + " API 版本："
                + android.os.Build.VERSION.SDK + ",发布版本"
                + android.os.Build.VERSION.RELEASE);

        String resultStr = builder.toString();
        textView.setText(resultStr);
    }
}
