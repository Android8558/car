package fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carduino.lzh.car.BlueToothListActivity;
import carduino.lzh.car.R;
import customViews.CircleBtnView;
import interfaces.CarConnected;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class Fg_connect extends Fragment implements View.OnClickListener {


    CircleBtnView connectbtn;
    CarConnected carConnected;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.content_main, null);
        connectbtn = (CircleBtnView) rootView.findViewById(R.id.connectbtn);
        connectbtn.initView("连接小车");
        connectbtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connectbtn:
                startActivityForResult(new Intent(getActivity(), BlueToothListActivity.class), 1010);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1010 && resultCode == Activity.RESULT_OK) {
            carConnected.connceted();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        carConnected = (CarConnected) activity;
    }
}
