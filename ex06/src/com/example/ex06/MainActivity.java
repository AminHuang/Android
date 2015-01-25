package com.example.ex06;

import java.io.IOException;
import java.text.SimpleDateFormat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SeekBar seekBar1;
	private TextView Text_time_now;
	private TextView Text_time_all;
	private SimpleDateFormat time;
	private TextView detial;
	private Button btn_play;
	private Button btn_stop;
	private Button btn_exit;
	
	public SecondService mSecondService;
	public Handler handler;
	public Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        
        bindServiceConnection();
        
        btn_play.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlayPauseButton(v);
			}
        	
        });
        
        btn_stop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handler.removeCallbacks(r);
				mSecondService.Stop();
				Text_time_now.setText("0:00");
				Text_time_all.setText("0:00");
				detial.setText("Stopping");
				
			}
        	
        });
        
        btn_exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				quit(v);
			}
        	
        });
        
    }
    
    public void initview() {
    	seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
    	Text_time_now = (TextView)findViewById(R.id.Text_time_now);
    	Text_time_all = (TextView)findViewById(R.id.Text_time_all);
    	time = new SimpleDateFormat("m:ss");
    	detial = (TextView)findViewById(R.id.detial);
    	btn_play = (Button)findViewById(R.id.btn_play);
    	btn_stop = (Button)findViewById(R.id.btn_stop);
    	btn_exit = (Button)findViewById(R.id.btn_exit);
    	
    	mSecondService = new SecondService();
    	handler = new Handler();
    	r = new Runnable() {

    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			Text_time_now.setText(time.format(mSecondService.mp.getCurrentPosition()));
    			seekBar1.setProgress(mSecondService.mp.getCurrentPosition());
    			handler.postDelayed(r, 100);
    		}
    		
    	};
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void bindServiceConnection() {
    	Intent intent = new Intent(MainActivity.this, SecondService.class);
    	startService(intent);
    	bindService(intent, sc, this.BIND_AUTO_CREATE);
    }
    
    private ServiceConnection sc = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mSecondService = ((SecondService.MyBinder)(service)).getService();
			System.out.println(mSecondService);
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mSecondService = null;
			
		}
    	
    };
    
    public void quit(View v) {
    	handler.removeCallbacks(r);
    	unbindService(sc);
    	try{
    		MainActivity.this.finish();
    	} catch (Exception e) {
    		
    	}
    }
    
    public void PlayPauseButton(View v) {
		mSecondService.PlayOrPause();
		if(mSecondService.mp.isPlaying()){
			detial.setText("Playing");
		}else{
			detial.setText("Pausing");
		}
		Text_time_all.setText(time.format(mSecondService.mp.getDuration()));
		handler.post(r);
		seekBar1.setMax(mSecondService.mp.getDuration());
		seekbar();
	}
    
    @Override
    protected void onStart() {
    	super.onStart();
    	System.out.println("onstart");
    }
    
    @Override
    protected void onDestroy() {
    	mSecondService.mp.release();
    	super.onDestroy();
    }
    
    public void seekbar() {
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				if (arg2)
					mSecondService.mp.seekTo(arg1);
			}
		});
	}
    
}
