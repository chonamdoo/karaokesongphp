package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

public class WebHandler implements Parcelable {
	private String mUrl;
	
	public WebHandler(String url){
		mUrl = url;
	}
	public WebHandler(Parcel src){
		mUrl = src.readString();
	}
	
	public String getmUrl() {
		return mUrl;
	}
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mUrl);
	}

	public static final Creator CREATOR = new Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new WebHandler(in);
		}
		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new WebHandler[size];
		}
	};

}
