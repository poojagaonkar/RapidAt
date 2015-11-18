package Utility;

import com.zevenpooja.attini.Login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveUserEmail extends Application 
{
	public static String SavedData = "AttiniCommsUserData";
	Login login = new Login();
	SharedPreferences saveEmail;
	Boolean isUserSaved = false;
	
	 public void SaveUsersEmail(String username)
	 {
	 saveEmail = login.getSharedPreferences(SavedData,0);
	 SharedPreferences.Editor prefsEditor = saveEmail.edit();
	 prefsEditor.putString("EmailId", username);
	 isUserSaved = true;
	 prefsEditor.commit();
	 
	 
	 }
	
	 public String LoadUserEmail()
	 {
		 if(isUserSaved)
		 saveEmail = login.getSharedPreferences(SavedData,0);
		 String username = saveEmail.getString("EmailId", "No record");
		 return username;
	 }
	 
	

}
