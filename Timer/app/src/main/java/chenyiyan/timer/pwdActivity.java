package chenyiyan.timer;

/**
 * Created by samue_000 on 12/24/2016.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class pwdActivity extends Activity{

    /** 滑动锁对象 **/
    private GesturePwdView mGesturePwdView;

    /** 提示文本 **/
    private TextView mtipsTv;

    private LinearLayout tipsIvLayout;

    private Animation shakeAnimation;

   private SharedPreferencesUtil spUtils;

    /** 手势密码 设置/未设置 textview标记 **/
   private String gesturePwdStr;

    /** 已设置过手势 并且修改手势验证成功,重启activity准备再次记录手势标示 **/
    private boolean isChangeValidateSuccess = false;

    //進度條的參數
    private RoundedRectProgressBar bar;
    private  int progress; //完成進度

    //調用來源
    boolean isFromMain;
    boolean setPwd;
    MyDBOpenHelper taskList ;//= new MyDBOpenHelper(getContext(),"task",null,1);
    MyDBOpenHelper taskUsedList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {   //if( myflag){setPswNull();myflag = false;}
        //判斷有沒有密碼，沒有密碼的話直接跳入主活動
        taskList = new MyDBOpenHelper(this,"task",null,1);
        taskUsedList = new MyDBOpenHelper(this,"taskUsed",null,1);
        spUtils = new SharedPreferencesUtil(pwdActivity.this);//用於保存密碼的類
        gesturePwdStr = spUtils.readStringValue(SharedPreferencesUtil.Key.GESTURE_PWD_TAG, "");//讀取密碼
        //判斷有沒有密碼，若沒有，直接結束進程
        isFromMain = getIntent().getBooleanExtra("isFromMain", false);
        setPwd = getIntent().getBooleanExtra("setPwd", false);
        progress = percentageOfDailyTask();
        if(gesturePwdStr.length() == 0 && isFromMain && !setPwd)
            finish();
        System.out.println("通过了finish");
        //進入輸入密碼界面
        requestWindowFeature(Window.FEATURE_NO_TITLE);//让app里面没有标题框
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pwd_gesture);
        initView();
        setListener();
        initData();
    }

    private void initView()
    {
        //畫手勢的地方，只要在GesturePwdView中畫好圖，就能綁到xml中
        mGesturePwdView = (GesturePwdView)findViewById(R.id.setting_gesture_pwd_gesture_view);
        //“繪製解鎖密碼”的文字
         mtipsTv = (TextView)findViewById(R.id.setting_gesture_pwd_tips_tv);
        isChangeValidateSuccess = getIntent().getBooleanExtra("isChangeValidateSuccess", false);
        System.out.println("在init这里" + isChangeValidateSuccess);
        if(isChangeValidateSuccess == true)mtipsTv.setText("设置新密码");
        else mtipsTv.setText("绘制密码");
        //上半部分的九個圓圈（此部分非常重要，若刪去，在點擊“設置密碼”時程序將會閃退
        tipsIvLayout = (LinearLayout)findViewById(R.id.gesture_pwd_iv_layout);
        //用於綁定進度條
        bar = (RoundedRectProgressBar) findViewById(R.id.bar);
    }

    private void setListener()
    {
        //啟用“返回”和“忘記密碼”兩個按鈕
        // mBackIv.setOnClickListener(this);

        // 左右移动动画
        shakeAnimation = AnimationUtils.loadAnimation(pwdActivity.this, R.anim.shake);
        // 手势完成后回调
        //這裡調用了GesturePwdView.java，運用它返回的值進行判斷
        mGesturePwdView.setOnGestureFinishListener(new GesturePwdView.OnGestureFinishListener()
        {

            @Override
            public void OnGestureFinish(int status, String key, List<Integer> linedCycles)
            {
                System.out.println("status是："+GesturePwdView.GesturePwdStatus.VALIDATE_SUCCESS);
                switch (status)
                {
                    case GesturePwdView.GesturePwdStatus.NUMBER_ERROR:// 连接点数少于4个
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_number));
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN:// 第一次手势绘制成功 二次验证状态
                        mtipsTv.setText("绘制相同的新密码");//getResources().getString(R.string.setting_gesture_pwd_drawing_again));
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN_ERROR:// 手势设置失败 二次验证状态
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_drawing_inconsistent));
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_AGAIN_SUCCESS:// 手势设置成功 二次验证状态
                        spUtils.saveStringValue(SharedPreferencesUtil.Key.GESTURE_PWD_TAG, key);
                        //Toast用於跳出一個透明的小提示，短時間後自動消失
                        if (isChangeValidateSuccess)
                        {
                            Toast.makeText(pwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(pwdActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        }
                        pwdActivity.this.finish();
                        break;
                    //第一次界面
                    case GesturePwdView.GesturePwdStatus.VALIDATE_SUCCESS:// 輸入密碼成功
                        pwdActivity.this.finish();
                        System.out.println("setPwd 是 "+setPwd);
                        //進入下一個界面
                        if(isFromMain && !setPwd)
                            return;
                        else {
                            Intent intent =
                                    new Intent(pwdActivity.this, pwdActivity.class);
                            intent.putExtra("isChangeValidateSuccess", true);
                            startActivity(intent);
                        }
                        break;
                    case GesturePwdView.GesturePwdStatus.VALIDATE_ERROR:// 輸入密碼失敗
                        mtipsTv.setText(getResources().getString(R.string.setting_gesture_pwd_validate_error));
                        mtipsTv.startAnimation(shakeAnimation);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void initData(){
        spUtils = new SharedPreferencesUtil(pwdActivity.this); //調用存儲class

        isChangeValidateSuccess = getIntent().getBooleanExtra("isChangeValidateSuccess", false);

        gesturePwdStr = spUtils.readStringValue(SharedPreferencesUtil.Key.GESTURE_PWD_TAG, "");
        if (gesturePwdStr.length() > 0 && isChangeValidateSuccess == false)// 已设置密码但还没有与原密码验证的状态 相当于设置成功后第一次进入
        {
            tipsIvLayout.setVisibility(View.INVISIBLE);
            mGesturePwdView.validationStatus = GesturePwdView.GesturePwdStatus.VALIDATE;
            mGesturePwdView.tempResString = new StringBuffer(gesturePwdStr);
        }
        //設置進度條
        //TODO 請在下方設置process的值
        bar.setProgress(progress);
    }

    public  void setPswNull(){
        spUtils = new SharedPreferencesUtil(pwdActivity.this);//用於保存密碼的類
        spUtils.readStringValue(SharedPreferencesUtil.Key.GESTURE_PWD_TAG, "");//讀取密碼
        spUtils.saveStringValue(SharedPreferencesUtil.Key.GESTURE_PWD_TAG, "");//將XXX存入密碼，當XXX為空時，代表刪除密碼
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

}
