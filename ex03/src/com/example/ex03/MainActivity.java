package com.example.ex03;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText user;
	private EditText password;
	
	private ImageButton mImage;
	private Button cancel;
	
	private Button mBtn2;
	private Context mContext;
	
	//private ImageButton mImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        user = (EditText)findViewById(R.id.user);
		password = (EditText)findViewById(R.id.password);
		cancel = (Button)findViewById(R.id.cancel);
		
		mImage = (ImageButton)findViewById(R.id.ImageBtn2);
		//mImage2 = (ImageButton)findViewById(R.id.ImageBtn3);
		
		//mImage.setOnLongClickListener(new setOnLongClickListener)
		
		
		
		mContext = this;
		
		
		
		OnClickListener ImageBtn2_Listener = new OnClickListener() {
			public void onClick(View v) {
				String userName = user.getText().toString();
				String Password = password.getText().toString();
				if (userName.equals("android") && Password.contains("mima")) {
					user.setVisibility(View.GONE);
					password.setVisibility(View.GONE);
					mImage.setImageDrawable(getResources().getDrawable(R.drawable.login_success));
					
				} else {
					mImage.setImageDrawable(getResources().getDrawable(R.drawable.login_try));
					password.requestFocus();
					password.setText("密码错误");
				}
			}
		};
		
		OnClickListener Btn2_Listener = new OnClickListener() {
			public void onClick(View v) {
				Toast toast = Toast.makeText(mContext, "动态添加按钮", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
				toast.show();
			}
		};
		
        OnLongClickListener ImageBtn2_long_Listener = new OnLongClickListener() {
			public boolean onLongClick(View v) {
				
				LinearLayout mLineraLayout;
				mLineraLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
				TextView tmp = new TextView(mContext);
				tmp.setText("动态添加的textview");
				mLineraLayout.addView(tmp);
				
				return false;
			}
		};
		
		
		
		OnClickListener Cancel_Listener = new OnClickListener() {
			public void onClick(View v) {
				user.setVisibility(View.VISIBLE);
				password.setVisibility(View.VISIBLE);
				mImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
				user.setText("请输入用户名...");
				password.setText("请输入密码...");
				
				
				LinearLayout mLineraLayout;
				mLineraLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
				TextView tmp = new TextView(mContext);
				tmp.setText("动态添加的textview");
				mLineraLayout.addView(tmp);
			}
		};
		
		mImage.setOnClickListener(ImageBtn2_Listener);
		cancel.setOnClickListener(Cancel_Listener);
		
		mImage.setOnLongClickListener(ImageBtn2_long_Listener);
		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
