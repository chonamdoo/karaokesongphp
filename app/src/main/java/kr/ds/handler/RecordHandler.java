package kr.ds.handler;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016-08-05.
 */
public class RecordHandler implements Parcelable{

    public String contents_id;
    public String image;
    public String title;
    public String video_id;
    public String url_file;
    public String regdate;
    public boolean play;

    public RecordHandler(){

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContents_id() {
        return contents_id;
    }

    public void setContents_id(String contents_id) {
        this.contents_id = contents_id;
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

    public String getUrl_file() {
        return url_file;
    }

    public void setUrl_file(String url_file) {
        this.url_file = url_file;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public RecordHandler(Parcel src){
        this.contents_id = src.readString();
        this.image = src.readString();
        this.title = src.readString();
        this.video_id = src.readString();
        this.url_file = src.readString();
        this.regdate = src.readString();
    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(this.contents_id);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.video_id);
        dest.writeString(this.url_file);
        dest.writeString(this.regdate);
    }
    public static final Creator CREATOR = new Creator() { //데이터 가져오기

        @Override
        public Object createFromParcel(Parcel in) {
            // TODO Auto-generated method stub
            return new RecordHandler(in);
        }
        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return new RecordHandler[size];
        }
    };





}
