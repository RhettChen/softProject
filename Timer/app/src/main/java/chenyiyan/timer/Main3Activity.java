package chenyiyan.timer;

import android.content.DialogInterface;
import android.content.Intent;
//import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import  java.text.SimpleDateFormat;

public class Main3Activity extends AppCompatActivity {
    TextView texts[];
    CustomDialog.Builder builder;
    boolean textFlags[] = new boolean[12];
    taskEntry showReward[] = new taskEntry[12];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bt1,bt2,bt3,bt4,bt5;
        bt1 = (Button)findViewById(R.id.activity3button);
        bt2 = (Button)findViewById(R.id.activity3button2);
        bt3 = (Button)findViewById(R.id.activity3button3);
        bt4 = (Button)findViewById(R.id.activity3button4);
        bt5 = (Button)findViewById(R.id.activity3button5);
        texts =new TextView[6];
        texts[5] = (TextView)findViewById(R.id.activity3textView6);
        texts[0] = (TextView)findViewById(R.id.activity3textView1);
        texts[1] = (TextView)findViewById(R.id.activity3textView2);
        texts[2] = (TextView)findViewById(R.id.activity3textView3);
        texts[3] = (TextView)findViewById(R.id.activity3textView4);
        texts[4] = (TextView)findViewById(R.id.activity3textView5);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        for(int i = 0;i < 12;++i){textFlags[i]=false;}
        for(int i = 0;i < 12;++i){
            showReward[i] = MainActivity.rewardList.getTaskByOrder(i);
            if(showReward[i]!=null && i<6)texts[i].setText(showReward[i].getContext());
            if(showReward[i]!=null)textFlags[i]=true;
        }


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
                        if(context == null)addflag = false;

                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
                         date= sDateFormat.format(new java.util.Date());
                        if(date == null){System.out.println("date是空的");addflag = false;}
                        if(addflag){
                            myReward = new taskEntry(type,context,0,level,date);
                            MainActivity.rewardList.insert(myReward);
                            for(int i = 0; i < 12;++i){
                                if(textFlags[i] == false){
                                    textFlags[i]=true;
                                    showReward[i] = myReward;
                                    texts[i].setText(myReward.getContext());
                                    System.out.println("在这里");
                                    break;
                                }
                            }
                        }

                        System.out.println("在这里1");
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
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main3Activity.this,Main31Activity.class));
            }
        });
    }

}
