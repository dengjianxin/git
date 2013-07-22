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
        // ȥ��������
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qqlogin);
        final EditText edtName=(EditText)findViewById(R.id.login_edit_account);
        final EditText edtPwd=(EditText)findViewById(R.id.login_edit_pwd);
        Button btnSubmit=(Button)findViewById(R.id.login_btn_login);
		final String WEBSERVICE_IP = "http://192.168.1.7";  
        
        btnSubmit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		try {  
		            // ���ô�������WebMethod  
		            final String SERVER_URL = WEBSERVICE_IP  
		                    + "/comm/NetworkDrive/NetworkDrive.aspx/LoadTypeShow"; // ��������WebMethod  
		            HttpPost request = new HttpPost(SERVER_URL); // ����������Դ��ַ����һ��Http����  
		            request.addHeader("Content-Type", "application/json; charset=utf-8");// ����Ҫ��Ӹ�Httpͷ���ܵ���WebMethodʱ����JSON����  
		            JSONObject jsonParams = new JSONObject();  
		            jsonParams.put("UserId", edtName.getText());// ����  
		            jsonParams.put("ChannelID", edtPwd.getText());  
		            HttpEntity bodyEntity = new StringEntity(jsonParams.toString(),  
		                    "utf8");// ��������Ҳ����JSON���ݸ�ʽ���ַ������ܴ��ݵ��������ˣ���������"{'Message':'xxx����Ч��JSON��Ԫ'}"�Ĵ���  
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
		            HttpResponse httpResponse = httpClient.execute(request); // �������󲢻�ȡ����  
		            // �������ص�����  
		            if (httpResponse.getStatusLine().getStatusCode() != 404) // StatusCodeΪ200��ʾ���������ӳɹ�  
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