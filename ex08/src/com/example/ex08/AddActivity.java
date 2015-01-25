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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;


public class AddActivity extends ActionBarActivity {
	Button add;

	
	private DB mDB;
	private Cursor mCursor;
	String TABLE_NAME = "phone_number";
	
	private EditText add_name;
	private EditText add_telephone;
	private RadioButton man;
	private RadioButton womam;
	private EditText remark;
	private Button sure;
	private Button cancel;
	private  RadioGroup group;
	
	private String name;
	private String sex="";
	private String phone;
	private String remarks="";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        
        mDB = new DB(this);
        sex = "";
        getView();
        bind();

		

    }
    
    public void getView(){
    	add_name = (EditText)findViewById(R.id.add_name);
    	add_telephone = (EditText)findViewById(R.id.add_telephone);
//    	remark = (EditText)findViewById(R.id.remark);
    	sure = (Button)findViewById(R.id.sure);
    	cancel = (Button)findViewById(R.id.cancel);
//    	group = (RadioGroup)this.findViewById(R.id.radioGroup1);
    }
    
    public void insertToDb(String name, String sex, String phone, String remark) {
    	mDB.insert(name, sex, phone, remark);	
    }
    
    public void getValue() {
    	name = add_name.getText().toString();
    	
    	phone = add_telephone.getText().toString();
//    	remarks = remark.getText().toString();
    }
    
    public void bind() {
    	sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getValue();
				insertToDb(name, sex, phone, remarks);
//				mDB.insert("3", "3", "3", "3");
				startActivity(new Intent(AddActivity.this,MainActivity.class));
				finish();
				
			}
			
		});
    	cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AddActivity.this,MainActivity.class));
				finish();
				
			}
			
		});
    	
//    	//绑定一个匿名监听器
//    	group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				// TODO Auto-generated method stub
//				int radioButtonId = group.getCheckedRadioButtonId();
//				RadioButton rb = (RadioButton)AddActivity.this.findViewById(radioButtonId);
//				sex = rb.getText().toString();
//				
//			}});
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
}
