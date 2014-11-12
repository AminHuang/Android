package com.example.ex04;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TestActivity extends Activity {
	private Button mBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        Bundle bundle = this.getIntent().getExtras();
		String mName = bundle.getString("name");
		String mClass = bundle.getString("class"); 
		
		TextView t = (TextView)findViewById(R.id.textView2);
		t.setText(mName+"同学是来自"+mClass+"班级的！");
		
		mBtn = (Button)findViewById(R.id.buttonBack);
		
		OnClickListener Btn1_Listener = new OnClickListener() {
			public void onClick(View v) {
				
				Intent intent = new Intent();
				intent.setClass(TestActivity.this, MainActivity.class);

				startActivity(intent);
				finish();
			}
		};
		
		mBtn.setOnClickListener(Btn1_Listener);
        	
    	
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
