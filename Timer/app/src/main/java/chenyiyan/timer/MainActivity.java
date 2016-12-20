package chenyiyan.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    static MyDBOpenHelper taskList ;//= new MyDBOpenHelper(getContext(),"task",null,1);
    static MyDBOpenHelper rewardList; //= new MyDBOpenHelper(getContext(),"reward",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

       /*set it to be full screen*/

        ListView list = (ListView) findViewById(R.id.ListView01);

        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<30;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.activity2_1_4);//图像资源的ID
            map.put("ItemTitle", "任务 "+i);
            map.put("ItemText", "任务描述");
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
                setTitle("点击第"+arg2+"个项目");
            }
        });

        //添加长按点击
        list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("长按菜单-ContextMenu");
                menu.add(0, 0, 0, "弹出长按菜单0");
                menu.add(0, 1, 0, "弹出长按菜单1");
            }
        });
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
        return super.onContextItemSelected(item);
    }
}
