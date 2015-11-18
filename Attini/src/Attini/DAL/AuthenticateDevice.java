package Attini.DAL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Attini.Model.ConnectionDetector;
import Utility.AuthenticationInformation;
import Utility.ProgressDialogFragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.zevenpooja.attini.Details1;
import com.zevenpooja.attini.Home;
import com.zevenpooja.attini.NewsDetails;


public class AuthenticateDevice extends AsyncTask<String, Void, Void>
{
	public Context mContext;
	String responseBody = "" ;
	 ProgressDialog dialog;
	 Utility.ProgressDialogFragment progDia;
	 FragmentManager man;
	private String usersname;
	private String email;
	private String SPHostUrl;
	private String encodedAccountName;
	private String deviceAuthKey;
	private String lastName;
	private String avatarUrl;
	private String accountname;
	private String hostUrl;
	Activity mActivity;

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
		            mActivity.finish();
		        }
		     })
		    .show();
		}
		
	}

	public AuthenticateDevice(Details1 myContext, ProgressDialogFragment myprogressDialog, String hostUrl)
	{
		// TODO Auto-generated constructor stub
		mContext = myContext;
		this.progDia = myprogressDialog;
		this.hostUrl = hostUrl;
		this.mActivity = myContext;
	}

	@Override
	protected Void doInBackground(String... params) 
	{
		// TODO Auto-generated method stub
		String Url = params[0];
    	HttpResponse response =null;
		String resultString = "";
	
		// Creating HTTP client
				HttpClient httpClient = new DefaultHttpClient();
				// Creating HTTP Post
				HttpGet request = new HttpGet(Url);

				try 
				{
					response = httpClient.execute(request);
					if(response.getStatusLine().getStatusCode()== 200)
					{
						HttpEntity entity = response.getEntity();
						if (entity != null)
						{
							
							InputStream inputStream = entity.getContent();
							responseBody = convertToString(inputStream);
							
							
							/** JSON response Parsing **/
							
							 JSONParser parser = new JSONParser();
						     Object register;
							
						     try 
						     {
								register = parser.parse(responseBody);
								org.json.simple.JSONObject authDevice = (org.json.simple.JSONObject) register;
								
								//Convert Json objects to strings
								//SPhosturl =https://zevenseas1.sharepoint.com/sites/intranet
								
								usersname  = (String) authDevice.get("FirstName");
								email = (String)authDevice.get("Email");
										
								SPHostUrl= (String) authDevice.get("SPHostUrl");
								encodedAccountName = (String) authDevice.get("EncodedAccountName");
								deviceAuthKey = (String) authDevice.get("DeviceAuthKey");
								avatarUrl  = (String)authDevice.get("AvatarUrl");
								lastName = (String)authDevice.get("LastName");
								//partitionKey = (String)authDevice.get("PartitionKey");
								//rowKey = (String)authDevice.get("RowKey");
							//	timeStamp = (String)authDevice.get("Timestamp");
								accountname = (String)authDevice.get("AccountName");
								
								
								AuthenticationInformation authInfo = new AuthenticationInformation(mContext);
								authInfo.SetUserInformation(hostUrl, email, avatarUrl, usersname, lastName, accountname,SPHostUrl,encodedAccountName,deviceAuthKey );
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
								
							//return responseBody;
							
						}
					}
					
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
				//return  responseBody;
				return null;
	}

	@Override
	protected void onPostExecute(Void result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		/*if(dialog!=null && dialog.isShowing())
		{
		dialog.dismiss();
		}
		
		if(progDia!=null&&progDia.isVisible())
		{
			progDia.dismiss();
		}*/
		Intent myIntent = new Intent(mContext, Home.class);
		
		myIntent.putExtra("UsersName", usersname);
		myIntent.putExtra("SPHostUrl",SPHostUrl);
		myIntent.putExtra("EncodedAccountName", encodedAccountName);
		myIntent.putExtra("DeviceAuthKey", deviceAuthKey);
		myIntent.putExtra("AvatarUrl", avatarUrl);
		myIntent.putExtra("LastName", lastName);
		
	
		
		
		myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mContext.startActivity(myIntent);
		mActivity.finish();
	}
	
	private String convertToString(InputStream inputStream) 
	{
		// TODO Auto-generated method stub
		StringBuffer string = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				string.append(line + "\n");
			}
		} catch (IOException e) {}
		return string.toString();
	}

	
}
