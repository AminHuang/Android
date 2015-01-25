package com.example.ex09;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static int GUI_UPDATE = 10001;
	private Button search;
	private EditText phoneNumber;
	private TextView detail;
	private String tmp;

	
	//用来完成对界面的更新
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			//如果是自定义的更新消息，则更新界面
			if (msg.what == GUI_UPDATE) {
				detail.setText(tmp);
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化变量
		init();
		
		//设置选择按钮监听器
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String t = phoneNumber.getText().toString();
				new Thread(new DownloadRunnable(t)).start();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void init() {
		search = (Button)findViewById(R.id.button1);
		phoneNumber = (EditText)findViewById(R.id.editText1);
		detail = (TextView)findViewById(R.id.detail);
	}
	
	class DownloadRunnable implements Runnable {
		//下载地址
		final static String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
		//保存要查询的城市
		String phone;

		public DownloadRunnable(String c) {
			phone = c;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("http", phone);
			
			// GET方法
			String getUrl = url + "?mobileCode=" + phone + "&userID=";
			HttpGet httpGet = new HttpGet(getUrl);
			 
			
			//新建HttpPost对象
			HttpPost httpPost = new HttpPost(url);
			//新建参数内容
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mobileCode", phone));
			params.add(new BasicNameValuePair("userID", ""));
			
			
			try {
				//设置HttpPost对象的参数和编码格式
				Log.i("http", "coming");
				
				httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				//执行POST操作并获取响应内容
				Log.i("http", "coming");
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//				HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
				//如果响应状态码为200，说明已经获取正确的响应
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					tmp = "";
					Log.i("http", "coming");
					//获取响应内容
					String result = EntityUtils.toString(httpResponse.getEntity());
					//新建一个XML解析器
					XmlPullParserFactory paserFactory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = paserFactory.newPullParser();
					//设置解析器的输入为响应报文
					parser.setInput(new StringReader(result));
					//获取解析器事件
					int parserEvent = parser.getEventType();
					//计数器，用来计算获取了多少个string标签
					int count = 0;
					
					while (parserEvent != XmlPullParser.END_DOCUMENT) {
						switch (parserEvent) {
						//如果是开始标签
						case XmlPullParser.START_TAG:
							//获取标签名
							String tag = parser.getName();
							//判断是不是<string>标签
							if (tag.equals(new String("string"))) {
								//计数器+1
//								count++;
//								//第5个<string>是天气信息，第6个是空气质量
//								if (count == 5) {
//									//获取内容文本
//									weatherText = parser.nextText();
//								} else if (count == 6) {
//									airText = parser.nextText();
//								}
								tmp += parser.nextText();
							}
							break;
						}
						//将解析器移动到下一个输入
						parserEvent = parser.next();
					}
					//新建消息
					Message msg = new Message();
					//设置消息类型并发送消息
					msg.what = GUI_UPDATE;
					MainActivity.this.myHandler.sendMessage(msg);
				}
				Log.i("http", Integer.toString(httpResponse.getStatusLine().getStatusCode()));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
