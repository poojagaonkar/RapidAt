package Attini.DAL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import Attini.Model.ConnectionDetector;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.zevenpooja.attini.Login;

public class RegisterDevice extends AsyncTask<String, String, String> 
{
	Login login = new Login();
	ProgressDialog dialog;
	Context mContext;
	private boolean didItWork;
	
	Activity mActivity;
  
	public RegisterDevice(Login login2) 
	{
		// TODO Auto-generated constructor stub
		this.mContext = login2;
		this.mActivity = login2;
	}

	@Override
	protected void onPreExecute() 
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		ConnectionDetector cd = new ConnectionDetector(mContext);
		if(cd.isConnectingToInternet()== false)
		{
			new AlertDialog.Builder(mContext)
		    .setTitle("Network error")
		    .setMessage("Please check your internet connection")
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) 
		        { 
		        	didItWork = false;
		           mActivity.finish();
		        }
		     })
		    .show();
		}

		else
		{
		didItWork = true;
		dialog = new ProgressDialog(mContext);
	    dialog.show(mContext, "Authenticating", "Please wait");
		}
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
	
		String Url = params[0];
    	HttpResponse response =null;
		String resultString = "";
		String responseBody = "" ;
		// Creating HTTP client
				HttpClient httpClient = new DefaultHttpClient();
				// Creating HTTP Post
				HttpPost httpPost = new HttpPost(Url);

				// Building post parameters
				// key and value pair
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
				nameValuePair.add(new BasicNameValuePair("DeviceId", Login.deviceId));
				nameValuePair.add(new BasicNameValuePair("Name",Login.deviceName));
				nameValuePair.add(new BasicNameValuePair("EncodedAccountName",Login.username));

				// Url Encoding the POST parameters
				try 
				{
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				}
				catch (UnsupportedEncodingException e) 
				{
					// writing error to Log
					e.printStackTrace();
				}

				// Making HTTP Request
				try 
				{
					response = httpClient.execute(httpPost);
					if(response.getStatusLine().getStatusCode()==200)
					{
					HttpEntity entity = response.getEntity();
			            
			            if(entity!=null)
			            {
			            	responseBody = EntityUtils.toString(entity);
			           
			            }
			            
					}
					// writing response to log
					
				}
				catch (ClientProtocolException e) 
				{
					// writing exception to log
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					// writing exception to log
					e.printStackTrace();

				}
				return resultString = responseBody;
	}

	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	if(dialog!=null && dialog.isShowing())
		dialog.dismiss();
	
	}

}
