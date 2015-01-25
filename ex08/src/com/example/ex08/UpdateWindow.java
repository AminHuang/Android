package com.example.ex08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UpdateWindow extends Activity implements OnClickListener {
	
	private DB mDB;
	private Cursor mCursor;
	
	private EditText add_name;
	private EditText add_telephone;
	private RadioButton man;
	private RadioButton womam;
	private EditText remark;
	private Button sure;
	private Button cancel;
	private  RadioGroup group;
	
	private LinearLayout layout;
	
	private int ID = 0;
	private String name;
	private String sex="";
	private String phone;
	private String remarks="";
	
	String TABLE_NAME = "phone_number";
	
	Bundle mBundle = new Bundle();
	Intent mIntent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		mDB = new DB(this);
		mCursor = mDB.select(TABLE_NAME);
		
		getView();

		
		Bundle bundle = this.getIntent().getExtras();
		ID = Integer.parseInt(bundle.getString("id"));
		name = bundle.getString("name");
		sex = bundle.getString("sex");
		phone = bundle.getString("phone");
		remarks = bundle.getString("remarks");
		
		setValue();

		
	}
	
	public void getView(){
    	add_name = (EditText)findViewById(R.id.add_name);
    	add_telephone = (EditText)findViewById(R.id.add_telephone);
//    	remark = (EditText)findViewById(R.id.remark);
    	sure = (Button)findViewById(R.id.sure);
    	cancel = (Button)findViewById(R.id.cancel);
//    	group = (RadioGroup)this.findViewById(R.id.radioGroup1);
    	
    	sure.setOnClickListener(this);
    	cancel.setOnClickListener(this);
    }
	
	public void setValue() {
		add_name.setText(name);
		add_telephone.setText(phone);
//		remark.setText(remarks);
//		if (sex.equals("男")) {
//			
//		}
	}
	
	public void getValue() {
    	name = add_name.getText().toString();
    	
    	phone = add_telephone.getText().toString();
    }
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//finish();
		return true;
	}
	
	
	public void updateTask(int id, String name, String sex, String phone, String remark) {
		if (name.equals("")) {
			Toast.makeText(this, "没有内容！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mDB.update(id, name, sex, phone, remark) ;
		
	}
	
	public void update() {
		getValue();
		updateTask(ID, name, sex, phone, remarks);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sure:
			update();
			startActivity(new Intent(UpdateWindow.this,MainActivity.class));
			finish();
			break;
		case R.id.cancel:
			startActivity(new Intent(UpdateWindow.this,MainActivity.class));
			finish();
			break;
		default:
			break;
		}
		finish();
	}

}
