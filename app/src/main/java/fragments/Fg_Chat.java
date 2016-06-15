package fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Adapters.ChatAdapter;
import carduino.lzh.car.R;
import carduino.lzh.car.Utils;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class Fg_Chat extends Fragment {
    private ListView listView;
    private Button sendBtn;
    private EditText inputEdit;
    private List<HashMap<String, String>> lists;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Utils.MESSAGE_READ) {
                byte[] buffers = (byte[]) msg.obj;
                String str = new String(buffers);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("cg", "receive");
                map.put("content", str);
                lists.add(map);
                ChatAdapter adapter = new ChatAdapter(lists, getContext());
                listView.setAdapter(adapter);

                /*让对话滑到最底部
                 */
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(listView.getBottom());
                    }
                };
                listView.post(runnable);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_chat_xml, null);
        listView = (ListView) rootView.findViewById(R.id.list_chat);
        sendBtn = (Button) rootView.findViewById(R.id.sendBtn);
        inputEdit = (EditText) rootView.findViewById(R.id.edit_input);
        sendBtn.setOnClickListener(new SendClickListener());
        lists = new ArrayList<HashMap<String, String>>();

        Utils.readHandler = mhandler;
        return rootView;
    }

    class SendClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String text = inputEdit.getText().toString();
            if (TextUtils.isEmpty(text))
                return;

            Utils.HandlerCommand(text, getContext());
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("cg", "send");
            map.put("content", text);
            lists.add(map);


            ChatAdapter adapter = new ChatAdapter(lists, getContext());
            listView.setAdapter(adapter);
            inputEdit.setText("");


        }
    }
}
