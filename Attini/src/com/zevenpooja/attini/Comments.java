package com.zevenpooja.attini;

public class Comments 
{
	 String title;
	    String description;
	    String thumbnail;
	    String date;
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
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		
		public Comments(String name, String thumbnail, String date, String description) 
		{
			// TODO Auto-generated constructor stub
			this.title = name;
			this.date = date;
			this.description = description;
			this.thumbnail = thumbnail;
				
		}
}
