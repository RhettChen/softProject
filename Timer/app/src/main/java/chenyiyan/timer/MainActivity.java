package chenyiyan.timer;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static MyDBOpenHelper taskList ;//= new MyDBOpenHelper(getContext(),"task",null,1);
    static MyDBOpenHelper taskUsedList;
    MyDBOpenHelper rewardList; //= new MyDBOpenHelper(getContext(),"reward",null,1);
    static MyDBOpenHelper rewardUsedList;
    static MyEasyDataHelper keyValueList;
    CustomDialog.Builder builder;
    ListView list;
    int cursection=0;
    Button creditsBtn;
    CustomAnotherDialog.Builder AnotherDialog;
    HistoryList.Builder historyBuilder;
    ArrayList<taskEntry>tasksnow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        taskList = new MyDBOpenHelper(this,"task",null,1);
        taskUsedList = new MyDBOpenHelper(this,"taskUsed",null,1);
        rewardList = new MyDBOpenHelper(this,"reward",null,1);
        rewardUsedList = new MyDBOpenHelper(this,"rewardUsed",null,1);
        keyValueList = new MyEasyDataHelper(this,"keyValueList",null,1);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date= sDateFormat.format(new java.util.Date());
        renewlist(date);
        Intent intent = new Intent(MainActivity.this, pwdActivity.class);
        intent.putExtra("isFromMain", true);
        startActivity(intent);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.ListView01);

        //生成动态数组，加入数据
        creditsBtn = (Button)findViewById(R.id.currentCredits);
        Button bt,bt6,bt7;
        bt7 = (Button)findViewById(R.id.button7);
        bt7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                taskEntry[] taskList;
                int length = 0;
                int tmpLength = 0;
                taskList = new taskEntry[100];
                String[] type = new String[]{String.valueOf(cursection*10),String.valueOf(cursection*10+1)};
                taskEntry newOne = taskUsedList.getTaskByCondition("type=?", new String[]{type[0]},0);
                taskEntry newOne1 = taskUsedList.getTaskByCondition("type=?", new String[]{type[1]},0);
                while(newOne!=null || newOne1!=null){
                    if(newOne!=null)taskList[length++] = newOne;
                    if(newOne1!=null)taskList[length++] = newOne1;
                    tmpLength++;
                    newOne = taskUsedList.getTaskByCondition("type=?", new String[]{type[0]},tmpLength);
                    newOne1 = taskUsedList.getTaskByCondition("type=?", new String[]{type[1]},tmpLength);
                }
                System.out.println("长度："+length);
                historyBuilder = new HistoryList.Builder(MainActivity.this,taskList,length,"过去所得奖励");
                historyBuilder.create().show();
            }
        });

        bt6 = (Button)findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, pwdActivity.class);
                intent.putExtra("isFromMain", true);
                intent.putExtra("setPwd", true);
   //             myflag = true;

                startActivity(intent);

            }
        });
        bt = (Button)findViewById(R.id.button5);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,Main3Activity.class));
            }
        });
        Button bt2;
        bt2 = (Button)findViewById(R.id.button);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new CustomDialog.Builder(MainActivity.this);
                builder.setTitle("新建任务");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int which) {
                        taskEntry myReward;
                        int type=0,level=0;
                        String context;
                        String date;
                        boolean addflag = true;
                        if(builder.typeLong.isChecked()){
                            if(builder.typeYear.isChecked()){
                                type = 31;
                            }
                            else if(builder.typeMonth.isChecked()){
                                type = 21;
                            }
                            else if(builder.typeWeek.isChecked()){
                                type = 11;
                            }
                            else if(builder.typeDay.isChecked()){
                                type = 1;
                            }
                            else addflag = false;
                        }

                        if(!builder.typeLong.isChecked()){
                            if(builder.typeYear.isChecked()){
                                type = 30;
                            }
                            else if(builder.typeMonth.isChecked()){
                                type = 20;
                            }
                            else if(builder.typeWeek.isChecked()){
                                type = 10;
                            }
                            else if(builder.typeDay.isChecked()){
                                type = 0;
                            }
                            else addflag = false;
                        }

                        if(builder.level0.isChecked())level=0;
                        else if(builder.level1.isChecked())level=1;
                        else if(builder.level2.isChecked())level=2;
                        else if(builder.level3.isChecked())level=3;
                        else addflag = false;

                        context =(builder.mycontext.getText()).toString();
                        if(context == null || context.length() > 15)addflag = false;


                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
                        date= sDateFormat.format(new java.util.Date());
                        if(date == null){addflag = false;}
                        if(addflag){
                            myReward = new taskEntry(type,context,0,level,date);
                            taskList.insert(myReward);
                            renewDisplay();
                        }

                        if(addflag)dialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });
        ((Button)findViewById(R.id.button2)).setBackground(getResources().getDrawable(R.drawable.activity3_1_666));
        bt = (Button)findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)findViewById(R.id.button0)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button1)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button2)).setBackground(getResources().getDrawable(R.drawable.activity3_1_666));
                ((Button)findViewById(R.id.button3)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                cursection=0;
                renewDisplay();
            }
        });
        bt = (Button)findViewById(R.id.button0);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)findViewById(R.id.button0)).setBackground(getResources().getDrawable(R.drawable.activity3_1_666));
                ((Button)findViewById(R.id.button1)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button2)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button3)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                cursection=1;
                renewDisplay();
            }
        });
        bt = (Button)findViewById(R.id.button3);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)findViewById(R.id.button0)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button1)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button2)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button3)).setBackground(getResources().getDrawable(R.drawable.activity3_1_666));
                cursection=2;
                renewDisplay();
            }
        });
        bt = (Button)findViewById(R.id.button1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)findViewById(R.id.button0)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button1)).setBackground(getResources().getDrawable(R.drawable.activity3_1_666));
                ((Button)findViewById(R.id.button2)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                ((Button)findViewById(R.id.button3)).setBackground(getResources().getDrawable(R.drawable.activity3_1_6));
                cursection=3;
                renewDisplay();
            }
        });
        renewDisplay();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
        return super.onContextItemSelected(item);
    }
    private void renewDisplay(){
        tasksnow=new ArrayList<taskEntry>();
        System.out.println("调用renewDisplay()"+String.valueOf(taskList.getCount()));
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        int uu=30;
        for(int i=0;i<taskList.getCount();i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            taskEntry task=taskList.getTaskByOrder(i);
            if (task.type/10!=cursection){
                continue;
            }
            --uu;
            if(task.finish==1)
                map.put("ItemImage", R.drawable.activity2_1_5);//图像资源的ID
            else
                map.put("ItemImage", R.drawable.activity2_1_4);//图像资源的ID
            map.put("ItemTitle", "任务 "+(i+1));
            map.put("ItemText", task.context);
            listItem.add(map);
            tasksnow.add(task);
        }
        for(int i=0;i<uu;++i){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", "");//图像资源的ID
            map.put("ItemTitle", "");
            map.put("ItemText", "");
            listItem.add(map);
        }

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.list_item,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemTitle", "ItemText"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemText}
        );

        //添加并且显示
        list.setAdapter(listItemAdapter);

        //添加点击
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2>=tasksnow.size())
                    return;
                taskList.finishByID(tasksnow.get(arg2).id,1-tasksnow.get(arg2).finish);

                renewDisplay();
                setTitle("点击第"+arg2+"个项目");
            }
        });

        //添加长按点击
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int arg2=position;
                if (arg2>=tasksnow.size())
                    return true;
                if(tasksnow.get(arg2).getFinish()==1){
                    dialog1_1(tasksnow.get(arg2));
                }

               // renewDisplay();
                return true;
            }
        });

        creditsBtn.setText("当前积分："+keyValueList.getValueByKey("credits"));
        /*taskEntry addOne;

        bt5.setText(MainActivity.keyValueList.getValueByKey("credits"));
        showRewardLength = 0;
        addOne=rewardList.getTaskByCondition("type=?",new String[]{"31"},showRewardLength+1);
        while(addOne!=null){
            showReward[showRewardLength++]=addOne;
            addOne = rewardList.getTaskByCondition("type=?",new String[]{"31"},showRewardLength+1);
        }
        renewDisplay(showReward,showRewardLength);*/
    }

    private void  dialog1_1(taskEntry aimed){
        //先new出一个监听器，设置好监听
        AnotherDialog=new CustomAnotherDialog.Builder(MainActivity.this,aimed);
        AnotherDialog.setTitle("所需积分："+ aimed.getCredit());
        AnotherDialog.setPositiveButton("兑换", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                taskEntry handledOne = AnotherDialog.getTask();
                int currentCredits = Integer.parseInt(keyValueList.getValueByKey("credits"));
                if(handledOne.getCredit() < currentCredits){
                    taskList.deleteTaskByID(handledOne.getID());
                    taskUsedList.insert(handledOne);
                    currentCredits += handledOne.getCredit();
                    keyValueList.changeValueByKey("credits",""+currentCredits);
                    renewDisplay();
                    dialog.dismiss();
                }
                else  Toast.makeText(MainActivity.this,"所拥有积分不足", Toast.LENGTH_SHORT).show();
            }
        });

        AnotherDialog.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AnotherDialog.setNeutralButton("删除",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        taskList.deleteTaskByID(AnotherDialog.getTask().getID());
                        renewDisplay();
                        dialog.dismiss();
                    }
                });

        AnotherDialog.create().show();

    }

    public int percentageOfDailyTask(){
        float taskUsed;
        float taskIng;
        int num;
        String nowDate;
        taskUsed = taskUsedList.numOfSection("type=?",new String[]{"1"})+taskUsedList.numOfSection("type=?",new String[]{"0"});
        taskIng = taskList.numOfSection("type=?",new String[]{"1"})+taskUsedList.numOfSection("type=?",new String[]{"0"});
        num = (int)((taskUsed/(taskIng+taskUsed))*100);
        System.out.println("taskUsed "+taskUsed+"taskIng "+taskIng+"比例："+num);
        return num;
    }
    private void renewlist(String date){
        int i = 1;
        taskEntry handleOne = taskList.getTaskByCondition("type=?",new String[]{"1"},i);
        i++;
        while(handleOne!=null){
            if(date.compareTo(handleOne.getDate())!=0)taskList.renewDateById(handleOne.getID(),date);
            handleOne = taskList.getTaskByCondition("type=?",new String[]{"1"},i);
            i++;
        }
        i=1;
        handleOne = taskList.getTaskByCondition("type=?",new String[]{"0"},i);
        i++;
        while(handleOne!=null){
            if(date.compareTo(handleOne.getDate())!=0)taskList.deleteTaskByID(handleOne.getID());
            handleOne = taskList.getTaskByCondition("type=?",new String[]{"0"},i);
            i++;
        }
        i=1;
        handleOne = taskUsedList.getTaskByCondition("type=?",new String[]{"1"},i);
        i++;
        while(handleOne!=null){
            if(date.compareTo(handleOne.getDate())!=0)taskUsedList.deleteTaskByID(handleOne.getID());
            handleOne = taskUsedList.getTaskByCondition("type=?",new String[]{"1"},i);
            i++;
        }
        i=1;
        handleOne = taskUsedList.getTaskByCondition("type=?",new String[]{"0"},i);
        i++;
        while(handleOne!=null){
            if(date.compareTo(handleOne.getDate())!=0)taskUsedList.deleteTaskByID(handleOne.getID());
            handleOne = taskUsedList.getTaskByCondition("type=?",new String[]{"0"},i);
            i++;
        }
    }
}
