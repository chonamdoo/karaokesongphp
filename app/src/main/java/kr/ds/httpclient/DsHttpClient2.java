package kr.ds.httpclient;
/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Http관련
 * @author Surviving
 * @since 20150129
 */
public class DsHttpClient2 {
    private String url;
private HttpURLConnection con;
private OutputStream os;

private String delimiter = "--";
private String boundary =  "SwA"+Long.toString(System.currentTimeMillis())+"SwA";

public DsHttpClient2(String url) {
    this.url = url;
}
/**
 * 다운로드
 * @param imgName
 * @return
 */
public byte[] downloadImage(String imgName) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
        System.out.println("URL ["+url+"] - Name ["+imgName+"]");

        HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.connect();
        con.getOutputStream().write( ("name=" + imgName).getBytes());

        InputStream is = con.getInputStream();
        byte[] b = new byte[1024];

        while ( is.read(b) != -1)
            baos.write(b);

        con.disconnect();
    }
    catch(Throwable t) {
        t.printStackTrace();
    }

    return baos.toByteArray();
}
/**
 * 접속
 * @throws Exception
 */
public void connectForMultipart() throws Exception {
    con = (HttpURLConnection) ( new URL(url)).openConnection();
    con.setRequestMethod("POST");//전송방식 POST 지정
    con.setDoInput(true);//서버 읽기모드
    con.setDoOutput(true);//서버 쓰기 모드 지정
    con.setRequestProperty("Connection", "Keep-Alive");
    con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    con.connect();
    os = con.getOutputStream();//데이터보냄
}
/**
 * 데이터 추가
 * @param paramName
 * @param value
 * @throws Exception
 */
public void addFormPart(String paramName, String value) throws Exception {
    writeParamData(paramName, value);
}
/**
 * 파일전송
 * @param paramName
 * @param fileName
 * @param data
 * @throws Exception
 */
public void addFilePart(String paramName, String fileName, byte[] data) throws Exception {
    os.write( (delimiter + boundary + "\r\n").getBytes());
    os.write( ("Content-Disposition: form-data; name=\"" + paramName +  "\"; filename=\"" + fileName + "\"\r\n"  ).getBytes());
    os.write( ("Content-Type: application/octet-stream\r\n"  ).getBytes());
    os.write( ("Content-Transfer-Encoding: binary\r\n"  ).getBytes());
    os.write("\r\n".getBytes());

    os.write(data);

    os.write("\r\n".getBytes());
}
/**
 * 종료
 * @param paramName
 * @param fileName
 * @param data
 * @throws Exception
 */
public void finishMultipart() throws Exception {
    os.write( (delimiter + boundary + delimiter + "\r\n").getBytes());
}

/**
 * 값 전달
 * @return
 * @throws Exception
 */
public String getResponse() throws Exception {

    InputStreamReader tmp = new InputStreamReader(con.getInputStream());
    BufferedReader reader = new BufferedReader(tmp);
    StringBuilder builder = new StringBuilder();
    String str;
    while ((str = reader.readLine()) != null) {
        builder.append(str + "\n");
    }
    return builder.toString();
}



private void writeParamData(String paramName, String value) throws Exception {
    os.write( (delimiter + boundary + "\r\n").getBytes());
    os.write( "Content-Type: text/plain\r\n".getBytes());
    os.write( ("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n").getBytes());
    os.write( ("\r\n" + value + "\r\n").getBytes());


}
}