package kr.ds.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class gcmHandler {
	public gcmHandler(){
		
	}
	public String HttpPostData(String android_id, String reg_id,  String send, String httpurl) {
		String myResult = "";
		try {
			URL url = new URL(httpurl); // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			StringBuffer buffer = new StringBuffer();
			buffer.append("android_id").append("=").append(android_id).append("&");
			buffer.append("reg_id").append("=").append(reg_id).append("&"); 
			buffer.append("send").append("=").append(send).append("&"); 
			OutputStreamWriter outStream = new OutputStreamWriter(
					http.getOutputStream(), "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();
			InputStreamReader tmp = new InputStreamReader(
					http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			myResult = builder.toString();
			Log.i("TEST1",myResult+"");
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} 
		return myResult;
	} 
	
	
}

