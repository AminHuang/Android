package com.example.ex08;

import java.util.ArrayList;
import java.util.HashMap;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
                          implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	Button add;
	ArrayList<HashMap<String, Object>> listData;  
	SimpleAdapter listItemAdapter;
	ListView list;
	
	private DB mDB;
	private Cursor mCursor;
	String TABLE_NAME = "phone_number";
	int ID = 0;
	private String name;
	private String sex;
	private String phone;
	private String remarks;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        add = (Button)findViewById(R.id.add);
        mDB = new DB(this);
        mCursor = mDB.select(TABLE_NAME);
//        mDB.insert("2", "2", "2", "2");
        getAllData();
//        test();
		
        
        list = (ListView) findViewById(R.id.listView1);
        listItemAdapter = new SimpleAdapter(MainActivity.this,
        		listData,
        		R.layout.item_main,
        		new String[]{"name",  "phone"},
        		new int[] {R.id.name,  R.id.phone});
        
        add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,AddActivity.class));
				finish();
			}
        	
        });
        
        list.setAdapter(listItemAdapter); 
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
    }
    
    public void getAllData() {
    	Cursor fCursor = mDB.select(TABLE_NAME);
    	int columnsSize = fCursor.getColumnCount();
    	listData = new ArrayList<HashMap<String, Object>>();
    	while(fCursor.moveToNext()) {
    		HashMap<String, Object> map = new HashMap<String, Object>();
    		for (int i = 0; i < columnsSize; i++) {
    			map.put("name", fCursor.getString(1));
//    			map.put("sex", "(" + fCursor.getString(2) + ")");
    			map.put("phone", fCursor.getString(3));
//    			map.put("remaks", fCursor.getString(4));
    		}
    		listData.add(map);
    	}
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		mCursor.moveToPosition(position);
		ID = mCursor.getInt(0);
		deleteFinishTask();
		listData.remove(position);
		listItemAdapter.notifyDataSetChanged();
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		mCursor.moveToPosition(position);
		ID = mCursor.getInt(0);
		name = mCursor.getString(1);
		sex = mCursor.getString(2);
		phone = mCursor.getString(3);
		remarks = mCursor.getString(4);
		Intent mIntent = new Intent();
		mIntent.setClass(MainActivity.this, UpdateWindow.class);
		Bundle tBumdle = new Bundle();
		tBumdle.putString("id", Integer.toString(ID));
		tBumdle.putString("name", name);
		tBumdle.putString("sex", sex);
		tBumdle.putString("phone", phone);
		tBumdle.putString("remarks", remarks);
		mIntent.putExtras(tBumdle);
		startActivity(mIntent);
		finish();
	}
	
	public void deleteFinishTask() {
		if (ID == 0) {
			return;
		}
		
		mDB.delete(TABLE_NAME,ID);
		Toast.makeText(this, "Delete Successed!", Toast.LENGTH_SHORT).show();
		
	}
}
