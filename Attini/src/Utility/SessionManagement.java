package Utility;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.webkit.CookieManager;

import com.zevenpooja.attini.Login;

public class SessionManagement
{
	 SharedPreferences pref;
     
	    // Editor for Shared preferences
	    Editor editor;
	     
	    // Context
	    Context _context;
	     
	    // Shared pref mode
	    int PRIVATE_MODE = 0;
	     
	    // Sharedpref file name
	    public static final String PREF_NAME = "AttiniCommsUserDetails";
	     
	    // All Shared Preferences Keys
	    private static final String IS_LOGIN = "IsLoggedIn";
	     
	    // User name (make variable public to access from outside)
	    public static final String KEY_EMAILID = "email";
	     
	    // Email address (make variable public to access from outside)
	    public static final String KEY_DEVICEAUTHURL = "deviceauthurl";
	    
	    public static final String KEY_ENDPOINTHOST = "endpointhost";
	    public static final String KEY_DEVICENAME = "devicename";
	    public static final String KEY_USERSNAME = "usersname";
	    public static final String KEY_ENCODEDACCOUNTNAME = "encodedaccountname";
	    public static final String KEY_HOSTURL = "hosturl";
	    public static final String KEY_DEVICEiD = "deviceid";
	    
	    public static final String  KEY_DEVICEREGISTERED = "deviceregistered";
	    // Constructor
	    public SessionManagement(Context context)
	    {
	        this._context = context;
	        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = pref.edit();
	    }
	    
	    public void createLoginSession(String emailId, String deviceauthurl, String deviceid, String endpointhost, String devicename, String usersname, String encodedaccountname, String hosturl, boolean isDeviceregistered, boolean isUserLoggedIn)
	    {
	        // Storing login value as TRUE
	    	editor.clear();
	        editor.putBoolean(IS_LOGIN, isUserLoggedIn);
	         
	        editor.putBoolean(KEY_DEVICEREGISTERED, isDeviceregistered);
			editor.putString(KEY_EMAILID, emailId);
			editor.putString(KEY_DEVICEAUTHURL, deviceauthurl);
			editor.putString(KEY_DEVICEiD, deviceid);
			editor.putString(KEY_ENDPOINTHOST, endpointhost);
			editor.putString(KEY_DEVICENAME,devicename);
			editor.putString(KEY_USERSNAME, usersname);
			editor.putString(KEY_ENCODEDACCOUNTNAME, encodedaccountname);
			editor.putString(KEY_HOSTURL, hosturl);
	         
	        // commit changes
	        editor.commit();
	    }
	    
	    /**
	     * Get stored session data
	     * */
	    public HashMap<String, String> getUserDetails()
	    {
	        HashMap<String, String> user = new HashMap<String, String>();
	      
	        user.put(KEY_EMAILID, pref.getString(KEY_EMAILID, null));
	         user.put(KEY_DEVICEAUTHURL, pref.getString(KEY_DEVICEAUTHURL, null));
	        user.put(KEY_DEVICEiD, pref.getString(KEY_DEVICEiD, null));
	        user.put(KEY_ENDPOINTHOST, pref.getString(KEY_ENDPOINTHOST, null));
	        user.put(KEY_DEVICENAME, pref.getString(KEY_DEVICENAME, null));
	        user.put(KEY_USERSNAME, pref.getString(KEY_USERSNAME, null));
	        user.put(KEY_ENCODEDACCOUNTNAME, pref.getString(KEY_ENCODEDACCOUNTNAME, null));
	        user.put(KEY_HOSTURL, pref.getString(KEY_HOSTURL, null));
	        
	        // return user
	        return user;
	    }
	    /**
	     * Check login method wil check user login status
	     * If false it will redirect user to login page
	     * Else won't do anything
	     * */
	    public void checkLogin()
	    {
	        // Check login status
	        if(!this.isLoggedIn())
	        {
	            // user is not logged in redirect him to Login Activity
	            Intent i = new Intent(_context, Login.class);
	            // Closing all the Activities
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	              _context.startActivity(i);
	        }
	         
	    }
	    
	   // This function clears all session data and redirect the user to LoginActivity
	    /**
	         * Clear session details
	         * */
	        public void logoutUser()
	        {
	            // Clearing all data from Shared Preferences
	           editor.clear();
	        	
	           
	        	/*editor.putString(KEY_DEVICEAUTHURL,"");
	        	editor.putString(KEY_DEVICENAME,"");
	        	editor.putString(KEY_DEVICEREGISTERED,"");
	        	editor.putString(KEY_DEVICEiD,"");
	        	editor.putString(KEY_EMAILID,"");
	        	editor.putString(KEY_ENCODEDACCOUNTNAME,"");
	        	editor.putString(KEY_ENDPOINTHOST,"");
	        	editor.putString(KEY_HOSTURL,"");
	        	editor.putString(KEY_USERSNAME,"");
	        	editor.putBoolean(IS_LOGIN, false);
	        	editor.putString(PREF_NAME,"");*/
	        	
	        	editor.putBoolean(IS_LOGIN, false);
	            editor.commit();
	            
	            
	         //   ClearData.getInstance().clearApplicationData(_context);
	            //ClearData.getInstance().clearComplete();
	            // After logout redirect user to Loing Activity
	            CookieManager.getInstance().removeAllCookie();
	            Intent i = new Intent(_context, Login.class);
	            // Closing all the Activities
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	            _context.startActivity(i);
	        }
	        
	        public boolean isLoggedIn()
	        {
	            return pref.getBoolean(IS_LOGIN, false);
	        }
}
