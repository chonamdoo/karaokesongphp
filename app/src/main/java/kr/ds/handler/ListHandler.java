package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by Administrator on 2016-08-05.
 */
public class ListHandler  implements Parcelable{

    public String dd_uid;
    public String image;
    public String title;
    public String video_id;
    public String date;
    public String hit;
    public String like;

    public ListHandler(){
    }

    public String getDd_uid() {
        return dd_uid;
    }

    public void setDd_uid(String dd_uid) {
        this.dd_uid = dd_uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public ListHandler (Parcel src){
        this.dd_uid = src.readString();
        this.image = src.readString();
        this.title = src.readString();
        this.video_id = src.readString();
        this.date = src.readString();
        this.hit = src.readString();
        this.like = src.readString();
    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.dd_uid);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.video_id);
        dest.writeString(this.date);
        dest.writeString(this.hit);
        dest.writeString(this.like);

    }
    public static final Creator CREATOR = new Creator() { //데이터 가져오기

        @Override
        public Object createFromParcel(Parcel in) {
            // TODO Auto-generated method stub
            return new ListHandler(in);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new ListHandler[size];
        }
    };





}
