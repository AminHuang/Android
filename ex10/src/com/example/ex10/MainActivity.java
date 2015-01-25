package com.example.ex10;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	private static final String URL = "http://fy.webxml.com.cn/webservices/EnglishChinese.asmx";
	private static final String OPERATION_NAME = "TranslatorSentenceString";
	private static final String SOAP_ACTION = "http://WebXml.com.cn/TranslatorSentenceString";
	private static final String REQUEST_PARA_NAME = "wordKey";
	private static final String RESPONSE_PROP_NAME = "TranslatorSentenceStringResult";
	
	private EditText word;
	private Button btn;
	private TextView trans;
	
	private static final int UPDATE_GUI = 1111;
	private String wordTrans;
	
	
	// ���̶Ի���
	private ProgressDialog dialog;
	
	// ������ɶԽ���ĸ���
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// ������Զ���ĸ�����Ϣ������½���
			if (msg.what == UPDATE_GUI) {
				trans.setText(wordTrans);
				dialog.dismiss();
			}
			super.handleMessage(msg);
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getView();
        
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String w = word.getText().toString();
				if (w.equals("")) {
					Toast.makeText(MainActivity.this, "�����뵥��", Toast.LENGTH_SHORT).show();
				} else {
					dialog = new ProgressDialog(MainActivity.this);
					dialog.setTitle("Requesting");
					dialog.show();
					new Thread(new SoapRunnable()).start();
				}
				
			}
        	
        });
        
    }
    
    private void getView() {
    	word = (EditText)findViewById(R.id.editText1);
    	btn = (Button)findViewById(R.id.button1);
    	trans = (TextView)findViewById(R.id.textView1);
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
    
    class SoapRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String w = word.getText().toString();
			
			// �����������
			SoapObject request = new SoapObject(NAMESPACE, OPERATION_NAME);
			// ����������
			request.addProperty(REQUEST_PARA_NAME, w);
			
			// ���ɵ���WebService������SOAP������Ϣ
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
			
			// ����.Net-Services��Ĭ�ϱ���
			envelope.dotNet = true;
			
			// ���÷��͵Ķ���
			envelope.setOutputSoapObject(request);
			
			// ����HttpTransportSE����
			HttpTransportSE httpTrnsportSE = new HttpTransportSE(URL);
		    
			try {
				// ����soapAction header���������󣬻�ȡ���
				httpTrnsportSE.call(SOAP_ACTION, envelope);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// ��ȡ����envelope��body��Ϣ
			SoapObject bodyIn = (SoapObject)envelope.bodyIn;
			
			// ��ȡTranslatorStringResult����
			SoapObject result = (SoapObject)bodyIn.getProperty(RESPONSE_PROP_NAME);
			
			System.out.println(result.toString());
			
			// ��ȡ���ĸ�Ԫ�أ�Ϊ���ʵķ���
			wordTrans = result.getPropertyAsString(0);
			
			// ������Ϣ���½���
			Message msg = new Message();
			msg.what = UPDATE_GUI;
			MainActivity.this.handler.sendMessage(msg);
			
		}
    	
    }
    
    
    
}
