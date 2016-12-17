package chenyiyan.timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by lenovo on 2016/12/16.
 */
//数据库类
public class MyDBOpenHelper extends SQLiteOpenHelper {
    //public static final int DATABASE_VERSION = 1;
    //public static final String DATABASE_NAME = "task.db";
    private String DATABASE_NAME;


   //构造函数，DATABASE_NAME为数据库名字，version为版本，构建时mDB = new MyDBOpenHelper(this,"task",null,1);，即可；
    public MyDBOpenHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, name, factory, version);
        DATABASE_NAME = name;
    }
   //第一次打开程序的时候才会跑！
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+DATABASE_NAME+"(ID integer primary key,item TEXT,type TEXT,context TEXT,finish TEXT,level TEXT,date TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    // 插入taskEntry类
    public void insert(taskEntry newone){
        int mid;
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cur = db1.rawQuery("SELECT MAX(ID) FROM "+DATABASE_NAME, null);
        cur.moveToFirst();
        mid = cur.getInt(0);
        cur.close();
        db1.close();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into " + DATABASE_NAME + "(item,type,context,finish,level,date) values(?,?,?,?,?,?)" , new Object[]{String.valueOf(mid),newone.getType(),newone.getContext(),newone.getFinish(),newone.getLevel(),newone.getDate()});
        db.close();
        newone.setID(mid);
        System.out.println(mid);
    }

    //通过ID查找taskEntry类
    public taskEntry getTaskByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        //int i = 0;
        String selection = "item=?";
        String[] selectionArgs = { String.valueOf(id)};
        Cursor cursor = db.query(DATABASE_NAME,null,selection,selectionArgs,null,null,null);
        if(cursor == null || !cursor.moveToFirst()){
            cursor.close();//关闭结果集
            db.close();//关闭数据库对象
            return null;
        }
      //  while(i < id){cursor.moveToNext();i++;}
        int thisId =  Integer.valueOf(cursor.getString(1)).intValue();
        taskEntry targetTask = new taskEntry(cursor.getInt(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6));
        targetTask.setID(thisId);
        cursor.close();//关闭结果集
        db.close();//关闭数据库对象
        return targetTask;
    }

    //在数据库中删除指定id的taskEntry条目，并返回
    public taskEntry deleteTaskByID(int id){
        taskEntry delone = getTaskByID(id);
        SQLiteDatabase db = this.getWritableDatabase();
        //删除条件
        String whereClause = "item=?";
        //删除条件参数
        String[] whereArgs = {String.valueOf(id)};
        System.out.println("执行删除 "+String.valueOf(id));
        //执行删除
        db.delete(DATABASE_NAME,whereClause,whereArgs);
        System.out.println("执行删除");
        db.close();
        return delone;
    }

    //将任务的finished条目设置为1
    public taskEntry finishByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        taskEntry delone;
        //实例化内容值
        ContentValues values = new ContentValues();
        //在values中添加内容
        values.put("finish",1);
        //修改条件
        String whereClause = "item=?";
        //修改添加参数
        String[] whereArgs={String.valueOf(id)};
        //修改
        db.update(DATABASE_NAME,values,whereClause,whereArgs);
        db.close();
        delone = getTaskByID(id);
        return delone;
    }
}
