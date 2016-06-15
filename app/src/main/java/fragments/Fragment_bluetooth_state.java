package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import carduino.lzh.car.R;

/**
 * Created by Administrator on 2016/2/2 0002.
 */
public class Fragment_bluetooth_state extends Fragment {
    private TextView mtextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_state_bluetooth,container,false);
        mtextView= (TextView) view.findViewById(R.id.f_s_textview);
         return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    public void setState(String str) {
        mtextView.setText(str);
    }

    public void appendState(String str) {
        mtextView.append("\n\n"+str);
    }
}
