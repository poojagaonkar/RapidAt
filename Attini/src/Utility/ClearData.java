package Utility;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class ClearData extends Application 
{
	
	private static ClearData instance;
	@Override
	public void onCreate()
	{
	
	super.onCreate();
	
	instance = this;
	
	}
	
	public static ClearData getInstance() 
	{
	if(instance == null)
		instance = new ClearData();
	return instance;
	
	}

	public void clearApplicationData(Context mContext)
	{
	
	File cache = mContext.getCacheDir();
	File appDir = new File(cache.getParent());
	
	if (appDir.exists()) {

	String[] children = appDir.list();
	
	for (String s : children) {
	
	if (!s.equals("lib")) {
	
	deleteDir(new File(appDir, s));
	
	Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
	
	}
	
	}
	
	}
	
	}
	
	public void clearComplete()
	{
		  File file = new File("data/data/com.zevenpooja.attini/shared_prefs ");

	        if (file.exists()) {
	            String deleteCmd = "rm -r " + "data/data/com.zevenpooja.attini/shared_prefs";
	            Runtime runtime = Runtime.getRuntime();
	            try {
	                runtime.exec(deleteCmd);
	            } catch (Exception e) { 
	                e.printStackTrace();
	            }
	        }
	}
	
	public static boolean deleteDir(File dir) 
	{
	
	if (dir != null && dir.isDirectory()) 
	{
	
	String[] children = dir.list();
	
	for (int i = 0; i < children.length; i++) 
	{

	boolean success = deleteDir(new File(dir, children[i]));

	if (!success) {
	
	return false;
	
	}

	}

	}
	
	return dir.delete();
	
	}
	
	}

