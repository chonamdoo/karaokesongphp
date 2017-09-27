package kr.ds.httpclient;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Http관련
 * @author Chodongsuk
 * @since 20150129
 */
public class DsHttpClient {
	private final static String TAG = DsHttpClient.class.getSimpleName();
	/**
	 * Write클래스 POST 전송
	 * @param hashmap
	 * @param httpurl
	 * @param encoding
	 * @return
	 */
	public static <K, V> String HttpWritePost(HashMap<K, V> hashmap, String httpurl, String encoding) {
		String result = "";
		try {
			URL url = new URL(httpurl); // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			StringBuffer buffer = new StringBuffer();
			
			HashMap<K, V> mHashMap = hashmap;
			Set<Entry<K, V>> set = mHashMap.entrySet();
			Iterator<Entry<K, V>> it = set.iterator();
			while (it.hasNext()) {
				Entry<K, V> e = (Entry<K, V>)it.next();
				buffer.append(e.getKey()).append("=").append(e.getValue()).append("&");
			}
			//buffer.append("data").append("=").append(data).append("&");
			//Write 클래스 2바이트 형식(char)으로 출력
			OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), encoding);
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();
			
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), encoding);
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			result = builder.toString();
			Log.i(TAG, result + "");
		} catch (IOException e) {
		}
		return result;
	}
	/**
	 * POST전송
	 * @param para_hashmap
	 * @param httpurl
	 * @param encoding
	 * @return
	 */
	public static <K,V> String HttpPost(HashMap<K, V> para_hashmap, String httpurl, String encoding) {
		String result = "";
		try {			
			String lineEnd = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        
			URL url = new URL(httpurl);
			HttpURLConnection http= (HttpURLConnection) url.openConnection();
			
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Connection", "Keep-Alive");
			http.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(http.getOutputStream());
			HashMap<K, V> mHashMap = para_hashmap;
			Set<Entry<K, V>> set = mHashMap.entrySet();
			Iterator<Entry<K, V>> it = set.iterator();
			while (it.hasNext()) {
				Entry<K, V> e = (Entry<K, V>)it.next();
				
				dos.writeBytes(twoHyphens + boundary + lineEnd);
	            dos.writeBytes("Content-Disposition: form-data; name=\""+e.getKey()+"\""+ lineEnd);
	            dos.writeBytes(lineEnd);
	            dos.writeBytes(URLEncoder.encode((String) e.getValue(),encoding));
	            dos.writeBytes(lineEnd);
			}
	        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	        dos.flush();
			
	        InputStreamReader tmp = new InputStreamReader(http.getInputStream(), encoding);
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			result = builder.toString();
			Log.i(TAG, result + "");
		} catch (Exception e) {
			Log.d("my", e.toString());
		}
		return result;
	}

    /**
     * GET전송
     * @param httpurl
     * @param encoding
     * @return
     */

    public static String HttpGet(String httpurl, String encoding) {
        String result = "";
        try {
            URL url = new URL(httpurl);
            HttpURLConnection http= (HttpURLConnection) url.openConnection();

            http.setDefaultUseCaches(false);
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("GET");
            http.setRequestProperty("Connection", "Keep-Alive");

            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), encoding);
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }
            result = builder.toString();
            Log.i(TAG, result + "");
        } catch (Exception e) {
            Log.d("my", e.toString());
        }
        return result;
    }

	/**
	 * 파일업로드
	 * @param para_hashmap
	 * @param fileupload_hashmap
	 * @param httpurl
	 * @param encoding
	 * @return
	 */
	
	public static <K,V> String HttpFileUploadPost(HashMap<K, V> para_hashmap, HashMap<K, V> fileupload_hashmap, String httpurl, String encoding) {
		String result = "";
		try {			
			String lineEnd = "\r\n";
	        String twoHyphens = "--";
	        String boundary = "*****";
	        
			URL url = new URL(httpurl);
			HttpURLConnection http= (HttpURLConnection) url.openConnection();
			
			http.setDefaultUseCaches(false);
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Connection", "Keep-Alive");
			http.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			
			DataOutputStream dos = new DataOutputStream(http.getOutputStream());
			HashMap<K, V> mHashMap = para_hashmap;
			Set<Entry<K, V>> set = mHashMap.entrySet();
			Iterator<Entry<K, V>> it = set.iterator();
			while (it.hasNext()) {
				Entry<K, V> e = (Entry<K, V>)it.next();
				
				dos.writeBytes(twoHyphens + boundary + lineEnd);
	            dos.writeBytes("Content-Disposition: form-data; name=\""+e.getKey()+"\""+ lineEnd);
	            dos.writeBytes(lineEnd);
	            dos.writeBytes(URLEncoder.encode((String) e.getValue(),encoding));
	            dos.writeBytes(lineEnd);
			}
			
			HashMap<K, V> mHashMap_ = fileupload_hashmap;
			Set<Entry<K, V>> set_ = mHashMap.entrySet();
			Iterator<Entry<K, V>> it_ = set.iterator();
			int i= 1;
			while (it_.hasNext()) {
				Entry<K, V> e_ = (Entry<K, V>)it_.next();
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				((Bitmap)e_.getValue()).compress(CompressFormat.JPEG, 100, byteArray);
				byte[] data = byteArray.toByteArray();
				dos.writeBytes(twoHyphens + boundary + lineEnd);
 		        dos.writeBytes("Content-Disposition: form-data; name=\"image"+i+"\"; filename=\"image"+i+".jpg\"" + lineEnd);
 		        dos.writeBytes(lineEnd);	        
 		        dos.write(data,0,data.length);
 		        dos.writeBytes(lineEnd);
				i++;
			}
	        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	        dos.flush();
			
	        InputStreamReader tmp = new InputStreamReader(http.getInputStream(), encoding);
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str + "\n");
			}
			result = builder.toString();
			Log.i(TAG, result + "");
		} catch (Exception e) {
			Log.d("my", e.toString());
		}
		return result;
	}
	
}
