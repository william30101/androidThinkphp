package com.example.sqltest;


import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.UnsupportedEncodingException;  
import java.net.URLEncoder;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Map;  
  
import org.apache.http.HttpResponse;  
import org.apache.http.HttpStatus;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.client.methods.HttpUriRequest;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;  
  
import android.os.Handler;  
import android.util.Log;  
  
public class HttpConnectionUtil  
{  
    public static enum HttpMethod  
    {  
        GET, POST  
    }  
  
      
    public void asyncConnect(final String url, final HttpMethod method,  
            final HttpConnectionCallback callback)  
    {  
        asyncConnect(url, null, method, callback);  
    }  
  
      
    public void syncConnect(final String url, final HttpMethod method,  
            final HttpConnectionCallback callback)  
    {  
        syncConnect(url, null, method, callback);  
    }  
  
      
    public void asyncConnect(final String url,  
            final Map<String, String> params, final HttpMethod method,  
            final HttpConnectionCallback callback)  
    {  
//        Handler handler = new Handler();  
//        Runnable runnable = new Runnable()  
//        {  
//            public void run()  
//            {  
//                syncConnect(url, params, method, callback);  
//            }  
//        };  
//        handler.post(runnable);  
        
		new Thread(new Runnable() {

			public void run() {

				syncConnect(url, params, method, callback);

				// TODO Auto-generated method stub
			}

		}).start();
        
    }  
  
      
    public void syncConnect(final String url, final Map<String, String> params,  
            final HttpMethod method, final HttpConnectionCallback callback)  
    {  
        String json = null;  
        BufferedReader reader = null;  
  
        try  
        {  
            HttpClient client = new DefaultHttpClient();  
           // HttpUriRequest request = getRequest(url, params, method);  
            HttpGet httpGet = new HttpGet("http://192.168.0.104/dishes/index.php?g=User&m=Index&a=androidGetData");
            //HttpResponse response = client.execute(request);  
            HttpResponse response = client.execute(httpGet);  
            Log.i("william",response.getStatusLine().toString());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
            {  
            	Log.i("william","Enter reader");
                reader = new BufferedReader(new InputStreamReader(response  
                        .getEntity().getContent()));  
                StringBuilder sb = new StringBuilder();  
                for (String s = reader.readLine(); s != null; s = reader  
                        .readLine())  
                {  
                    sb.append(s);  
                }  
  
                json = sb.toString();  
            }  
        } catch (ClientProtocolException e)  
        {  
            Log.e("william", e.getMessage(), e);  
        } catch (IOException e)  
        {  
            Log.e("william", e.getMessage(), e);  
        } 
        catch(Exception e) {
            Log.e("william", e.toString());
       }
        finally  
        {  
            try  
            {  
                if (reader != null)  
                {  
                    reader.close();  
                }  
            } catch (IOException e)  
            {  
                // ignore me   
            	Log.i("william","error is " + e.getMessage());
            }  
        }  
        callback.execute(json);  
    }  
  
      
    private HttpUriRequest getRequest(String url, Map<String, String> params,  
            HttpMethod method)  
    {  
        if (method.equals(HttpMethod.POST))  
        {  
            List<NameValuePair> listParams = new ArrayList<NameValuePair>();  
            if (params != null)  
            {  
                for (String name : params.keySet())  
                {  
                    listParams.add(new BasicNameValuePair(name, params  
                            .get(name)));  
                }  
            }  
            try  
            {  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(  
                        listParams);  
                HttpPost request = new HttpPost(url);  
                request.setEntity(entity);  
                return request;  
            } catch (UnsupportedEncodingException e)  
            {  
                // Should not come here, ignore me.   
                throw new java.lang.RuntimeException(e.getMessage(), e);  
            }  
        } else  
        {  
            if (url.indexOf("?") < 0)  
            {  
                url += "?";  
            }  
            if (params != null)  
            {  
                for (String name : params.keySet())  
                {  
                    try  
                    {  
                        url += "&" + name + "="  
                                + URLEncoder.encode(params.get(name), "UTF-8");  
  
                    } catch (UnsupportedEncodingException e)  
                    {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            HttpGet request = new HttpGet(url);  
            return request;  
        }  
    }  
      
      
    public interface HttpConnectionCallback  
    {  
          
        void execute(String response);  
    }  
  
}  