package Utility;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeven.attini.R;

public class ProgressDialogFragment extends DialogFragment
{
	
	 public static ProgressDialogFragment newInstance()
	    {
	        return new ProgressDialogFragment();
	    }
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent);
	    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        return inflater.inflate(R.layout.fragment_dialog_progress, container, false);
	    }
}

