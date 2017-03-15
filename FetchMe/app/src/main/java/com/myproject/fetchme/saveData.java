package com.myproject.fetchme;


	public class  saveData {

		
		private String UserName;
		private String ProfileUrl;
		private String Imgurl;
		private String Image;
		
//Save our data collected from.Async class in here to define them
		public saveData() {
			
		}

		public saveData (String Imgurl, String UserName, String ProfileUrl, String Image )
		
		{
			super();
			this.UserName = UserName;
			this.ProfileUrl = ProfileUrl;
			this.Imgurl = Imgurl;
			this.Image = Image;
		}


		public String getUserName() {
			return UserName;
		}

		public void setUserName(String UserName) {
			this.UserName = UserName;
		}

		

	public String getProfileUrl() {
		return ProfileUrl;
	}

	public void setProfileUrl(String ProfileUrl) {
		this.ProfileUrl = ProfileUrl;
	}
	
	public String getImgurl() {
		return Imgurl;
	}

	public void setImgurl(String Imgurl) {
		this.Imgurl = Imgurl;
	}
		public String getImage() {
			return Image;
		}

		public void setImage(String Image) {
			this.Image = Image;
		}
	
}
