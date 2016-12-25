package chenyiyan.timer;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by lenovo on 2016/12/21.
 */
public class MyEasyDataHelper extends SQLiteOpenHelper {
    private String DATABASE_NAME;


    //构造函数，DATABASE_NAME为数据库名字，version为版本，构建时mDB = new MyDBOpenHelper(this,"task",null,1);，即可；
    public MyEasyDataHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        DATABASE_NAME = name;
    }
    //第一次打开程序的时候才会跑！
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+DATABASE_NAME+"(ID integer primary key,key TEXT, value TEXT);");
        db.execSQL("insert into " + DATABASE_NAME + "(key,value) values(?,?)" , new Object[]{"credits","0"});
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(String key,String value){
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cur = db1.rawQuery("SELECT MAX(ID) FROM "+DATABASE_NAME, null);
        cur.moveToFirst();
        cur.close();
        db1.close();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into " + DATABASE_NAME + "(key,value) values(?,?)" , new Object[]{key,value});
        db.close();
    }

    public String getValueByKey(String selectionArgs){
        SQLiteDatabase db = this.getReadableDatabase();
        int i = 1;

        Cursor cursor = db.query(DATABASE_NAME,null,"key=?",new String[]{selectionArgs},null,null,null);
        if(cursor == null|| !cursor.moveToFirst()){
            cursor.close();//关闭结果集
            db.close();//关闭数据库对象
            return null;
        }
        String value =  cursor.getString(2);
        cursor.close();//关闭结果集
        db.close();//关闭数据库对象
        return value;
    }

    //在数据库中删除指定id的taskEntry条目，并返回
    public String deleteValueByKey(String key){
        String delone = getValueByKey(key);
        SQLiteDatabase db = this.getWritableDatabase();
        //删除条件
        String whereClause = "key=?";
        //删除条件参数
        String[] whereArgs = {key};
        //执行删除
        db.delete(DATABASE_NAME,whereClause,whereArgs);
        System.out.println("执行删除");
        db.close();
        return delone;
    }

    //将任务的finished条目设置为1
    public String changeValueByKey(String key,String value){
        SQLiteDatabase db = this.getWritableDatabase();
        String delone;
        //实例化内容值
        ContentValues values = new ContentValues();
        //在values中添加内容
        values.put("value ",value);
        //修改条件
        String whereClause = "key=?";
        //修改添加参数
        String[] whereArgs={key};
        //修改
        db.update(DATABASE_NAME,values,whereClause,whereArgs);
        db.close();
        delone = getValueByKey(key);
        return delone;
    }
}
