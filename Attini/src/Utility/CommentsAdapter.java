package Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zevenpooja.attini.NewsDetails;




public class CommentsAdapter extends BaseAdapter
{
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public Context myContext;
	public ImageLoader imageLoader;
	public Context mContext;
	public ProgressDialog pDialog;
	private ArrayList<HashMap<String, String>> dataList;
	static class LazyViewHolder
	{
		TextView title;
		TextView description;
		ImageView thumb_image;
		WebView imageWebView;
		TextView pubDate;
	}

	public CommentsAdapter(NewsDetails ctx, ArrayList<HashMap<String, String>> getList) 
	{
		// TODO Auto-generated constructor stub
		mContext = ctx;
		data=getList;
		inflater =LayoutInflater.from(ctx);
		imageLoader=new ImageLoader(ctx);

		notifyDataSetChanged();

	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position;
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View vi=convertView;
		LazyViewHolder viewHolder;
		if(convertView==null)
		{
			vi = inflater.inflate(com.zeven.attini.R.layout.comments_row,null);

			viewHolder = new LazyViewHolder();

			viewHolder.title = (TextView)vi.findViewById(com.zeven.attini.R.id.title); // title
			viewHolder.description = (TextView)vi.findViewById(com.zeven.attini.R.id.description); // artist name
			viewHolder.pubDate = (TextView)vi.findViewById(com.zeven.attini.R.id.txtPubDate);
			vi.setTag(viewHolder);
		}
		else
		{
			viewHolder = (LazyViewHolder)vi.getTag();
		}

		HashMap<String, String> song = new HashMap<String, String>();
		song = data.get(position);

		// Setting all values in listview
		//Set title
		String myTitle =song.get(NewsDetails.AUTHORDISPLAYNAME);
		StringBuilder sb = new StringBuilder(myTitle);

		int i = 0;
		while ((i = sb.indexOf(" ", i + 40)) != -1) 
		{
			sb.replace(i, i + 1, "\n");
		}

		viewHolder.title.setText(sb.toString());

		//Set description
		String mydescription = song.get(NewsDetails.TEXT);


		viewHolder.description.setText(mydescription);

		// Set image
		//   String bitmapUrl = song.get(Home.avatarUrl);

		//Set date
		String myDate = song.get(NewsDetails.DATEUPDATED);

		final String OLD_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		final String NEW_FORMAT = "HH:mm, MMMM dd, yyyy";

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

		return vi;
	}



	public void addData(ArrayList<HashMap<String, String>> newDataList) 
	{
		// add new datas to your ArrayList
		this.dataList = newDataList;
		// notify the adapter that new datas are added
		notifyDataSetChanged();
	} 


}


