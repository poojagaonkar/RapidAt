package Attini.DAL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import Attini.Model.UserInformation;
import android.content.Context;

public class DeviceManagement
{
	public static Context mContext;
	public DeviceManagement(Context mycontext) 
	{
		// TODO Auto-generated constructor stub
	mContext = mycontext;
	}		
	
	public static String RegisterDevice(String username, String deviceId, String deviceName) 
	{
			HttpResponse response =null;
		String resultString = "";
		String responseBody = "" ;
		// Creating HTTP client
				HttpClient httpClient = new DefaultHttpClient();
				// Creating HTTP Post
				HttpPost httpPost = new HttpPost("https://www.attinicomms.com/api/RegisterDevice");

				// Building post parameters
				// key and value pair
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
				nameValuePair.add(new BasicNameValuePair("DeviceId", deviceId));
				nameValuePair.add(new BasicNameValuePair("Name",deviceName));
				nameValuePair.add(new BasicNameValuePair("EncodedAccountName", username));

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
					HttpEntity entity = response.getEntity();
			            
			            if(entity!=null)
			            {
			            	responseBody = EntityUtils.toString(entity);
			           
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

	public static UserInformation AuthenticateDevice(String encodedAccountName,String deviceId, String hostUrl)
	{
		// TODO Auto-generated method stub
		String params = "?encodedAccountName=" + encodedAccountName + "&deviceId=" + deviceId + "&hostUrl=" + hostUrl;
		try {
			params = URLEncoder.encode(params,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String authorizationContentString = "https://www.attinicomms.com/api/AuthenticateDevice" + params;
		
		
		HttpGet httpRequest = new HttpGet(authorizationContentString);
		HttpEntity httpEntity = null;
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpResponse;
		
		try 
		{
			httpResponse = client.execute(httpRequest);
			httpEntity = httpResponse.getEntity();
			String response = EntityUtils.toString(httpEntity);
			
			
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				
				return new UserInformation().GetUserInformation(httpResponse.getAllHeaders().toString());
				
				
			}
			else
			{
				return new UserInformation();
			}
			
			
		} 
		catch (ClientProtocolException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
			
		
		
		return null;
	}
	
	
	/*public static void RegisterDevice(String username, String deviceId, String deviceName)
	{
		String resultString = "";
		StringBuilder registerContent = new StringBuilder();
		
		registerContent.append("DeviceId=");
		registerContent.append(deviceId);
		registerContent.append("&");
		registerContent.append("Name=");
		registerContent.append(deviceName);
		registerContent.append("&");
		registerContent.append("EncodedAccountName=");
		registerContent.append(username);
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.execute(request)
		
		
	}*/
	

}
