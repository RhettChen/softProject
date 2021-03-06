package chenyiyan.timer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/12/19.
 */

public class HistoryList extends Dialog{

    public HistoryList(Context context) {
        super(context);
    }

    public HistoryList(Context context, int theme) {
        super(context, theme);
    }


     public static class Builder{
         private Context context;
         private ListView listView;
         private TextView headView;
         private int selectionItem;

         private taskEntry[] taskList;
         private int length;
         private String title;
         public Builder(Context context,taskEntry[] tl,int lt,String ttl) {
             this.context = context;
             this.taskList = tl;
             this.length = lt;
             this.title = ttl;


         }
         public CustomDialog create() {

             LayoutInflater inflater = (LayoutInflater) context
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             // instantiate the dialog with the custom Theme
             final CustomDialog dialog = new CustomDialog(context);
             View layout = inflater.inflate(R.layout.mlist, null);
             layout.setBackgroundColor(Color.rgb(65,105,255));
             dialog.addContentView(layout, new LayoutParams(
                     LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
             // set the dialog title
             headView = (TextView)layout.findViewById(R.id.mlist_textView);
             headView.setText(title);
            listView = (ListView)layout.findViewById(R.id.mlistListView);
             dialog.setContentView(layout);
             /*List<Map<String, Object>> myData = getData(this.taskList,this.length);
             SimpleAdapter adapter = new SimpleAdapter(context,myData,R.layout.mlist,
                     new String[]{"context"},
                     new int[]{R.id.activity31textView1});*/
             ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();
             for(int i = 0;i < length; i++){
                 HashMap<String,Object> map = new HashMap<String,Object>();
                 map.put("Item1",taskList[i].getContext());
                 listItem.add(map);
             }

             SimpleAdapter listItemAdapter = new SimpleAdapter(context,listItem,//数据源
                     R.layout.history_listview,//ListItem的XML实现
                     //动态数组与ImageItem对应的子项
                     new String[] {"Item1"},
                     //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                     new int[] {R.id.history_button1}
             );

             //添加并且显示
             listView.setAdapter(listItemAdapter);

             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                 @Override
                 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                         long arg3) {
                     View view = listView.getChildAt(arg2);
                     selectionItem = arg2;
                     Button btnFinish = (Button) view.findViewById(R.id.activity3ListViewButton2);
                     btnFinish.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                         }
                     });

                 }
             });
             dialog.setContentView(layout);

             return dialog;
         }

         private List<Map<String, Object>> getData(taskEntry[] tList,int lgth) {
             List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
             for(int i = 0;i < lgth; ++ i) {
                 Map<String, Object> map = new HashMap<String, Object>();
                 map.put("context", tList[i].getContext()+"  "+tList[i].getDate());
                 list.add(map);
             }


             return list;
         }


     }

}
