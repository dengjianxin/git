package com.example.hellword;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class QQLogin extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qqlogin);
        final EditText edtName=(EditText)findViewById(R.id.login_edit_account);
        final EditText edtPwd=(EditText)findViewById(R.id.login_edit_pwd);
        Button btnSubmit=(Button)findViewById(R.id.login_btn_login);
		final String WEBSERVICE_IP = "http://192.168.1.7";  
        
        btnSubmit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		try {  
		            // 调用带参数的WebMethod  
		            final String SERVER_URL = WEBSERVICE_IP  
		                    + "/comm/NetworkDrive/NetworkDrive.aspx/LoadTypeShow"; // 带参数的WebMethod  
		            HttpPost request = new HttpPost(SERVER_URL); // 根据内容来源地址创建一个Http请求  
		            request.addHeader("Content-Type", "application/json; charset=utf-8");// 必须要添加该Http头才能调用WebMethod时返回JSON数据  
		            JSONObject jsonParams = new JSONObject();  
		            jsonParams.put("UserId", edtName.getText());// 传参  
		            jsonParams.put("ChannelID", edtPwd.getText());  
		            HttpEntity bodyEntity = new StringEntity(jsonParams.toString(),  
		                    "utf8");// 参数必须也得是JSON数据格式的字符串才能传递到服务器端，否则会出现"{'Message':'xxx是无效的JSON基元'}"的错误  
		           request.setEntity(bodyEntity);  
		  
		            HttpParams httpParameters = new BasicHttpParams();  
		            // Set the timeout in milliseconds until a connection is  
		            // established.  
		            // The default value is zero, that means the timeout is not used.  
		            int timeoutConnection = 3 * 1000;  
		            HttpConnectionParams.setConnectionTimeout(httpParameters,  
		                    timeoutConnection);  
		            // Set the default socket timeout (SO_TIMEOUT)  
		            // in milliseconds which is the timeout for waiting for data.  
		            int timeoutSocket = 5 * 1000;  
		            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
		  
		            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);  
		            HttpResponse httpResponse = httpClient.execute(request); // 发送请求并获取反馈  
		            // 解析返回的内容  
		            if (httpResponse.getStatusLine().getStatusCode() != 404) // StatusCode为200表示与服务端连接成功  
		            {  
		                StringBuilder builder = new StringBuilder();  
		                BufferedReader bufferedReader2 = new BufferedReader(  
		                        new InputStreamReader(httpResponse.getEntity()  
		                                .getContent()));  
		                for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2.readLine()) {  
		                    builder.append(s);  
		                }  
		  
		                String resultString = builder.toString();  
		               // resultString = JSONToolkit.removeEscape(resultString);  
		                System.out.println(resultString);
		  
		            }  
		        } catch (Exception e) {  
		            e.printStackTrace();   
		        }  
        	}
        });
    }
}