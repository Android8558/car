package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import carduino.lzh.car.R;

/**
 * Created by Administrator on 2016/5/5 0005.
 */
public class ChatAdapter extends BaseAdapter {

    private List<HashMap<String, String>> lists;
    private Context context;

    public ChatAdapter(List<HashMap<String, String>> lists, Context cOntext) {
        this.lists = lists;
        this.context = cOntext;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        HashMap<String, String> map = lists.get(position);
        String catgory = map.get("cg");
        String content = map.get("content");

        if (catgory.equals("send")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.send_chat_item, null);
            ViewHolder holder = new ViewHolder();
            holder.txt_content = (TextView) convertView.findViewById(R.id.chat_content_send);
            holder.img_head = (ImageView) convertView.findViewById(R.id.chat_img_send);
            holder.txt_content.setText(content);

        } else if (catgory.equals("receive")) {


            convertView = LayoutInflater.from(context).inflate(R.layout.chat_item, null);
            ViewHolder holder = new ViewHolder();
            holder.txt_content = (TextView) convertView.findViewById(R.id.chat_content);
            holder.img_head = (ImageView) convertView.findViewById(R.id.chat_img);
            holder.txt_content.setText(content);
        }

        return convertView;
    }


    class ViewHolder {
        private TextView txt_content;
        private ImageView img_head;


    }
}
