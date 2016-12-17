package chenyiyan.timer;

/**
 * Created by lenovo on 2016/12/17.
 */

public class taskEntry {
    int id;
    int type;
    String context;
    int finish;
    int level;
    String date;

    taskEntry(int ty,String ct,int fn,int lv,String dt){
        id = -1;
        type = ty;
        context = ct;
        finish = fn;
        level = lv;
        date =dt;
    }
    public int setID(int i){
        this.id = i;
        return this.id;
    }
    public int getID(){
        return this.id;
    }

    public int getType(){
        return this.type;
    }

    public String getContext(){
        return this.context;
    }

    public int getFinish(){
        return this.finish;
    }

    public int getLevel(){
        return this.level;
    }

    public String getDate(){
        return this.date;
    }
}
