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

	
	//������ɶԽ���ĸ���
	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			//������Զ���ĸ�����Ϣ������½���
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
		
		//��ʼ������
		init();
		
		//����ѡ��ť������
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
		//���ص�ַ
		final static String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
		//����Ҫ��ѯ�ĳ���
		String phone;

		public DownloadRunnable(String c) {
			phone = c;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("http", phone);
			
			// GET����
			String getUrl = url + "?mobileCode=" + phone + "&userID=";
			HttpGet httpGet = new HttpGet(getUrl);
			 
			
			//�½�HttpPost����
			HttpPost httpPost = new HttpPost(url);
			//�½���������
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("mobileCode", phone));
			params.add(new BasicNameValuePair("userID", ""));
			
			
			try {
				//����HttpPost����Ĳ����ͱ����ʽ
				Log.i("http", "coming");
				
				httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
				//ִ��POST��������ȡ��Ӧ����
				Log.i("http", "coming");
				HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//				HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
				//�����Ӧ״̬��Ϊ200��˵���Ѿ���ȡ��ȷ����Ӧ
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					tmp = "";
					Log.i("http", "coming");
					//��ȡ��Ӧ����
					String result = EntityUtils.toString(httpResponse.getEntity());
					//�½�һ��XML������
					XmlPullParserFactory paserFactory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = paserFactory.newPullParser();
					//���ý�����������Ϊ��Ӧ����
					parser.setInput(new StringReader(result));
					//��ȡ�������¼�
					int parserEvent = parser.getEventType();
					//�����������������ȡ�˶��ٸ�string��ǩ
					int count = 0;
					
					while (parserEvent != XmlPullParser.END_DOCUMENT) {
						switch (parserEvent) {
						//����ǿ�ʼ��ǩ
						case XmlPullParser.START_TAG:
							//��ȡ��ǩ��
							String tag = parser.getName();
							//�ж��ǲ���<string>��ǩ
							if (tag.equals(new String("string"))) {
								//������+1
//								count++;
//								//��5��<string>��������Ϣ����6���ǿ�������
//								if (count == 5) {
//									//��ȡ�����ı�
//									weatherText = parser.nextText();
//								} else if (count == 6) {
//									airText = parser.nextText();
//								}
								tmp += parser.nextText();
							}
							break;
						}
						//���������ƶ�����һ������
						parserEvent = parser.next();
					}
					//�½���Ϣ
					Message msg = new Message();
					//������Ϣ���Ͳ�������Ϣ
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
