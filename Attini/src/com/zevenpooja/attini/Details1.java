package com.zevenpooja.attini;

import java.util.HashMap;

import Attini.DAL.AuthenticateDevice;
import Attini.DAL.EndPoints;
import Attini.Model.ConnectionDetector;
import Utility.SessionManagement;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;
import android.widget.TextView;

import com.zeven.attini.R;

@SuppressLint("ValidFragment")
public class Details1 extends Activity
{
	//Url to load :- https://zevenseas1.sharepoint.com/sites/intranet/_layouts/15/AppRedirect.aspx?client_id=261f8324-1837-47fd-a5a1-ddd8a6047f04&redirect_uri=https://www.attinicomms.com/AuthorizeDevice/index/6BEBD502CAD549D0B5D378B82786C8A5?%7BStandardTokens%7D
	
	//[ Class variables
		private WebView myWebView;
		private String url;
		private static String endpointHost, partitionKey, timeStamp, rowKey;
		public static String encodedAccountName, deviceId, hostUrl,usersname, SPHostUrl,deviceAuthKey, avatarUrl, lastName, accountname, email;
		private Login login = new Login();
		private String AuthenticateDeviceEndPoint = EndPoints.AuthenticateDevice;
		private TextView txtWelcome;
		private String redirected ;
		private AccountManager manager;
		public static ProgressDialog progressDialog;
		public Utility.ProgressDialogFragment myprogressDialog;
		public SessionManagement sessiondetails ;
		private boolean canContinue; //]
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.details);

			sessiondetails = new SessionManagement(getApplicationContext());
			
			myWebView = (WebView)findViewById(R.id.webView1);
			
			
			ConnectionDetector cd = new ConnectionDetector(Details1.this);
			if(cd.isConnectingToInternet()== false)
			{
				new AlertDialog.Builder(this)
			    .setTitle("Network error")
			    .setMessage("Please check your internet connection")
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() 
			    {
			        public void onClick(DialogInterface dialog, int which) 
			        { 
			            canContinue = false;
			            finish();
			        }
			     })
			    .show();
			}
			else
			{	
			canContinue= true;
			
			
			sessiondetails.checkLogin();
			 HashMap<String, String> userDetails = sessiondetails.getUserDetails();
			 url = userDetails.get("deviceauthurl");
			 endpointHost = userDetails.get("endpointhost");
			 encodedAccountName = userDetails.get("encodedaccountname");
			 deviceId = userDetails.get("deviceid");
			 hostUrl = userDetails.get("hosturl");
			 usersname = userDetails.get("usersname");
			
			
			 /*	Intent in = getIntent();
		url=in.getStringExtra("DeviceAuthUrl");
		deviceId=in.getStringExtra("DeviceId");
		endpointHost=	in.getStringExtra("EndpointHost");
			encodedAccountName = in.getStringExtra("Name");
		
			encodedAccountName =in.getStringExtra("EncodedAccountName");
			hostUrl = in.getStringExtra("HostUrl");*/
			 
			
			//Encoding the url
			url = url.replace("{", "%7B");
			url = url.replace("}", "%7D");

			
			
			myWebView.getSettings().setJavaScriptEnabled(true);

			myWebView.loadUrl(url);
			WebViewDatabase.getInstance(Details1.this).clearHttpAuthUsernamePassword();
			WebViewDatabase.getInstance(Details1.this).clearUsernamePassword();
			WebViewDatabase.getInstance(Details1.this).clearFormData();
			myprogressDialog= new Utility.ProgressDialogFragment().newInstance();
			
			final ProgressDialog progressBar = ProgressDialog.show(Details1.this, "Loading..", "Please wait!");
			
			myWebView.setWebViewClient(new WebViewClient()
			{
				
				
				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) 
				{
					// TODO Auto-generated method stub
					super.onPageStarted(view, url, favicon);
					redirected =view.getUrl();
					redirected=  Uri.decode(redirected);
					
					if(Build.VERSION.SDK_INT<= Build.VERSION_CODES.JELLY_BEAN_MR2)
					{
					if(redirected!=null && redirected.contains("redirect_uri="+endpointHost+"/AuthorizeDevice/index/"+deviceId+"?{StandardTokens}"))
					{
						
						myprogressDialog.show(getFragmentManager(), "Wait");
					}
					}
						else if(Build.VERSION.SDK_INT> Build.VERSION_CODES.JELLY_BEAN_MR2)
					{
						if(redirected!=null &&redirected.contains("SPVisited"))
						{
							
							myprogressDialog.show(getFragmentManager(), "Wait");
						}
					}
					
				}

				@Override
				public void onPageFinished(WebView view, String url) 
				{
					// TODO Auto-generated method stub
					super.onPageFinished(view, url);
					//
					
					if(progressBar!=null && progressBar.isShowing())
					{
						progressBar.dismiss();
					}
					
					redirected =view.getUrl();
					redirected=  Uri.decode(redirected);
					
				/*	new AlertDialog.Builder(Details1.this)
				   
				    .setMessage(redirected)
				   
				    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				   
				     .show();*/
					
					//redirectString="redirect_uri="+endpointHost+"/AuthorizeDevice/index/"+deviceId+"?SPHostUrl=https://zevenseas1.sharepoint.com/sites/intranet&"
						if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.JELLY_BEAN_MR2)
					{
					if(redirected!=null && myprogressDialog.isVisible())//&& redirected.matches("https://www.attinicomms.com/AuthorizeDevice/index/"+deviceId+"?SPHostUrl=https://zevenseas1.sharepoint.com/sites/intranet&SPLanguage=en-US&SPClientTag=6&SPProductNumber=16.0.2916.1214&SPAppWebUrl=https://ZEVENSEAS1-5fa86411b88b26.sharepoint.com/sites/intranet/AttiniCommsPublisher"))
					{
						
						//myprogressDialog.dismiss();
						
						myWebView.stopLoading();
						
						String authorizationContentString =  AuthenticateDeviceEndPoint+ "?encodedAccountName=" + encodedAccountName + "&deviceId=" + deviceId + "&hostUrl=" + hostUrl;
						authorizationContentString =authorizationContentString.replace("|", "%7C");
						
						
						new AuthenticateDevice(Details1.this, myprogressDialog, hostUrl).execute(authorizationContentString);
						
						
					}
						
						
					}
				else if(Build.VERSION.SDK_INT> Build.VERSION_CODES.JELLY_BEAN_MR2)
					{
						if(redirected!=null && redirected.startsWith(endpointHost+"/AuthorizeDevice/index/"+deviceId))//&& myprogressDialog!=null && myprogressDialog.isVisible())
						{
						
							myWebView.stopLoading();
							
							String authorizationContentString =  AuthenticateDeviceEndPoint+ "?encodedAccountName=" + encodedAccountName + "&deviceId=" + deviceId + "&hostUrl=" + hostUrl;
							authorizationContentString =authorizationContentString.replace("|", "%7C");
							
							
							new AuthenticateDevice(Details1.this, myprogressDialog, hostUrl).execute(authorizationContentString);
							
							
							
						}
					}
				}

				
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
				
					// TODO Auto-generated method stub
					view.loadUrl(url);
					

			
					return true;

				}

			});
			}
			
		}
		@Override
		protected void onDestroy() 
		{
			// TODO Auto-generated method stub
			super.onDestroy();
			if(myprogressDialog!=null && myprogressDialog.isVisible())
			{
				myprogressDialog.dismiss();
			}
		}
		
}
