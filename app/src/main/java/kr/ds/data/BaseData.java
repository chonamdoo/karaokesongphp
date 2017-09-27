package kr.ds.data;

public abstract class BaseData {
	
	public abstract BaseData clear();
	public abstract BaseData setUrl(String url);
	public abstract BaseData setParam(String param);
	public abstract BaseData getView();
	public abstract <T> BaseData getViewPost(T post);
	public abstract <T> BaseData setCallBack(T resultListener);

}
