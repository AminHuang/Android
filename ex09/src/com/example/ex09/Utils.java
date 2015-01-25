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
	 * 采用HTTPPost方法查询天气
	 * 
	 * @param url
	 * @param method
	 * @param cityname
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static ArrayList<String> httpsearch(String url, String method, String cityname) {
		Log.i("WeatherInfo","查询城市 = " + cityname);
		ArrayList<String> lsxml = new ArrayList<String>();
		//新建HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		//新建参数内容
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("theCityCode", cityname));
		params.add(new BasicNameValuePair("theUserID", ""));
		try {
			//设置HttpPost对象的参数和编码格式
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
			
			//执行POST操作并获取响应内容
			HttpResponse httpResponse = httpclient.execute(httpPost);
			
			
			//如果响应状态码为200，说明已经获取正确的响应
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				Log.i("WeatherInfo", "访问状态 = " + httpResponse.getStatusLine().getStatusCode());
				//获取响应内容
				String result = EntityUtils.toString(httpResponse.getEntity());
				Log.i("respone",result );
				//新建一个XML解析器
				XmlPullParserFactory paserFactory = XmlPullParserFactory.newInstance();
				XmlPullParser parser = paserFactory.newPullParser();
				//设置解析器的输入为响应报文
				parser.setInput(new StringReader(result));
				//获取解析器事件
				int parserEvent = parser.getEventType();
				//计数器，用来计算获取了多少个string标签
				int count = 0;
				//设置城市名
				lsxml.add(cityname);
				while (parserEvent != XmlPullParser.END_DOCUMENT) {
					
					switch (parserEvent) {
					//如果是开始标签
					case XmlPullParser.START_TAG:
						//获取标签名
						String tag = parser.getName();
						Log.i("statu2", tag);
						//判断是不是<string>标签
						if (tag.equals(new String("string"))) {
							//计数器+1
							count++;
							//第5个<string>是天气信息，第6个是空气质量
							if (count == 5) {
								//获取内容文本
								lsxml.add(parser.nextText());
//								weatherText = parser.nextText();
							} else if (count == 6) {
								lsxml.add(parser.nextText());
//								airText = parser.nextText();
							}
						}
						break;
					}
					//将解析器移动到下一个输入
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
