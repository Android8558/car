package carduino.lzh.car.datas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by Administrator on 2016/5/9 0009.
 */
public class OpenHelper extends SQLiteOpenHelper {

    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists battery_info("
                + "id integer primary key,"
                + "time varchar,"
                + "level integer)");

        ContentValues values = new ContentValues();
        values.put("name", 12);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
