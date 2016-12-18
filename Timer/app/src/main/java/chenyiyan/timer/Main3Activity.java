package chenyiyan.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    TextView text1;
    taskEntry mytask1;
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
        text1 = (TextView)findViewById(R.id.activity3textView1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
