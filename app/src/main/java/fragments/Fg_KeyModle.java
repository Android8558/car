package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import carduino.lzh.car.R;
import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class Fg_KeyModle extends Fragment implements View.OnClickListener {
    /*
    初始化相关控件
     */
    private Button btn_forword;
    private Button btn_back;
    private Button btn_left;
    private Button btn_right;
    private Button btn_stop;
    private TextView tv_state;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.key_layout, null);
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        btn_forword = (Button) rootView.findViewById(R.id.btn_forword);
        btn_left = (Button) rootView.findViewById(R.id.btn_left);
        btn_right = (Button) rootView.findViewById(R.id.btn_right);
        btn_stop = (Button) rootView.findViewById(R.id.btn_stop);
        tv_state = (TextView) rootView.findViewById(R.id.tv_key_state);
        btn_right.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_forword.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == Utils.MESSAGE_READ) {
                    byte[] buffers = (byte[]) msg.obj;
                    String str = new String(buffers);
                    tv_state.setText(str);

                }
            }
        };


        Utils.readHandler = handler;
        return rootView;

    }

    @Override
    public void onClick(View v) {
        if (Utils.connectedThread == null) {
            Utils.showToast(getActivity(), "小车未连接，请先连接！");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_back:
                Utils.GO_BACK();
                break;
            case R.id.btn_forword:
                Utils.GO_FOWRD();

                break;
            case R.id.btn_right:
                Utils.GO_RIGHT();

                break;
            case R.id.btn_left:
                Utils.GO_LEFT();

                break;
            case R.id.btn_stop:
                Utils.GO_STOP();
                break;
        }

    }
}
