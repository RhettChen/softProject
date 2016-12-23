package chenyiyan.timer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Main31Activity extends AppCompatActivity implements OnGestureListener {
    Button bt3;
    Button bt2;
    TextView mytext;
    private ViewFlipper viewFlipper;
    private GestureDetector detector; //手势检测

    Animation leftInAnimation;
    Animation leftOutAnimation;
    Animation rightInAnimation;
    Animation rightOutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main31);
        viewFlipper = (ViewFlipper)findViewById(R.id.activity31viewFlipper);
        detector = new GestureDetector(this);

        //往viewFlipper添加View
        viewFlipper.addView(getImageView(R.drawable.activity3_1_3));
        viewFlipper.addView(getImageView(R.drawable.activity2_1_30));
        viewFlipper.addView(getImageView(R.drawable.activity2_1_24));
        viewFlipper.addView(getImageView(R.drawable.activity3_1_7));
        viewFlipper.addView(getImageView(R.drawable.activity3_1_3));
        viewFlipper.addView(getImageView(R.drawable.activity2_1_3));

        //动画效果
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
        rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);

        ImageButton bt1 ;
        bt1 = (ImageButton)findViewById(R.id.activtiy31imageButton);
        bt2 = (Button)findViewById(R.id.activity31button2);
        bt3 = (Button)findViewById(R.id.activtiy31button3);
        //mytext = (TextView)findViewById(R.id.activity3textView1);


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

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    private ImageView getImageView(int id){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(id);
        return imageView;
    }


    public boolean onTouchEvent(MotionEvent event) {

        return this.detector.onTouchEvent(event); //touch事件交给手势处理。
    }


    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }


    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
       // Log.i(TAG, "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));



        if(e1.getX()-e2.getX()>120){
            viewFlipper.setInAnimation(leftInAnimation);
            viewFlipper.setOutAnimation(leftOutAnimation);
            viewFlipper.showNext();//向右滑动
            return true;
        }else if(e1.getX()-e2.getY()<-120){
            viewFlipper.setInAnimation(rightInAnimation);
            viewFlipper.setOutAnimation(rightOutAnimation);
            viewFlipper.showPrevious();//向左滑动
            return true;
        }
        return false;
    }


    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }


    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }


    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }


    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

}
