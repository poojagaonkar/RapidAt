package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import Attini.Model.NavDrawerItem;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.zeven.attini.R;
import com.zevenpooja.attini.News;




public class LazyAdapter extends ArrayAdapter<String>
{
	private Activity activity;
	private List<News> data;
	private static LayoutInflater inflater = null;
	public Context myContext;
	public ImageLoader imageLoader;
	public Context mContext;
	public ProgressDialog pDialog;
	private String mydescription;
	private String bitmapUrl;
	private String myDate;
	private final Random mRandom;
	private String myTitle;
	private String myColor;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	private ArrayList<NavDrawerItem> navDrawerItems;
	private ArrayList<String> colorList;
	private static Pattern REMOVE_TAG = null;
	
	 static class LazyViewHolder
	 {
		 TextView title;
		 TextView description;
		 ImageView thumb_image;
		 WebView imageWebView;
		 TextView pubDate;
		 View viewDate;
		 
		 String myTitle;
		 String mydescription;
		 String bitmapUrl;
		 String myColor;
	 }
	  public LazyAdapter(Context ctx, int just,  List<News> getList) 
	  {
		  super(ctx,just);
	        this.mContext = ctx;
	        this.data=getList;
	        this.inflater =LayoutInflater.from(ctx);
	        this.mRandom =  new Random();
	        this.imageLoader=new ImageLoader(ctx);
	     
	    }
	  
	  private double getPositionRatio(final int position)
	  {
	        double ratio = sPositionHeightRatios.get(position, 0.0);
	        // if not yet done generate and stash the columns height
	        // in our real world scenario this will be determined by
	        // some match based on the known height and width of the image
	        // and maybe a helpful way to get the column height!
	        if (ratio == 0) {
	            ratio = getRandomHeightRatio();
	            sPositionHeightRatios.append(position, ratio);
	            Log.d("TAG", "getPositionRatio:" + position + " ratio:" + ratio);
	        }
	        return ratio;
	    }
	  private double getRandomHeightRatio() {
	        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5
	                                                    // the width
	    }
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public String getItem(int position)
	{
		// TODO Auto-generated method stub
		return super.getItem(position);

	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean isEnabled(int position) 
	{
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		if(position%2==0)
		{
			return 0;
		}
		else
			
			return 1;
		
		
	}
	@Override
	public int getViewTypeCount() 
	{
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	 public View getView(int position, View convertView, ViewGroup parent)
	{
        View vi=convertView;
        LazyViewHolder viewHolder;
        if(convertView==null)
        {
        	switch(getItemViewType(position))
        	{
        	case 0:
        		vi = inflater.inflate(com.zeven.attini.R.layout.list_row,null);
        		
        		break;
        	case 1:
        		vi = inflater.inflate(com.zeven.attini.R.layout.list_row1,null);
        		break;
        	}
        
            
        }
        viewHolder = new LazyViewHolder();
        
        viewHolder.title = (TextView)vi.findViewById(com.zeven.attini.R.id.title); // title
        viewHolder.description = (TextView)vi.findViewById(com.zeven.attini.R.id.description); // artist name
       viewHolder.pubDate = (TextView)vi.findViewById(com.zeven.attini.R.id.txtPubDate);
       viewHolder.viewDate  =(View)vi.findViewById(R.id.viewDate);
      
       
       
       			News song = data.get(position);
       			
       			
       		
       				
       			myColor = song.getColor();
       			/*if(myColor=="#7959BC")
       			{
       				myColor = "#62934D";
       			}
       			else if(myColor =="#62934D")
       			{
       				myColor="#7959BC";
       			}*/
       			
       			       viewHolder.viewDate.setBackgroundColor(Color.parseColor(myColor));
       			 
      
        	   myTitle =song.getTitle();
           
               
               //Set description
            //  mydescription =Html.fromHtml(song.getBody()).toString();
        	
        		   mydescription =  Jsoup.parse(song.getBody()).text();
            
          
               // Set image
              bitmapUrl = song.getNewsBigImage();
               
               //Set date
             myDate = song.getPublishedDate();
               		
            
               
       
      
        StringBuilder sb = new StringBuilder(myTitle);
        
        int i = 0;
        while ((i = sb.indexOf(" ", i + 40)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        viewHolder.title.setText(sb.toString());
        
        /*  if(mydescription.length()<100)
      {
    	  mydescription = mydescription.substring(0, Math.min(mydescription.length(), 50)) + "....";
      }
      else
      {
    	  mydescription = mydescription.substring(0, Math.min(mydescription.length(), 100)) + "....";
      }*/
        
        
       switch(getItemViewType(position))
    	{
    	case 0:
    		  mydescription = mydescription.substring(0, Math.min(mydescription.length(), 50)) + "....";
    		
    		break;
    	case 1:
    		 mydescription = mydescription.substring(0, Math.min(mydescription.length(), 100)) + "....";
    		break;
    	}
       
       	
        viewHolder.description.setText(mydescription, BufferType.SPANNABLE);
        
      
        
     
        final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  		 final String NEW_FORMAT = "MMMM dd, yy";
  		 
  		 String newDate ="";
  		 
  		 SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
  		 try
  		 {
  			Date d = sdf.parse(myDate);
  			sdf.applyLocalizedPattern(NEW_FORMAT);
  			newDate = sdf.format(d);
  			viewHolder.pubDate.setText(newDate);
  		} 
  		 catch (java.text.ParseException e)
  		 {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}		
        
        
      
         // Bitmap bitmap = DownloadImage(song.get(Home.THUMBNAILPATH));
          
          
         viewHolder.thumb_image=(ImageView)vi.findViewById(com.zeven.attini.R.id.list_image); // thumb image
         imageLoader.DisplayImage(bitmapUrl, viewHolder.thumb_image);
      
        
     
        
       
      
        return vi;
    }
	
	private String skipLines(String in, int numLines)
	{
		int newlineIndex = 0;
		try
		{
        
        for(int i=0; i<numLines && i<in.length(); i++)
        {
            newlineIndex += in.indexOf('\n', newlineIndex);
        }
       
		}
		catch(StringIndexOutOfBoundsException e)
		{
			
		}
		 return new String(in.substring(0, newlineIndex));
	}
	
	private class OpenHttpConnection extends AsyncTask<String, String, InputStream> 
	{
       
		@Override
		protected InputStream doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			 InputStream in = null;
		        int response = -1;


		        try 
		        {

			        URL url = new URL(params[0]);
			        URLConnection conn = url.openConnection();

			        if (!(conn instanceof HttpURLConnection))
			            throw new IOException("Not an HTTP connection");
			        
		            HttpURLConnection httpConn = (HttpURLConnection) conn;
		            httpConn.setAllowUserInteraction(false);
		            httpConn.setInstanceFollowRedirects(true);
		            httpConn.setRequestMethod("GET");
		            httpConn.connect();
		            response = httpConn.getResponseCode();
		            if (response == HttpURLConnection.HTTP_OK) 
		            {
		                in = httpConn.getInputStream();
		            }
		        } 
		        catch (Exception ex)
		        {
		            try 
		            {
						throw new IOException("Error connecting");
					} 
		            catch (IOException e) 
		            {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        return in;
		}
      
    }
	private Bitmap DownloadImage(String URL) 
	{
		  Bitmap bitmap = null;
	        InputStream in = null;
	        try
	        {
	            in = new OpenHttpConnection().execute(URL).get();
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } 
	        catch (IOException e1) 
	        {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        } 
	        catch (InterruptedException e)
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        catch (ExecutionException e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return bitmap;
	 }



	
	
}


