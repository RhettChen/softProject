package chenyiyan.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static MyDBOpenHelper taskList ;//= new MyDBOpenHelper(getContext(),"task",null,1);
    MyDBOpenHelper rewardList; //= new MyDBOpenHelper(getContext(),"reward",null,1);
    static MyDBOpenHelper rewardUsedList;
    static MyEasyDataHelper keyValueList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt;
        taskList = new MyDBOpenHelper(this,"task",null,1);
        rewardList = new MyDBOpenHelper(this,"reward",null,1);
        rewardUsedList = new MyDBOpenHelper(this,"rewardUsed",null,1);
        keyValueList = new MyEasyDataHelper(this,"keyValueList",null,1);
        bt = (Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,Main3Activity.class));
            }
        });
    }
}
