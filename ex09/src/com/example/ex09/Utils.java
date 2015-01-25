package com.example.ex09;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Message;
import android.util.Log;

public class Utils {
	
	/**
	 * ����HTTPPost������ѯ����
	 * 
	 * @param url
	 * @param method
	 * @param cityname
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static ArrayList<String> httpsearch(String url, String method, String cityname) {
		Log.i("WeatherInfo","��ѯ���� = " + cityname);
		ArrayList<String> lsxml = new ArrayList<String>();
		//�½�HttpPost����
		HttpPost httpPost = new HttpPost(url);
		//�½���������
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("theCityCode", cityname));
		params.add(new BasicNameValuePair("theUserID", ""));
		try {
			//����HttpPost����Ĳ����ͱ����ʽ
//			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
//			CookieSpecFactory csf = new CookieSpecFactory() {
//			    public CookieSpec newInstance(HttpParams params) {
//			        return new BrowserCompatSpec() {   
//			            @Override
//						public void validate(Cookie cookie,
//								CookieOrigin origin)
//								throws MalformedCookieException {
//							// TODO Auto-generated method stub
//						}
//			        };
//			    }
//			};
			DefaultHttpClient httpclient = new DefaultHttpClient();
//			httpclient.getCookieSpecs().register("easy", csf);
//			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, "easy");
			
			//ִ��POST��������ȡ��Ӧ����
			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			
			//�����Ӧ״̬��Ϊ200��˵���Ѿ���ȡ��ȷ����Ӧ
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				Log.i("WeatherInfo", "����״̬ = " + httpResponse.getStatusLine().getStatusCode());
				//��ȡ��Ӧ����
				String result = EntityUtils.toString(httpResponse.getEntity());
				Log.i("respone",result );
				//�½�һ��XML������
				XmlPullParserFactory paserFactory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = paserFactory.newPullParser();
				//���ý�����������Ϊ��Ӧ����
				parser.setInput(new StringReader(result));
				//��ȡ�������¼�
				int parserEvent = parser.getEventType();
				//�����������������ȡ�˶��ٸ�string��ǩ
				int count = 0;
				//���ó�����
				lsxml.add(cityname);
				while (parserEvent != XmlPullParser.END_DOCUMENT) {
					
					switch (parserEvent) {
					//����ǿ�ʼ��ǩ
					case XmlPullParser.START_TAG:
						//��ȡ��ǩ��
						String tag = parser.getName();
						Log.i("statu2", tag);
						//�ж��ǲ���<string>��ǩ
						if (tag.equals(new String("string"))) {
							//������+1
							count++;
							//��5��<string>��������Ϣ����6���ǿ�������
							if (count == 5) {
								//��ȡ�����ı�
								lsxml.add(parser.nextText());
//								weatherText = parser.nextText();
							} else if (count == 6) {
								lsxml.add(parser.nextText());
//								airText = parser.nextText();
							}
						}
						break;
					}
					//���������ƶ�����һ������
					parserEvent = parser.next();
				}
				Log.i("Info", "ok?");
			}
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
		return lsxml;
	}

}
