package com.example.ex05;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private Button mRegisterBt;
	private Button msendBt;
	private EditText mMsgEt;
	private Boolean isRegistered;
	private MyBroadcastReceiver mMyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mRegisterBt = (Button)findViewById(R.id.mRegisterBt);
        msendBt = (Button)findViewById(R.id.sendBt);
        mMsgEt = (EditText)findViewById(R.id.msg);
        isRegistered = false;
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        
        mRegisterBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isRegistered) {
					unregisterReceiver(mMyBroadcastReceiver);
					mRegisterBt.setText("注册广播接收器");
					isRegistered = false;
				} else {
					registerReceiver(mMyBroadcastReceiver, new IntentFilter("SYSU_ANDROID_2014"));
					mRegisterBt.setText("注销广播接收器");
					isRegistered = true;
					
				}
			}
          });
        
        msendBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("SYSU_ANDROID_2014");
				intent.putExtra("msg", mMsgEt.getText().toString());
				sendBroadcast(intent);
			}	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
