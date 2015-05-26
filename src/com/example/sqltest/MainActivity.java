package com.example.sqltest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sqltest.HttpConnectionUtil.HttpConnectionCallback;
import com.example.sqltest.HttpConnectionUtil.HttpMethod;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	 private UIHandler UIhandler = new UIHandler();  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViews();
        setListeners();
//        
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()  
//        .detectDiskReads()  
//        .detectDiskWrites()  
//        .detectNetwork()  
//        .penaltyLog()  
//        .build());  
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
//        .detectLeakedSqlLiteObjects()   
//        .penaltyLog()  
//        .penaltyDeath()  
//        .build());  
//        
     
	}


	private class UIHandler extends Handler{  
        @Override  
        public void handleMessage(Message msg) {  

            // TODO Auto-generated method stub  
            super.handleMessage(msg);  
            
            TableLayout user_list = (TableLayout)findViewById(R.id.user_list);
            user_list.setStretchAllColumns(true);
            TableLayout.LayoutParams row_layout = new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams view_layout = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            
            
            Bundle bundle = msg.getData();  
            String res = bundle.getString("jsonRet");  

            JSONArray array = null;
			try {
				array = new JSONArray(res);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

        	 JSONObject obj = null;
        	 for(int i = 0; i < array.length(); i++) {
                 JSONObject jsonData = null;
				try {
					jsonData = array.getJSONObject(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 TableRow tr = new TableRow(MainActivity.this);
                 tr.setLayoutParams(row_layout);
                 tr.setGravity(Gravity.CENTER_HORIZONTAL);
                 
                 TextView user_acc = new TextView(MainActivity.this);
                 try {
					user_acc.setText(jsonData.getString("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 user_acc.setLayoutParams(view_layout);
                 
                 TextView user_pwd = new TextView(MainActivity.this);
                 try {
					user_pwd.setText(jsonData.getString("tabname"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 user_pwd.setLayoutParams(view_layout);
                 
                 tr.addView(user_acc);
                 tr.addView(user_pwd);
                 user_list.addView(tr);
             }

//            persons = JsonToList(response); 
//
//            setInAdapter(); 
//
//            listView.setAdapter(adapter); 

        } 
            
        
    }  
	

	  private void getAdapter() 

	    { 

         
		  
	        //网址 

	        String url = "http://192.168.0.104/dishes/index.php?g=User&m=Index&a=androidGetData"; 

	        //上节课讲的网络连接方法 

	        HttpConnectionUtil connUtil = new HttpConnectionUtil(); 

	        connUtil.asyncConnect(url, HttpMethod.GET, 

	                new HttpConnectionCallback() 

	                { 

	 

	                    @Override 

	                    public void execute(String response) 

	                    { 
	                    	Log.i("william","response is " + response);
	                    	
	                    	 
	                    	
	                    	//Thinkphp got some unused text contain on string header.
	                    	//We need to remove it.
	                    	String res = response.substring(1);

	                    	Message msg = new Message();  
	                        Bundle bundle = new Bundle();  
	                        bundle.putString("jsonRet",res);  
	                        msg.setData(bundle);  
	                        MainActivity.this.UIhandler.sendMessage(msg);  
	                    	
	                    	
	                    }
	                }); 

	 

	    } 
	  
//	  protected List<Person> JsonToList(String response) 
//
//	    { 
//
//	        List<Person> list = new ArrayList<Person>(); 
//
//	 
//
//	        try 
//
//	        { 
//
//	            // 将字符串转换为Json数组 
//
//	            JSONArray array = new JSONArray(response); 
//
//	            // 数组长度 
//
//	            int length = array.length(); 
//
//	            for (int i = 0; i < length; i++) 
//
//	            { 
//
//	                // 将每一个数组再转换成Json对象 
//
//	                JSONObject obj = array.getJSONObject(i); 
//
//	 
//
////	                person = new Person(); 
////
////	                person.setId(obj.getString("id")); 
////
////	                person.setStatus(obj.getString("status")); 
////
////	                person.setName(obj.getString("name")); 
////
////	                person.setTool(obj.getString("tool")); 
////
////	                person.setNumber(obj.getString("number")); 
////
////	 
////
////	                list.add(person); 
//
//	 
//
//	            } 
//
//	 
//
//	            return list; 
//
//	        } catch (Exception e) 
//
//	        { 
//
//	            e.printStackTrace(); 
//
//	        } 
//
//	        return null; 
//
//	    } 
	
	
private Button button_get_record;
    
    private void findViews() {
        button_get_record = (Button)findViewById(R.id.get_record);
    }
    
    private void setListeners() {
        button_get_record.setOnClickListener(getDBRecord);
    }
    
    private Button.OnClickListener getDBRecord = new Button.OnClickListener() {
        public void onClick(View v) {
        	
        	try 

	        { 

	            // 自完义适配方法 

	            getAdapter(); 

	 

	        } catch (Exception e) 

	        { 

	            e.printStackTrace(); 

	        } 
        	
//        	new Thread(new Runnable() {
//
//        		 public void run() {
//
//        		
//    	
//        			 
//        	            // TODO Auto-generated method stub
//        		 }
//        		
//        		 }).start();

        	
        };
    };
	
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
}
