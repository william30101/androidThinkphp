package com.example.sqltest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class DBConnector {
    public static String executeQuery(String query_string) {
        String result = "";
        
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet("http://192.168.0.102/dishes/index.php?g=User&m=Index&a=androidGetData");
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

            
            //httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.i("william",httpResponse.getStatusLine().toString());
            //view_account.setText(httpResponse.getStatusLine().toString());
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream inputStream = httpEntity.getContent();
            
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();
            
            Log.i("william","done here");
        } catch(Exception e) {
             Log.e("william", e.toString());
        }
        
        return result;
    }
}