package com.zevenpooja.attini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Attini.DAL.EndPoints;
//import com.zevenpooja.attini.DrawerActivity.DrawerItemClickListener;
import Attini.Model.AttiniNewsItem;
import Attini.Model.ConnectionDetector;
import Attini.Model.NavDrawerItem;
import Attini.Model.NavDrawerListAdapter;
import Utility.ArticleFilter;
import Utility.CommentsComparator;
import Utility.ListViewInScrollViewHeight;
import Utility.MyComparator;
import Utility.ProgressDialogFragment;
import Utility.SessionManagement;
//import Utility.StorageHelper;
import Utility.ViewsComparator;
import Utility.WaitProgressFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.zeven.attini.R;



@SuppressLint("NewApi")
public class Home extends Activity implements OnItemClickListener
{
	TextView usersName;
	String encodedAccountName,SPHostUrl,refreshToken, realm,usersname, deviceAuthKey,lastName, fullName;
	ListView newsList;

	public static  String[] titles = null;
	public static   String[] descriptions = null;
	public static   String[] thumbnails = null;
	public static   String[] urls = null;
	//JSON Node Names
	public static final String NEWSITEMS ="NewsItems" ;

	public static final String  ARTICLEGUID ="ArticleGuid";
	public static final String  AUTHORLOGINNAME=" AuthorLoginName";
	public static final String  BODYTEXT ="BodyText";
	public static final String  BODY ="Body" ;
	//JSONObject ColSpan = ColSpan(i),
	public static final String CREATEDDATE = "CreatedDate";
	public static final String  ENTERPRISEKEYWORDS="EnterpriseKeywords";
	public static final String  ISPLACEHOLDERFORTHUMBNAIL="IsPlaceHolderForThumbnail";
	public static final String  NEWSSOURCEID="NewsSourceId";
	public static final String  IMAGEBLOBURLS="ImageBlobUrls";
	public static final String  NEWSSOURCETITLE="NewsSourceTitle";
	public static final String  NUMBEROFCOMMENTS="NumberOfComments";
	public static final String  NUMBEROFVIEWS="NumberOfViews";
	public static final String  PUBLISHEDDATE="PublishedDate";
	//JSONObject RowSpan = RowSpan(i),
	public static final String  THUMBANAILDESCRIPTION="ThumbnailDescription";
	public static final String  THUMBNAILPATH="ThumbnailPath" ;
	public static final String  TITLE = "Title" ;
	public static final String  URL= "Url" ;

	JSONArray newsItems = null;
	public static String avatarUrl = "";

	public static List<News> myNewsList = new ArrayList<News>();

	private  String items;

	private  TextView txtDrawerUserName;

	//URL to get JSON Array
	private WaitProgressFragment refDialog ;

	//  private static final Logout instance = new Logout();


	public ArrayList<AttiniNewsItem> itemsArray;

	public static ProgressDialog myDialog;
	public ProgressDialog newsDialog;
	ImageButton btnNav;
	private View viewIconColor;

	//FOr the navigationMenu
	private DrawerLayout mDrawerLayout;
	private ScrollView mainLinearLayout;
	private ListView mDrawerList, mCatagoryList, moptionsList;
	private ActionBarDrawerToggle mDrawerToggle;
	private MediaPlayer playSound;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private String[] iconColors;
	private String[] numberofLikesArray;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	public static List<News> myFinalNewsList = new ArrayList<News>();
	public static List<News> companyNewsList = new ArrayList<News>();
	public static ArrayList<String> catagoryArrayList = new ArrayList<String>();

	public static String[] catagoryList = new String[] {"Most liked", "Most commented", "Most Viewed", "About us"};//,"Logout"};
	public static String[] colorPallete = new String[] {"#1F1A17", "#62934D", "#F9B03F", "#7959BC", "#74B8DE", "#E65641", "#7CC8BB", "#D7CE5D", "#D6BE95", "#B694D1"};
	private String NewsSourceTitle = null;
	ProgressDialogFragment prog;
	private ProgressDialogFragment myprogressDialog;
	private List<News> updatedList;
	private boolean canContinue  = true;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{


		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawerlayout); 

