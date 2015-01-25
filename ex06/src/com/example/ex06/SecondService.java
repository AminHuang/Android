package com.example.ex06;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class SecondService extends Service {

	public static MediaPlayer mp = new MediaPlayer();

	public final IBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		SecondService getService() {
			return SecondService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		try {
			mp.setDataSource("/data/01.mp3");
			mp.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Service-----onCreate");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mp != null) {
			mp.stop();
			mp.release();
		}
	}

	// service²¥·Å&ÔÝÍ£
	public void PlayOrPause() {
		try {
			mp.setDataSource("/data/01.mp3");
			mp.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mp.isPlaying()) {
			mp.pause();
		} else {
			mp.start();
		}
	}

	// serviceÍ£Ö¹
	public void Stop() {
		if (mp != null) {
			mp.stop();
			mp.reset();

		}
	}

}
