package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import carduino.lzh.car.R;
import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/5/4 0004.
 */
public class Fg_Detection extends Fragment implements View.OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dection_fragemtnt, null);
        button1 = (Button) rootView.findViewById(R.id.button1);
        button2 = (Button) rootView.findViewById(R.id.button2);
        button3 = (Button) rootView.findViewById(R.id.button3);
        button4 = (Button) rootView.findViewById(R.id.button4);
        button5 = (Button) rootView.findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {

        if (Utils.connectedThread == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.button1:
                Utils.GO_FOWRD();
                break;
            case R.id.button2:
                Utils.GO_BACK();
                break;
            case R.id.button3:
                Utils.GO_LEFT();
                break;
            case R.id.button4:
                Utils.GO_RIGHT();
                break;
            case R.id.button5:
                Utils.GO_STOP();
                break;
            default:
                break;
        }

    }
}
