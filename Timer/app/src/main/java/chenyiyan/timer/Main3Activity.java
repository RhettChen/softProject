package chenyiyan.timer;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

//import android.icu.text.SimpleDateFormat;

public class Main3Activity extends AppCompatActivity {

    CustomDialog.Builder builder;
    CustomAnotherDialog.Builder AnotherDialog;
    HistoryList.Builder historyBuilder;
    MyDBOpenHelper rewardList;
    MyDBOpenHelper rewardUsedList;
    MyEasyDataHelper keyValueList;

    boolean textFlags[] = new boolean[12];
    taskEntry showReward[] = new taskEntry[100];
    int showRewardLength = 0;
    int targetReward;
    private ListView mylistView;
    Button bt1,bt2,bt3,bt4,bt5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rewardList = new MyDBOpenHelper(this,"reward",null,1);
        rewardUsedList = new MyDBOpenHelper(this,"rewardUsed",null,1);
        keyValueList = new MyEasyDataHelper(this,"keyValueList",null,1);
        String title = "年度任务";
        bt1 = (Button)findViewById(R.id.activity3button);
        bt2 = (Button)findViewById(R.id.activity3button2);
        bt3 = (Button)findViewById(R.id.activity3button3);
        bt4 = (Button)findViewById(R.id.activity3button4);
        bt5 = (Button)findViewById(R.id.activity3button5);
        bt4 .setText(title);
        keyValueList.changeValueByKey("credits",""+100);
        bt5.setText(keyValueList.getValueByKey("credits"));
        mylistView = (ListView)findViewById(R.id.activity3ListView1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        renewDisplay();


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new CustomDialog.Builder(Main3Activity.this);
                builder.setTitle("新建奖励");
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
                            rewardList.insert(myReward);
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

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskEntry[] taskList;
                int length = 0;
                taskList = new taskEntry[100];
                taskEntry newOne = rewardUsedList.getTaskByCondition("type=?", new String[]{"31"},0);
                while(newOne!=null){
                    taskList[length++] = newOne;
                    newOne = rewardUsedList.getTaskByCondition("type=?", new String[]{"31"},length);
                }
                System.out.println("长度："+length);
                historyBuilder = new HistoryList.Builder(Main3Activity.this,taskList,length,"过去所得奖励");
                historyBuilder.create().show();
            }
        });



    /*    texts[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textFlags[0]==true){
                    taskEntry current = showReward[0];
                    dialog1_1(current);
                    bt4.setText(MainActivity.keyValueList.getValueByKey("credits"));
                }
            }
        });*/

    }

    private void  dialog1_1(taskEntry aimed){
        //先new出一个监听器，设置好监听
       AnotherDialog=new CustomAnotherDialog.Builder(Main3Activity.this,aimed);
        AnotherDialog.setTitle("所需积分："+ aimed.getCredit());
        AnotherDialog.setPositiveButton("兑换", new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                taskEntry handledOne = AnotherDialog.getTask();
                int currentCredits = Integer.parseInt(keyValueList.getValueByKey("credits"));
                if(handledOne.getCredit() < currentCredits){
                    rewardList.deleteTaskByID(handledOne.getID());
                    rewardUsedList.insert(handledOne);
                    currentCredits -= handledOne.getCredit();
                    keyValueList.changeValueByKey("credits",""+currentCredits);
                    renewDisplay();
                    dialog.dismiss();
                }
                else  Toast.makeText(Main3Activity.this,"所拥有积分不足", Toast.LENGTH_SHORT).show();
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
                        rewardList.deleteTaskByID(AnotherDialog.getTask().getID());
                        renewDisplay();
                        dialog.dismiss();
                    }
                });

        AnotherDialog.create().show();

    }
    private void renewDisplay(){
        taskEntry addOne;
        System.out.println("调用renewDisplay()");
        bt5.setText(MainActivity.keyValueList.getValueByKey("credits"));
        showRewardLength = 0;
        addOne=rewardList.getTaskByCondition("type=?",new String[]{"31"},showRewardLength+1);
        while(addOne!=null){
            showReward[showRewardLength++]=addOne;
            addOne = rewardList.getTaskByCondition("type=?",new String[]{"31"},showRewardLength+1);
        }
        renewDisplay(showReward,showRewardLength);
    }


    private void renewDisplay(taskEntry[] taskarray,int Length){
        ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();
        for(int i = 0;i < Length; i++){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("Item1",null);
            map.put("Item2",taskarray[i].getContext());
            map.put("Item3",null);
            listItem.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.activity3_listview,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"Item1","Item2", "Item3"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.activity3ListView1,R.id.activity3ListViewButton1,R.id.activity3ListViewButton2}
        );

        //添加并且显示
        mylistView.setAdapter(listItemAdapter);

        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                View view = mylistView.getChildAt(arg2);
                targetReward = arg2;
                Button btnFinish = (Button) view.findViewById(R.id.activity3ListViewButton2);
                btnFinish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("在这里");
                        dialog1_1(showReward[targetReward]);
                    }
                });

                }
            });

        }






}
