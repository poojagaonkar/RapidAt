package Attini.Model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserInformation 
{

	@JsonProperty("HostUrl")
	public  String HostUrl;

	public  String getHostUrl() {
		return HostUrl;
	}

	public void setHostUrl(String hostUrl) {
		HostUrl = hostUrl;
	}

	@JsonProperty("EncodedAccountName")
	public  String EncodedAccountName;

	public  String getEncodedAccountName() {
		return EncodedAccountName;
	}

	public void setEncodedAccountName(String encodedAccountName) {
		EncodedAccountName = encodedAccountName;
	}
	
	@JsonProperty("Email")
	public  String Email;
	
	@JsonProperty("DeviceAuthKey")
	public  String DeviceAuthKey;
	
	public String getDeviceAuthKey() {
		return DeviceAuthKey;
	}

	public void setDeviceAuthKey(String deviceAuthKey) {
		DeviceAuthKey = deviceAuthKey;
	}

	@JsonProperty("FirstName")
	public  String FirstName;
	
	@JsonProperty("LastName")
	public  String LastName;
	
	@JsonProperty("AvatarUrl")
	public  String AvatarUrl;
	
	@JsonProperty("AccountName")
	public  String AccountName;
	
	@JsonProperty("SPHostUrl")
	public  String SPHostUrl;
	
/*	@JsonProperty("RefreshToken")
	public  String RefreshToken;*/
	
	public  String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public  String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public  String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public  String getAvatarUrl() {
		return AvatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		AvatarUrl = avatarUrl;
	}

	public  String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public  String getSPHostUrl() {
		return SPHostUrl;
	}

	public void setSPHostUrl(String sPHostUrl) {
		SPHostUrl = sPHostUrl;
	}

	/*public  String getRefreshToken() {
		return RefreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}

	public  String getRealm() {
		return Realm;
	}

	public void setRealm(String realm) {
		Realm = realm;
	}*/

	public  String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public  String getPartitionKey() {
		return PartitionKey;
	}

	public void setPartitionKey(String partitionKey) {
		PartitionKey = partitionKey;
	}

	public  String getRowKey() {
		return RowKey;
	}

	public void setRowKey(String rowKey) {
		RowKey = rowKey;
	}

	@JsonProperty("Realm")
	public  String Realm;
	
	@JsonProperty("Timestamp")
	public  String Timestamp;
	
	@JsonProperty("PartitionKey")
	public  String PartitionKey;
	
	@JsonProperty("RowKey")
	public   String RowKey;
	
	
	public UserInformation GetUserInformation(String result) throws JsonParseException, JsonMappingException, IOException 
	{
		
		UserInformation uInfo = new ObjectMapper().readValue(result, UserInformation.class);
		
		return uInfo;
	  
	}

}
