package com.zevenpooja.attini;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable
{

    
    String title;
    String description;
    String thumbnail;
    String newsUrl;
    String body;
    String newsBigImage ;
    String newsComments ;
    String newsViews;
    String newsLikes;
    String publishedDate;
    String articleGuid;
    String newsSourceId;
    String newsId ;
    String publisherName;
    String newsSourceTitle;
    String color;
    String tags;
    
    protected News(Parcel in) {
        title = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        newsUrl = in.readString();
        body = in.readString();
        newsBigImage = in.readString();
        newsComments = in.readString();
        newsViews = in.readString();
        publishedDate = in.readString();
        articleGuid = in.readString();
        newsSourceId = in.readString();
        newsId = in.readString();
        publisherName = in.readString();
        newsSourceTitle = in.readString();
        color = in.readString();
        tags = in.readString();
    }
    
    public String getNewsLikes() {
		return newsLikes;
	}

	public void setNewsLikes(String newsLikes) {
		this.newsLikes = newsLikes;
	}

	News(String title, String description, String thumbnail, String newsUrl, String body, String newsBigImage,  String newsComments,  String newsViews,
    String publishedDate,
    String articleGuid,
    String newsSourceId,
    String newsId ,
    String publisherName,
    String newsSourceTitle,
    String newsLikes, String tags)
    {
        
        this.title = title;
        this.description = description;
        this.articleGuid =articleGuid;
        this.thumbnail = thumbnail;
        this.newsUrl = newsUrl;
        this.body = body;
        this.newsBigImage = newsBigImage;
        this.newsComments = newsComments;
        this.newsViews = newsViews;
        this.publishedDate = publishedDate;
        this.newsId = newsId;
        this.newsSourceId = newsSourceId;
        this.publisherName = publisherName;
        //this.color = color;
        this.newsSourceTitle  =newsSourceTitle;
        this.newsLikes = newsLikes;
        this.tags = tags;
    }

	

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNewsBigImage() {
		return newsBigImage;
	}

	public void setNewsBigImage(String newsBigImage) {
		this.newsBigImage = newsBigImage;
	}

	public String getNewsComments() {
		return newsComments;
	}

	public void setNewsComments(String newsComments) {
		this.newsComments = newsComments;
	}

	public String getNewsViews() {
		return newsViews;
	}

	public void setNewsViews(String newsViews) {
		this.newsViews = newsViews;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getArticleGuid() {
		return articleGuid;
	}

	public void setArticleGuid(String articleGuid) {
		this.articleGuid = articleGuid;
	}

	public String getNewsSourceId() {
		return newsSourceId;
	}

	public void setNewsSourceId(String newsSourceId) {
		this.newsSourceId = newsSourceId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getNewsSourceTitle() {
		return newsSourceTitle;
	}

	public void setNewsSourceTitle(String newsSourceTitle) {
		this.newsSourceTitle = newsSourceTitle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(title);
	    dest.writeString(description);
	    dest.writeString(thumbnail);
	    dest.writeString(newsUrl);
	    dest.writeString(body);
	    dest.writeString(newsBigImage);
	    dest.writeString(newsComments);
	    dest.writeString(newsViews);
	    dest.writeString(publishedDate);
	    dest.writeString(articleGuid);
	    dest.writeString(newsSourceId);
	    dest.writeString(newsId);
	    dest.writeString(publisherName);
	    dest.writeString(newsSourceTitle);
	    dest.writeString(color);
	}
	@SuppressWarnings("unused")
	public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
	    @Override
	    public News createFromParcel(Parcel in) {
	        return new News(in);
	    }

	    @Override
	    public News[] newArray(int size) {
	        return new News[size];
	    }
	};
  
}
