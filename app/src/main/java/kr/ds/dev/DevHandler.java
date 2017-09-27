package kr.ds.dev;

/**
 * Created by Administrator on 2017-02-07.
 */
public class DevHandler {
    private String dd_uid;
    private String name;
    private String link;
    private String image;
    private String sub_name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }
}
