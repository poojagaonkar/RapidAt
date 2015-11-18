package Attini.Model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AttiniNewsItem 
{
	@JsonProperty("NewsSourceId")
	public String NewsSourceId;
	
	@JsonProperty("ArticleGuid")
	public String ArticleGuid;
	
	@JsonProperty("Url")
	public String Url;
	
	@JsonProperty("Title")
	public String Title;
	
	@JsonProperty("BodyText")
	public String BodyText;
	
	@JsonProperty("Body")
	public String Body;
	
	@JsonProperty("EnterpriseKeywords")
	public String EnterpriseKeywords;
	
	@JsonProperty("ThumbnailPath")
	public String ThumbnailPath;
	
	@JsonProperty("ThumbnailDescription")
	public String ThumbnailDescription;
	
	@JsonProperty("ImageUrls")
	public String ImageUrls;
	
	@JsonProperty("ImageBlobUrls")
	public String ImageBlobUrls;
	
	@JsonProperty("isPlaceHolderForThumbnail")
	public String isPlaceHolderForThumbnail;
	
	
	@JsonProperty("PublishedDate")
	public String PublishedDate;
	
	@JsonProperty("NumberOfComments")
	public String NumberOfComments;
	
	@JsonProperty("CreatedDate")
	public String CreatedDate;
	
	public String getNewsSourceId() {
		return NewsSourceId;
	}

	public void setNewsSourceId(String newsSourceId) {
		NewsSourceId = newsSourceId;
	}

	public String getArticleGuid() {
		return ArticleGuid;
	}

	public void setArticleGuid(String articleGuid) {
		ArticleGuid = articleGuid;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getBodyText() {
		return BodyText;
	}

	public void setBodyText(String bodyText) {
		BodyText = bodyText;
	}

	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public String getEnterpriseKeywords() {
		return EnterpriseKeywords;
	}

	public void setEnterpriseKeywords(String enterpriseKeywords) {
		EnterpriseKeywords = enterpriseKeywords;
	}

	public String getThumbnailPath() {
		return ThumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		ThumbnailPath = thumbnailPath;
	}

	public String getThumbnailDescription() {
		return ThumbnailDescription;
	}

	public void setThumbnailDescription(String thumbnailDescription) {
		ThumbnailDescription = thumbnailDescription;
	}

	public String getImageUrls() {
		return ImageUrls;
	}

	public void setImageUrls(String imageUrls) {
		ImageUrls = imageUrls;
	}

	public String getImageBlobUrls() {
		return ImageBlobUrls;
	}

	public void setImageBlobUrls(String imageBlobUrls) {
		ImageBlobUrls = imageBlobUrls;
	}

	public String getIsPlaceHolderForThumbnail() {
		return isPlaceHolderForThumbnail;
	}

	public void setIsPlaceHolderForThumbnail(String isPlaceHolderForThumbnail) {
		this.isPlaceHolderForThumbnail = isPlaceHolderForThumbnail;
	}

	public String getPublishedDate() {
		return PublishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		PublishedDate = publishedDate;
	}

	public String getNumberOfComments() {
		return NumberOfComments;
	}

	public void setNumberOfComments(String numberOfComments) {
		NumberOfComments = numberOfComments;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getAuthorLoginName() {
		return AuthorLoginName;
	}

	public void setAuthorLoginName(String authorLoginName) {
		AuthorLoginName = authorLoginName;
	}

	@JsonProperty("AuthorLoginName")
	public String AuthorLoginName;
	
	public List<AttiniNewsItem> GetNewsRecords(String result)
	{
		
			List<AttiniNewsItem> attini = null;
			try 
			{
				attini = (List<AttiniNewsItem>) new ObjectMapper().readValue(result, AttiniNewsItem.class);
			}
			catch (JsonParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (JsonMappingException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return attini;
		
		
	}
}
