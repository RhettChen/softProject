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

    public int getCredit(){
        switch(level){
            case 0: return 10;
            case 1: return 20;
            case 2: return 50;
            case 3: return 100;
        }
        return 0;
    }

    public String getTypeString(){
        switch(type){
            case 31:case 30: return "年度任务";
            case 21:case 20: return "每月任务";
            case 11:case 10: return "每周任务";
            case 1:case 0:return "每日任务";
        }
        return "";
    }
}