		ConnectionDetector cd = new ConnectionDetector(Home.this);
		if(cd.isConnectingToInternet()== false)
		{
			new AlertDialog.Builder(this)
			.setTitle("Network error")
			.setMessage("Please check your internet connection")
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) 
				{ 
					canContinue = false;
					finish();
				}
			})
			.show();
		}

		else

			txtDrawerUserName = (TextView)findViewById(R.id.txtUserDrawer);
		viewIconColor = (View)findViewById(R.id.viewIconColor);
		mainLinearLayout = (ScrollView)findViewById(R.id.left_drawer);
		mCatagoryList = (ListView)findViewById(R.id.list_slidermenu2);
		moptionsList = (ListView)findViewById(R.id.list_slidermenu3);




		Intent in = getIntent();
		SPHostUrl = in.getStringExtra("SPHostUrl");
		encodedAccountName = in.getStringExtra("EncodedAccountName");
		deviceAuthKey = in.getStringExtra("DeviceAuthKey");
		usersname = in.getStringExtra("UsersName");
		avatarUrl = in.getStringExtra("AvatarUrl");
		lastName = in.getStringExtra("LastName");
		fullName = usersname+" "+ lastName;



		String registerContet = EndPoints.FetchNewsItemsUrl + "spHostUrl="+SPHostUrl + "&encodedAccountName="+encodedAccountName+"&deviceAuthKey="+ deviceAuthKey+"&count=50";

		int id = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView txtTitle = (TextView) findViewById(id);
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTextSize(20);
        txtTitle.setTypeface(null, Typeface.BOLD);
        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
       
        txtTitle.setWidth(width);
      Typeface typaFace = Typeface.createFromAsset(getAssets(), "RobotoSlab-Bold.ttf");
      txtTitle.setTypeface(typaFace);
		mTitle = mDrawerTitle = getTitle();
		getActionBar().setDisplayShowHomeEnabled(false);



		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		txtDrawerUserName.setText(fullName);
		txtDrawerUserName.setTextColor(Color.WHITE);
		txtDrawerUserName.setPadding(10, 10, 10, 10);


		navDrawerItems = new ArrayList<NavDrawerItem>();

		try 
		{


			items = new FetchItems().execute(registerContet).get();

			myFinalNewsList = new GetList().execute(items).get();
			
			if(myFinalNewsList.size() == 0)
			{
				new AlertDialog.Builder(Home.this)
				.setTitle("Something went wrong!")
			    .setMessage("Problem while fetching the news. Try again later.")
			   
			    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			   
			     .show();
				
			}

			Map<String, String> idToColorMap = new HashMap<String, String>();
			int colorIndex = 0;
			for (int i = 0; i < myFinalNewsList.size(); i++) 
			{
				if (myFinalNewsList.size() > 1 && !idToColorMap.containsKey(myFinalNewsList.get(i).getNewsSourceId())) {
					News currentNews = myFinalNewsList.get(i);
					currentNews.setColor(colorPallete[colorIndex]);
					idToColorMap.put(currentNews.getNewsSourceId(), colorPallete[colorIndex]);

					for (int j = i + 1; j < myFinalNewsList.size(); j++) {
						if (myFinalNewsList.get(j).getNewsSourceId().equals(currentNews.getNewsSourceId())) {
							myFinalNewsList.get(j).setColor(colorPallete[colorIndex]);
						}
					}

					if (++colorIndex == colorPallete.length) {
						colorIndex = 0;
					}
				} else {
					myFinalNewsList.get(0).setColor(colorPallete[0]);
					idToColorMap.put(myFinalNewsList.get(0).getNewsSourceId(), colorPallete[0]);
				}  
			}



			ArrayList<String> isonColors = new ArrayList<String>();

			for(int j =0; j<myFinalNewsList.size(); j++)
			{
				String colorz = myFinalNewsList.get(j).getColor();
				isonColors.add(colorz);
			}

			TreeSet<String> mySet =  new TreeSet<String>(isonColors);
			//iconColors = isonColors.toArray(new String[isonColors.size()]);
		
		//	iconColors = new HashSet<String>(Arrays.asList(iconColors)).toArray(new String[0]);
			iconColors =  mySet.toArray(new String[mySet.size()]);
			iconColors = addFirst(iconColors, "#FFFFFF");

			navMenuTitles = new GetNavDrawerItems().execute(items).get();
			
			navMenuTitles = new HashSet<String>(Arrays.asList(navMenuTitles)).toArray(new String[0]);
			navMenuTitles = addFirst(navMenuTitles, "All News");
			for(int j =0 ;j <navMenuTitles.length && j<iconColors.length;j++)
			{

				navDrawerItems.add(new NavDrawerItem(navMenuTitles[j], iconColors[j]));	


			}
			


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




		// Recycle the typed array
		navMenuIcons.recycle();


		// setting the nav drawer list adapter

		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.hamburger_button, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				) {
			public void onDrawerClosed(View view) 
			{

				getActionBar().setTitle("Attini Comms");
				// calling onPrepareOptionsMenu() to show action bar icons
				
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Settings");
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
				
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) 
		{
			// on first time display view for first nav item

			displayView(0);
		}



		//Second list
		
		final View footerView =  ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.logoutfooter, null, false);
        mCatagoryList.addFooterView(footerView);
        footerView.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				// Logout
				footerView.setBackgroundResource(R.drawable.list_selector1);
				SessionManagement session = new SessionManagement(getApplicationContext());
				session.logoutUser();
				finish();

			}
		});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Home.this, R.layout.drawer_list_item_2, R.id.txtCatagories, catagoryList);
		mCatagoryList.setAdapter(adapter);
		mCatagoryList.setOnItemClickListener(this);
		
	
	}
	public static List<News> getMyFinalNewsList() {
		return myFinalNewsList;
	}
	public static void setMyFinalNewsList(List<News> myFinalNewsList) {
		Home.myFinalNewsList = myFinalNewsList;
	}
	public static int[] convertIntegers(List<Integer> integers)
	{
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}
	private class GetNavDrawerItems extends AsyncTask<String, String, String[]>
	{

		@Override
		protected String[] doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			JSONArray jObject;
			ArrayList<String> myTitleList = new ArrayList<String>();
			String[] myTitlesArray =null;

			try 
			{
				jObject = new JSONArray(params[0]);
				for (int i = 0; i < jObject.length(); i++)
				{

					JSONObject menuObject = jObject.getJSONObject(i);
					String newsSourceTitle = menuObject.getString("NewsSourceTitle");

					myTitleList.add(newsSourceTitle);
				}

			}
			catch(Exception e)
			{

			}
			return myTitleList.toArray(new String[myTitleList.size()]);

		}
	}
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();

		ListViewInScrollViewHeight.setListViewHeightBasedOnChildren(mCatagoryList);
		ListViewInScrollViewHeight.setListViewHeightBasedOnChildren(mDrawerList);



	}

	private class GetList extends AsyncTask<String, String, List<News>>
	{


		@Override
		protected void onPostExecute(List<News> result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
		}

		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();



		}

		@Override
		protected List<News> doInBackground(String... params)
		{
			// TODO Auto-generated method stub
			myNewsList.clear();
			JSONArray jObject;
			try 
			{
				jObject = new JSONArray(params[0]);
				for (int i = 0; i < jObject.length(); i++)
				{
					JSONObject menuObject = jObject.getJSONObject(i);

					String title= menuObject.getString("Title");
					String description= menuObject.getString("BodyText");
					String thumbnail= menuObject.getString("ThumbnailPath");
					String newsUrl = menuObject.getString("Url");
					String body = menuObject.getString("Body");
					String newsBigImage = menuObject.getString("ThumbnailPath");
					String newsComments = menuObject.getString("NumberOfComments");
					String newsViews = menuObject.getString("NumberOfViews");
					String publishedDate = menuObject.getString("PublishedDate");
					String articleGuid = menuObject.getString("ArticleGuid");
					String newsSourceId = menuObject.getString("NewsSourceId");
					String newsId  = menuObject.getString("ID");
					String publisherName = menuObject.getString("AuthorDisplayName");
					String newsSourceTitle = menuObject.getString("NewsSourceTitle");
					String newsLikes  = menuObject.getString("NumberOfLikes");
					String tags = menuObject.getString("EnterpriseKeywords");



					myNewsList.add(new News(title, description, thumbnail, newsUrl, body, newsBigImage, newsComments, newsViews, publishedDate, articleGuid, newsSourceId, newsId, publisherName, newsSourceTitle, newsLikes, tags));



				}

			}
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return myNewsList;
		}



	}
	// Get response from the News API	    
	private class FetchItems extends AsyncTask<String, String, String> 
	{
		// TODO Auto-generated method stub
		ProgressDialog myDialog; 
		boolean worked;
		@Override
		protected String doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			HttpResponse response =null;
			String resultString = "";
			String myResponseBody = "" ;
			// Creating HTTP client
			HttpClient httpClient = new DefaultHttpClient();
			// Creating HTTP Post
			HttpGet request = new HttpGet(params[0]);
			try 
			{
				response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()== 200)
				{
					HttpEntity entity = response.getEntity();
					if (entity != null)
					{

						InputStream inputStream = entity.getContent();
						myResponseBody = convertToString(inputStream);
					}
				}
				else
				{
					Toast.makeText(Home.this, "Something went wrong!", Toast.LENGTH_LONG).show();
				}
			}
			catch(Exception e)
			{
			}
			return myResponseBody;
		}





		@Override
		protected void onPostExecute(String result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(refDialog!=null)
			{
				refDialog.dismiss();
			}

		}





		@Override
		protected void onPreExecute() 
		{
			// TODO Auto-generated method stub
			super.onPreExecute();
		
			ConnectionDetector cd = new ConnectionDetector(Home.this);
			if(cd.isConnectingToInternet()== false)
			{
				new AlertDialog.Builder(Home.this)
			    .setTitle("Network error")
			    .setMessage("Please check your internet connection")
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) 
			        { 
			        	worked = false;
			            finish();
			        }
			     })
			    .show();
			}
			else
			{
				worked = true;
			if(refDialog!=null)
			{
				refDialog =null;
			}
			refDialog =  WaitProgressFragment.newInstance();
			
			refDialog.show(getFragmentManager(), "Wait");
			}
		}




		private String convertToString(InputStream inputStream) 
		{
			// TODO Auto-generated method stub
			StringBuffer string = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			try 
			{
				while ((line = reader.readLine()) != null) 
				{
					string.append(line + "\n");
				}
			} 
			catch (IOException e) {}
			return string.toString();
		}


	}

	@Override
	protected void onRestart() 
	{

		// TODO Auto-generated method stub
		super.onRestart();

		if(HomeFragment.myDialog!= null && HomeFragment.myDialog.isShowing())
		{
			HomeFragment.myDialog.dismiss();
		}
	}
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
	ListView.OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) 
		{
			// display view for selected nav drawer item


			displayView(position);
		}
	}

		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{ 
		Fragment fragment = null;
		// toggle nav drawer on selecting action bar app icon/title
		ProgressDialog pg;
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) 
		{

		case R.id.action_refresh:

			String registerContet = EndPoints.FetchNewsItemsUrl + "spHostUrl="+SPHostUrl + "&encodedAccountName="+encodedAccountName+"&deviceAuthKey="+ deviceAuthKey+"&count=50";
			new FetchItems().execute(registerContet);
		/*	new GetList().execute();
			
			myFinalNewsList = getMyFinalNewsList();*/
			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),myFinalNewsList);
			
			return true;
			

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}


	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mainLinearLayout);
		menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * @param myPosition 
	 * */
	private void displayView(int position) 
	{

		Fragment fragment = null;

		//Filter
		if(position>0)
		{
			String myPosition = null;

			myPosition =  navDrawerItems.get(position).getTitle();
			companyNewsList = Lists.newArrayList(Collections2.filter(myFinalNewsList, new ArticleFilter(myPosition)));

			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),companyNewsList);
		}
		else if(position==0 && (navDrawerItems.get(position).getTitle()=="All News"))
		{

			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),myFinalNewsList);
		}




		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			//setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mainLinearLayout);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}


	/**
	 * To add "Home" as first item in existing array of titles
	 */

	@SuppressWarnings("unchecked")
	public static <T> T[] addFirst(T[] current, T value) {

		T[] newone = (T[]) Array.newInstance(current.getClass()
				.getComponentType(), current.length + 1);
		copy(current, newone, 0, 1, current.length);
		newone[0] = value;
		return newone;
	}
	public static <T> T[] copy(T[] from, T[] to, int fromPos, int toPos,
			int length) {
		System.arraycopy(from, fromPos, to, toPos, length);
		return to;
	}

	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = "Attini Comms";
		
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	//Filters
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		companyNewsList = myFinalNewsList;
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch (position)
		{
		case 0:
			
			Collections.sort(companyNewsList, new MyComparator());
			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),companyNewsList);

			break;

		case 1:
			//Most commented filter
			Collections.sort(companyNewsList, new CommentsComparator());
			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),companyNewsList);

			break;

		case 2:
			// Most Viewed Filter
			Collections.sort(companyNewsList, new ViewsComparator());
			fragment = new HomeFragment(SPHostUrl,encodedAccountName,deviceAuthKey,usersname,avatarUrl, fullName,getApplicationContext(),companyNewsList);

			break;

		case 3:

			final TextView message = new TextView(Home.this);
			  
			  final SpannableString s = new SpannableString("To find out more about Attini Comms, and the people behind it, please check out our site at, http://attini.com");
			  Linkify.addLinks(s, Linkify.WEB_URLS);
			  message.setText(s);
			  message.setMovementMethod(LinkMovementMethod.getInstance());
			  message.setPadding(12, 12, 12, 12);

			  	new AlertDialog.Builder(Home.this)
			   .setTitle("About us")
			   .setCancelable(true)
			   .setIcon(android.R.drawable.ic_dialog_info)
			   .setPositiveButton("Ok", null)
			   .setView(message)
			   .show();
			  	

			break;
		}


		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mCatagoryList.setItemChecked(position, true);
			mCatagoryList.setSelection(position);
			//setTitle(catagoryList[position]);
			mDrawerLayout.closeDrawer(mainLinearLayout);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}




}






