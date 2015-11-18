package Utility;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeven.attini.R;

public class WaitProgressFragment extends DialogFragment
{
	
	 public static WaitProgressFragment newInstance()
	    {
	        return new WaitProgressFragment();
	    }
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent);
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        return inflater.inflate(R.layout.waitdialog, container, false);
	    }
}

