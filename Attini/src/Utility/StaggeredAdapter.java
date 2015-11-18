package Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;








import com.zevenpooja.attini.HomeFragment;
import com.zevenpooja.attini.News;
import com.zeven.attini.R;

public class StaggeredAdapter extends ArrayAdapter<String> 
{

	

	private ImageLoader mLoader;
	private Activity activity;
	private List<News> data;
	private static LayoutInflater inflater = null;
	public Context myContext;
	public ImageLoader imageLoader;
	public Context mContext;
	public ProgressDialog pDialog;
	private final Random mRandom;
	private String myTitle;
	private String mydescription;
	private String bitmapUrl;
	private String myDate;
	private String myColor;
	private String likes, comms, views;
	private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	MediaPlayer playSound;
	HomeFragment myfragment;
	 static class LazyViewHolder
	 {
		 TextView title;
		 TextView description;
		 ScaleImageView thumb_image;
		 WebView imageWebView;
		 TextView pubDate;
		 View viewdate;
		 TextView txtViews, txtComms, txtLikes;
		 FrameLayout layGridLayout;
	 }
	
	 public StaggeredAdapter(Context context, int textViewResourceId, List<News> getList, HomeFragment homeFragment) 
	 {
			super(context, textViewResourceId);
		
		 	//super(ctx, 0, null);
		 	mContext = context;
	        data=getList;
	        inflater =LayoutInflater.from(context);
	        this.mRandom =  new Random();
			mLoader = new ImageLoader(context);
			this.myfragment = homeFragment;
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
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		@Override
		public boolean isEnabled(int position) 
		{
			// TODO Auto-generated method stub
			return true;
		}
		@Override
		 public View getView(final int position, View convertView, ViewGroup parent)
		{
	        
	        final LazyViewHolder viewHolder;
	        
	        if(convertView==null)
	        	convertView = inflater.inflate(R.layout.row_staggered,null);
	        
	       viewHolder = new LazyViewHolder();
	        
	       viewHolder.title = (TextView)convertView.findViewById(R.id.title); // title
	       viewHolder.description = (TextView)convertView.findViewById(R.id.description); // artist name
	       viewHolder.thumb_image=(ScaleImageView)convertView.findViewById(R.id.imageView1); // thumb image
	       viewHolder.pubDate = (TextView)convertView.findViewById(R.id.txtPubDate);
	       viewHolder.viewdate = (View)convertView.findViewById(R.id.viewdate);
	       viewHolder.layGridLayout = (FrameLayout)convertView.findViewById(R.id.layoutOfStagGridView);
	       viewHolder.txtComms = (TextView)convertView.findViewById(R.id.txtcomm);
	       viewHolder.txtViews = (TextView)convertView.findViewById(R.id.txtview);
	       viewHolder.txtLikes = (TextView)convertView.findViewById(R.id.txtlike);
	       
	       News song = data.get(position);
	 
	       myColor = song.getColor();
	       
	       
	     /*  myColor = song.getColor();
  			if(myColor=="#7959BC")
  			{
  				myColor = "#62934D";
  			}
  			else if(myColor =="#62934D")
  			{
  				myColor="#7959BC";
  			}*/
	       viewHolder.viewdate.setBackgroundColor(Color.parseColor(myColor));
	       
	       
	       views = song.getNewsViews();
	       comms = song.getNewsComments();
	       likes = song.getNewsLikes();
	       
	       
	       viewHolder.txtComms.setText(comms);
	       viewHolder.txtLikes.setText(likes);
	       viewHolder.txtViews.setText(views);
	       viewHolder.txtComms.setTextColor(Color.GRAY);
	       viewHolder.txtLikes.setTextColor(Color.GRAY);
	       viewHolder.txtViews.setTextColor(Color.GRAY);
	        // Setting all values in listview
	        //Set title
	       myTitle =song.getTitle();
	        StringBuilder sb = new StringBuilder(myTitle);

	        int i = 0;
	        while ((i = sb.indexOf(" ", i + 40)) != -1) {
	            sb.replace(i, i + 1, "\n");
	        }

	        viewHolder.title.setText(sb.toString());
	        
	        //Set description
	        mydescription =  Jsoup.parse(song.getBody()).text();
	      
	        switch(getItemViewType(position))
	    	{
	    	case 0:
	    		  mydescription = mydescription.substring(0, Math.min(mydescription.length(), 50)) + "....";
	    		
	    		break;
	    	case 1:
	    		 mydescription = mydescription.substring(0, Math.min(mydescription.length(), 100)) + "....";
	    		break;
	    	}
	        viewHolder.description.setText(mydescription);
	        
	        // Set image
	        bitmapUrl = song.getNewsBigImage();
	        
	        //Set date
	        myDate = song.getPublishedDate();
	        		
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
	     
		
		//convertView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400));
		mLoader.DisplayImage(bitmapUrl, viewHolder.thumb_image);
		viewHolder.layGridLayout.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				//viewHolder.layGridLayout.setForeground(R.drawable.foreground_selector);
				myfragment.sendData(position);
				playSound = MediaPlayer.create(mContext, R.raw.buttonclicksound);
				playSound.start();
			}
		});
		return convertView;
		}
		
		
		
		 private double getPositionRatio(final int position) {
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
	

		
		
	}




