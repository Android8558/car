package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carduino.lzh.car.R;
import carduino.lzh.car.Utils;
import customViews.HuaBanView;
import interfaces.PathChangedFace;

/**
 * Created by Administrator on 2016/5/4 0004.
 * 按照时间轴计算x与y的差值决定小车的运动方向
 */
public class Fg_Plan extends Fragment implements PathChangedFace {
    HuaBanView pathView;
    private final int CALC_PIX_SPEC = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_plan_xml, null);
        pathView = (HuaBanView) rootView.findViewById(R.id.huaban);
        pathView.setPathChangedFace(this);

        return rootView;
    }


    @Override
    public void paintChanged(float cx, float cy) {
        if (Utils.connectedThread == null) {
            Utils.showToast(getContext(), "小车未连接！");
            return;
        }
        if (cx > CALC_PIX_SPEC) {
            Utils.GO_LEFT();
            return;
        }
        if (cx < -CALC_PIX_SPEC) {
            Utils.GO_RIGHT();
            return;
        }

        if (cy > CALC_PIX_SPEC) {
            Utils.GO_FOWRD();
            return;
        }
        if (cy < -CALC_PIX_SPEC) {
            Utils.GO_BACK();
            return;
        }
        Utils.GO_STOP();
        Utils.showToast(getContext(), "路径已改变");
    }
}
