package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-08-05.
 */
public class ChannelHandler implements Parcelable{

    public String ccd_uid;
    public String title;
    public String playlist_id;
    public String date;
    public String image;
    public String total;


    public ChannelHandler(){

    }

    public String getCcd_uid() {
        return ccd_uid;
    }

    public void setCcd_uid(String ccd_uid) {
        this.ccd_uid = ccd_uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(String playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ChannelHandler(Parcel src){
        this.ccd_uid = src.readString();
        this.image = src.readString();
        this.title = src.readString();
        this.playlist_id = src.readString();
        this.date = src.readString();
        this.total = src.readString();


    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.ccd_uid);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.playlist_id);
        dest.writeString(this.date);
        dest.writeString(this.total);
    }
    public static final Creator CREATOR = new Creator() { //데이터 가져오기

        @Override
        public Object createFromParcel(Parcel in) {
            // TODO Auto-generated method stub
            return new ChannelHandler(in);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ChannelHandler[size];
        }
    };





}
