package com.example.ex04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listView;
	private MySimpleAdapter adapter;
	
	private ArrayList<HashMap<String, Object>> mDataList = new ArrayList<HashMap<String, Object>>();
	
	private void setData() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_launcher);
		map.put("name", "张三");
		map.put("class", "电政");
		mDataList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_launcher);
		map.put("name", "李四");
		map.put("class", "树莓");
		mDataList.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_launcher);
		map.put("name", "王五");
		map.put("class", "计应");
		mDataList.add(map);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        	
    		setData();
    		
    		listView = (ListView)findViewById(R.id.listview_item);
    		
    		adapter = new MySimpleAdapter (this,
    				mDataList,
    				R.layout.item_main,
    				new String[] {"img","name", "class" },
    				new int[]{R.id.icon, R.id.item_name, R.id.item_else}
    				);
    		listView.setAdapter(adapter);
    		
    		OnItemClickListener mItemClickListener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("name", ((TextView) arg1.findViewById(R.id.item_name)).getText().toString());
					bundle.putString("class", ((TextView) arg1.findViewById(R.id.item_else)).getText().toString());
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, TestActivity.class);
					
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
					
				}
    	    	
    	    };
    		
    		listView.setOnItemClickListener(mItemClickListener);
    		
    		OnItemLongClickListener mItemLongClickListener = new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					mDataList.remove(arg2);
					adapter.notifyDataSetChanged();
					return false;
				}
    			
    		};
    		listView.setOnItemLongClickListener(mItemLongClickListener);
    }
    
    private class MySimpleAdapter extends SimpleAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = super.getView(position, convertView, parent);
			
			ImageButton btn=(ImageButton) v.findViewById(R.id.icon);
			   btn.setTag(position);
			   btn.setOnClickListener(new OnClickListener() {
			    
			    @Override
			    public void onClick(View v) {
			     // TODO Auto-generated method stub
			    	
			    	mDataList.remove((int)v.getTag());
					notifyDataSetChanged();
			     //Toast.makeText(getApplicationContext(), "单击我了"+v.getTag(), 1).show();
			     
			    }
			   });
			   
			   return v;
		}

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
