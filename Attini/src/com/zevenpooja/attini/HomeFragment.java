
package com.zevenpooja.attini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import Attini.DAL.EndPoints;
import Attini.Model.ConnectionDetector;
import Utility.LazyAdapter;
import Utility.ProgressDialogFragment;
import Utility.StaggeredAdapter;
import Utility.WaitProgressFragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.origamilabs.library.views.StaggeredGridView;
import com.zeven.attini.R;



@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements  com.origamilabs.library.views.StaggeredGridView.OnItemClickListener, SensorEventListener, OnItemClickListener 
{

	TextView txtUsersName;
	String encodedAccountName,SPHostUrl,refreshToken, realm, usersname,deviceAuthKey, avatarUrl, fullName;
	ListView newsList;
	LinearLayout layout1;
	
	private WaitProgressFragment refDialog ;

	public static  String[] titles = null;
	public static   String[] descriptions = null;
	public static   String[] thumbnails = null;
	public static   String[] urls = null;
	//JSON Node Names
	public static  String NEWSITEMS ="NewsItems" ;
	
	public static  String  ARTICLEGUID ="ArticleGuid";
	public static  String  AUTHORLOGINNAME=" AuthorLoginName";
	public static  String  BODYTEXT ="BodyText";
	public static  String  BODY ="Body" ;
	public static  String  PUBLISHER ="AuthorDisplayName" ;
	//JSONObject ColSpan = ColSpan(i),
	public static  String CREATEDDATE = "CreatedDate";
	public static  String  ENTERPRISEKEYWORDS="EnterpriseKeywords";
	public static  String  ISPLACEHOLDERFORTHUMBNAIL="IsPlaceHolderForThumbnail";
	public static  String  NEWSSOURCEID="NewsSourceId";
	public static  String  IMAGEBLOBURLS="ImageBlobUrls";
	public static String  NEWSSOURCETITLE="NewsSourceTitle";
	public static  String  NUMBEROFCOMMENTS="NumberOfComments";
	public static  String  NUMBEROFVIEWS="NumberOfViews";
	public static  String  PUBLISHEDDATE="PublishedDate";
	public static  String ID ="id";
    //JSONObject RowSpan = RowSpan(i),
	public static  String  THUMBANAILDESCRIPTION="ThumbnailDescription";
	public static  String  THUMBNAILPATH="ThumbnailPath" ;
	public static  String  TITLE = "Title" ;
	public static  String  URL= "Url" ;
	
	private boolean hasRequestMore;
	
	JSONArray newsItems = null;
	
	public static List<News> myNewsList = new ArrayList<News>();
	 
	public static String[] colorPallete = new String[] {"#1F1A17", "#62934D", "#F9B03F", "#7959BC", "#74B8DE", "#E65641", "#7CC8BB", "#D7CE5D", "#D6BE95", "#B694D1"};
	private TextView txtTitle;
	private TextView txtBody;
	private TextView txtNewsView;
	private TextView txtNewsComments, txtWelcome;
	private ImageView imgThumbnail;
	private HashMap<String, String> map ;
	private View viewDate;
	private ListView leftList, rightList;
	//URL to get JSON Array
	private static String url = EndPoints.FetchNewsItemsUrl;
	ProgressDialog pDialog;
	 
	private JSONArray newsArray = null;
	private LazyAdapter itemsAdapter;
	public static ProgressDialog myDialog;
	public ProgressDialog newsDialog;
	private Context myContext;
	private List<News> getList = new ArrayList<News>();
	private List<News> getListLeft = new ArrayList<News>();
	private List<News> getListRight = new ArrayList<News>();
	private ProgressDialogFragment prog;
	private String mycolor;
	private com.etsy.android.grid.StaggeredGridView mGridView;
	private StaggeredAdapter stagAdaper;
	private SensorManager sensorManager1;
	protected MediaPlayer playSound;
    
    @SuppressLint("ValidFragment")


	public HomeFragment(String sPHostUrl2, String encodedAccountName2,
			String deviceAuthKey, String usersname2, String avatarUrl, String fullName, Context context, List<News> myFinalNewsList) 
	{
		// TODO Auto-generated constructor stub
		this.SPHostUrl = sPHostUrl2;
		this.encodedAccountName = encodedAccountName2;
		this.avatarUrl = avatarUrl;
		this.deviceAuthKey = deviceAuthKey;
		this.usersname = usersname2;
		this.myContext = context;
		this.fullName = fullName;
		this.getList = myFinalNewsList;
		
	}
 /*
  * (non-Javadoc)
  * @see android.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
  */

	public HomeFragment() {
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("ResourceAsColor")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
	{
		
        View vi = inflater.inflate(R.layout.fragment_home, container, false);
		
	/* Component initialization */ 
        
        txtTitle = (TextView)vi.findViewById(R.id.title);
		txtBody = (TextView)vi.findViewById(R.id.desc);
		
		
		imgThumbnail = (ImageView)vi.findViewById(R.id.icon);
		newsList = (ListView)vi.findViewById(android.R.id.list);
		
		mGridView =(com.etsy.android.grid.StaggeredGridView)vi.findViewById(R.id.staggeredGridView1);
	

		setHasOptionsMenu(true);
		
	// Check if orientation is Portrait	
		if(mGridView == null)
		{
			myNewsList.clear();
			//String NewsRecordsEndPoint = "https://www.attinicomms.com/api/cpnews?";
		  //  String registerContet = EndPoints.FetchNewsItemsUrl + "spHostUrl="+SPHostUrl + "&encodedAccountName="+encodedAccountName+"&deviceAuthKey="+ deviceAuthKey+"&count=10";
			
			itemsAdapter = new LazyAdapter(myContext,0, getList );
			newsList.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
			newsList.setOnItemClickListener(new OnItemClickListener() 
				{
					

					@Override
					public void onItemClick(AdapterView<?> arg0,
							View arg1, int position, long arg3) 
					{
						// TODO Auto-generated method stub
						
						sendData(position);
						
						
					}
				});
			
		

		}
		// If orientation is landscape
		else
		{
			myNewsList.clear();
			mGridView.setFastScrollEnabled(false);
			stagAdaper = new StaggeredAdapter(myContext, android.R.layout.simple_list_item_1, getList, HomeFragment.this);
			mGridView.setAdapter(stagAdaper);
			stagAdaper.notifyDataSetChanged();
			stagAdaper.setNotifyOnChange(true);
			mGridView.setOnItemClickListener(this);
			mGridView.setSelector(R.drawable.list_selector1);
			
			int margin = 2;
			
			mGridView.setPadding(margin, 0, margin, 0);
			
		}
		
		return vi;
    }

private void accelerometerMethod()
{
	sensorManager1 = (SensorManager)myContext.getSystemService(Context.SENSOR_SERVICE);
    Sensor accelerometer = sensorManager1.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
    sensorManager1.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
}
	//Item click listener for GridView
	@Override
	public void onItemClick(StaggeredGridView parent, View view, int position,
			long id)
	{
		// TODO Auto-generated method stub
	   sendData(position);	
				
	}

	@SuppressWarnings("deprecation")
	public void sendData(int position)
	{
		
		myDialog = new ProgressDialog(myContext).show(getActivity(), "Fetching news..", "Just a moment");
		
		myDialog.getWindow().setContentView(R.layout.openarticlewaitprogress);
		myDialog.getWindow().setTitle("Loading..");
		myDialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		 new  NewsDetails(myDialog);

			
			
	        Intent newsIntent = new Intent(getActivity(),NewsDetails.class);
	
		       String newsBody=getList.get(position).getBody();
				 String newsImage =getList.get(position).getNewsBigImage();
				 String newsView = getList.get(position).getNewsViews();
				 String newsComments = getList.get(position).getNewsComments();
				 String pubDate = getList.get(position).getPublishedDate();
				 String articleGuid = getList.get(position).getArticleGuid();
				 String newsSourceId = getList.get(position).getNewsSourceId();
				 String newsId = getList.get(position).getNewsId();
				 String publisherName = getList.get(position).getPublisherName();
				 String colors = getList.get(position).getColor();
				 String newsSourceTitle = getList.get(position).getNewsSourceTitle();
				 String title =  getList.get(position).getTitle();
				 String newsLikes = getList.get(position).getNewsLikes();
				 String tags = getList.get(position).getTags();
	        
	        newsIntent.putExtra("NewsBody", newsBody);
	        newsIntent.putExtra("NewsViews", newsView);
	        newsIntent.putExtra("NewsImage", newsImage);
	        newsIntent.putExtra("NewsComments", newsComments);
	        newsIntent.putExtra("NewsSourceId", newsSourceId);
	        newsIntent.putExtra("NewsId", newsId);
	        newsIntent.putExtra("NewsSourceTitle", newsSourceTitle);
	        newsIntent.putExtra("PubDate", pubDate);
	        newsIntent.putExtra("ArticleGuid", articleGuid);
	        newsIntent.putExtra("Color", colors);
	        newsIntent.putExtra("PubName", publisherName);
	        newsIntent.putExtra("Title", title);
	        newsIntent.putExtra("NewsLikes", newsLikes);
	        newsIntent.putExtra("Tags", tags);
	      
	    	newsIntent.putExtra("SPHostUrl", SPHostUrl);
			newsIntent.putExtra("EncodedAccountName", encodedAccountName);
			newsIntent.putExtra("DeviceAuthKey", deviceAuthKey);
			newsIntent.putExtra("AvatarUrl", avatarUrl);
			newsIntent.putExtra("FullName", fullName);
			
			
			startActivity(newsIntent);
			
	}

	


	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			mGridView.setColumnCount(2);
			mGridView.invalidate();
			
		}
		else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			mGridView.setColumnCount(3);
			mGridView.invalidate();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 int currentOrientation = getResources().getConfiguration().orientation;
		    if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE){
		        mGridView.setColumnCount(4);
		        mGridView.invalidate();
		    }else{
		    	mGridView.setColumnCount(2);
		    	mGridView.invalidate();
		    }   
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		sendData(position);	
	}

	

	
	
	



}