package com.example.ex03;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridLayout.Spec;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private TextView mText;
	private Button mBtn;
	private Context mContext;
	
	//需要放到按钮上的字符串
    String chars[] = new String[]{
    	"1", "2", "3",
    	"4", "5", "6",
        "7", "8", "9",
        "*", "0", "#",
        "取消", "拨号", "清除"
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;
		
		GridLayout mGridLayout;
		mGridLayout = (GridLayout)findViewById(R.id.grid);
		
		
		// 新建TextView
		mText = new TextView(mContext);
		mText.setTextSize(40);
		// 设定TextView的行列
		Spec rowSpec = GridLayout.spec(0);
		// spec (int start, int size) spec.span = [start, start + size]
		Spec columnSpec = GridLayout.spec(0,4);
		LayoutParams laParams = new LayoutParams(rowSpec, columnSpec);
		mGridLayout.addView(mText, laParams);
		
		// 创建一个按钮监听器，
		class btnListener implements OnClickListener {
			private Button btn;
			// 构造方法将Button对象作为参数
			private btnListener (Button btn) {
				this.btn = btn;
			}
			public void onClick(View v) {
				String text = mText.getText().toString();
				text += btn.getText().toString();
				mText.setText(text);
			}
		};
		
		// 创建键盘
		for(int i = 0; i < 12; i ++){
            Button button = new Button(this);
            button.setText(chars[i]);
            button.setTextSize(40);
            //给按钮添加监听事件
            button.setOnClickListener(new btnListener(button));
            //指定组件所在行
            rowSpec = GridLayout.spec(i / 3 + 1);
            //指定组件所在列
            columnSpec = GridLayout.spec(i % 3);
            //生成LayoutParams对象
            LayoutParams layoutParams = new LayoutParams(rowSpec, columnSpec);
            //指定组件充满网格
            layoutParams.setGravity(Gravity.FILL);
            //将组件设置给GridLayout
            mGridLayout.addView(button, layoutParams);
        }
		
		///*
			mBtn = new Button(this);
			mBtn.setText(chars[12]);
			mBtn.setTextSize(40);
			mBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String text = mText.getText().toString();
					text = text.substring(0, text.length()-1);
					mText.setText(text);
				} 
			});
			rowSpec = GridLayout.spec(5);
            columnSpec = GridLayout.spec(0);
            LayoutParams layoutParams = new LayoutParams(rowSpec, columnSpec);
            layoutParams.setGravity(Gravity.FILL);
            mGridLayout.addView(mBtn, layoutParams);
            
			
			Button tmp1 = new Button(this);
			tmp1.setText(chars[13]);
			tmp1.setTextSize(40);
			columnSpec = GridLayout.spec(1);
            layoutParams = new LayoutParams(rowSpec, columnSpec);
			mGridLayout.addView(tmp1, layoutParams);
			
			Button tmp2 = new Button(this);
			tmp2.setText(chars[14]);
			tmp2.setTextSize(40);
			tmp2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String text = "";
					mText.setText(text);
				} 
			});
			columnSpec = GridLayout.spec(2);
            layoutParams = new LayoutParams(rowSpec, columnSpec);
			mGridLayout.addView(tmp2, layoutParams);
		
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
