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
	
	//��Ҫ�ŵ���ť�ϵ��ַ���
    String chars[] = new String[]{
    	"1", "2", "3",
    	"4", "5", "6",
        "7", "8", "9",
        "*", "0", "#",
        "ȡ��", "����", "���"
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;
		
		GridLayout mGridLayout;
		mGridLayout = (GridLayout)findViewById(R.id.grid);
		
		
		// �½�TextView
		mText = new TextView(mContext);
		mText.setTextSize(40);
		// �趨TextView������
		Spec rowSpec = GridLayout.spec(0);
		// spec (int start, int size) spec.span = [start, start + size]
		Spec columnSpec = GridLayout.spec(0,4);
		LayoutParams laParams = new LayoutParams(rowSpec, columnSpec);
		mGridLayout.addView(mText, laParams);
		
		// ����һ����ť��������
		class btnListener implements OnClickListener {
			private Button btn;
			// ���췽����Button������Ϊ����
			private btnListener (Button btn) {
				this.btn = btn;
			}
			public void onClick(View v) {
				String text = mText.getText().toString();
				text += btn.getText().toString();
				mText.setText(text);
			}
		};
		
		// ��������
		for(int i = 0; i < 12; i ++){
            Button button = new Button(this);
            button.setText(chars[i]);
            button.setTextSize(40);
            //����ť��Ӽ����¼�
            button.setOnClickListener(new btnListener(button));
            //ָ�����������
            rowSpec = GridLayout.spec(i / 3 + 1);
            //ָ�����������
            columnSpec = GridLayout.spec(i % 3);
            //����LayoutParams����
            LayoutParams layoutParams = new LayoutParams(rowSpec, columnSpec);
            //ָ�������������
            layoutParams.setGravity(Gravity.FILL);
            //��������ø�GridLayout
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
