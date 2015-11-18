package Utility;

import java.util.HashMap;

import com.zevenpooja.attini.Details1;
import com.zevenpooja.attini.Home;
import com.zevenpooja.attini.Login;

import Attini.Model.UserInformation;
import android.R.bool;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode;

public class AuthenticationInformation 
{
	
	 bool authenticationVal;
	 Context mContext;
	 static SharedPreferences localSettings;
	 static Editor editor;

	 
	public static String 	KEY_HOSTURL = "hosturl";
	public static String KEY_EMAIL = "email";
	public static String KEY_AVATARURL = "avatarurl";
	public static String KEY_FIRSTNAME= "firstname";
	public static String KEY_LASTNAME = "lastname";
	public static String KEY_ACCOUNTNAME = "accountname";
	public static String KEY_TIMESTAMP= "timestamp";
	public static String KEY_PARTITIONKEY = "partitionkey";
	public static String KEY_ROWKEY = "rowkey";
	public static String KEY_ENCODEDACCOUNTNAME = "encodedaccountname";
	public static String KEY_DEVICEAUTHKEY = "deviceauthkey";
	public static String KEY_SPHOSTURL = "sphosturl";
	public static String KEY_ISUSERAUTHENTICATED	 = "IsUserAuthenticated";
	
	  private  final String PREF_NAME = "AttiniCommsUserLogged";
	  
	 
     
     public AuthenticationInformation(Context applicationContext) 
     {
		// TODO Auto-generated constructor stub
    	 mContext = applicationContext;
    	 localSettings = applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    	 editor = localSettings.edit();
	}

 
     

    public static void SetUserInformation(String hostUrl, String email, String avatarurl, String firstname, String lastname, String accountName, String sphosturl, String timestamp, String partitionkey)
    {
        try
        {
            
            editor.putString(KEY_HOSTURL, hostUrl);
            editor.putString(KEY_EMAIL, email);

            if (avatarurl != null)
            {
                editor.putString(KEY_AVATARURL, avatarurl);
            }
            else
            {
                editor.putString("AvatarUrl", "");
            }

            editor.putString(KEY_FIRSTNAME, firstname);
            editor.putString(KEY_LASTNAME, lastname);
            editor.putString(KEY_ACCOUNTNAME, accountName);
            editor.putString(KEY_SPHOSTURL, sphosturl);
            //editor.putString("RefreshToken", info.RefreshToken);
            //editor.putString("Realm", info.Realm);
            editor.putString(KEY_TIMESTAMP, timestamp);
            editor.putString(KEY_PARTITIONKEY, partitionkey);
          //  editor.putBoolean(KEY_ISUSERAUTHENTICATED, isAuthenticted);
           // editor.putString(KEY_ROWKEY, rowkey);
          //  editor.putString(KEY_ENCODEDACCOUNTNAME, encodedaccountname);
          //  editor.putString(KEY_DEVICEAUTHKEY, deviceauthkey);
            //SetAuthentication(true);
        }
        catch (Exception e)
        {
            android.util.Log.e("Error", e.toString());
        }

    }

    public static HashMap<String, String> GetUserInformation(Context mContext)
    {
    	HashMap<String, String> user = new HashMap<String, String>();
       
       
    		user.put(KEY_HOSTURL, localSettings.getString(KEY_HOSTURL, null));
	         user.put(KEY_EMAIL, localSettings.getString(KEY_EMAIL, null));
	        user.put(KEY_AVATARURL, localSettings.getString(KEY_AVATARURL, null));
	        user.put(KEY_FIRSTNAME, localSettings.getString(KEY_FIRSTNAME, null));
	        user.put(KEY_LASTNAME, localSettings.getString(KEY_LASTNAME, null));
	        user.put(KEY_SPHOSTURL, localSettings.getString(KEY_SPHOSTURL, null));
	        user.put(KEY_DEVICEAUTHKEY, localSettings.getString(KEY_DEVICEAUTHKEY, null));
	       // user.put(KEY_TIMESTAMP, localSettings.getString(KEY_TIMESTAMP, null));
	      //  user.put(KEY_PARTITIONKEY, localSettings.getString(KEY_PARTITIONKEY, null));
	      //  user.put(KEY_ROWKEY, localSettings.getString(KEY_ROWKEY, null));
	        user.put(KEY_ACCOUNTNAME, localSettings.getString(KEY_ACCOUNTNAME, null));
       

        return user;
    }
    public void deleteUser()
    {
        // Clearing all data from Shared Preferences
        //editor.clear();
    	editor.remove(KEY_DEVICEAUTHKEY);
    	editor.remove(KEY_HOSTURL);
    	editor.remove(KEY_FIRSTNAME);
    	editor.remove(KEY_LASTNAME);
    	editor.remove(KEY_SPHOSTURL);
    	editor.remove(KEY_ENCODEDACCOUNTNAME);
    	//editor.remove(KEY_TIMESTAMP);
    	//editor.remove(KEY_PARTITIONKEY);
    	//editor.remove(KEY_ROWKEY);
    //	editor.remove(KEY_AVATARURL);
    	editor.remove(KEY_EMAIL);
    	editor.remove(KEY_AVATARURL);
        editor.commit();
         
        // After logout redirect user to Loing Activity
      //  Intent i = new Intent(mContext, Login.class);
        // Closing all the Activities
       // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         
        // Add new Flag to start new Activity
    //    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         
        // Staring Login Activity
    //    mContext.startActivity(i);
    }
    
}
