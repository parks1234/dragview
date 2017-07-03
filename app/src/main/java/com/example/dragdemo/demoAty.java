package com.example.dragdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bbb on 2017/7/3.
 */

public class demoAty  extends Activity {
    private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();
    LinearLayout llt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        llt= (LinearLayout) findViewById(R.id.llt);
        
        DragGridView mDragGridView = (DragGridView) findViewById(R.id.dragGridView);
        for (int i = 0; i < 9; i++) {
            HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
            itemHashMap.put("item_image",R.drawable.ic_launcher);
            itemHashMap.put("item_text", "拖拽 " + Integer.toString(i));
            dataSourceList.add(itemHashMap);
        }
        mDragGridView.setDragResponseMS(300);
        mDragGridView.setDP(60);

        final SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, dataSourceList,
                R.layout.demo_item, new String[] { "item_image", "item_text" },
                new int[] { R.id.item_image, R.id.item_text });

        mDragGridView.setAdapter(mSimpleAdapter);

        mDragGridView.setOnChangeListener(new DragGridView.OnChanageListener() {

            @Override
            public void onChange(int from, int to) {
//                llt.setVisibility(View.GONE);
                HashMap<String, Object> temp = dataSourceList.get(from);
                //直接交互item
//				dataSourceList.set(from, dataSourceList.get(to));
//				dataSourceList.set(to, temp);


                //这里的处理需要注意下
                if(from < to){
                    for(int i=from; i<to; i++){
                        Collections.swap(dataSourceList, i, i+1);
                    }
                }else if(from > to){
                    for(int i=from; i>to; i--){
                        Collections.swap(dataSourceList, i, i-1);
                    }
                }

                dataSourceList.set(to, temp);

                mSimpleAdapter.notifyDataSetChanged();


            }

            @Override
            public void onDel(int form) {
                llt.setVisibility(View.GONE);
                if (form!=-1){
                    dataSourceList.remove(form);
                    mSimpleAdapter.notifyDataSetChanged();
                }
               
                for (int i = 0; i < dataSourceList.size(); i++) {
                    System.out.println(dataSourceList.get(i).get("item_text"));
                }
            }

            @Override
            public void onShowDel(int form) {
llt.setVisibility(View.VISIBLE);
            }
        });

    }

}
