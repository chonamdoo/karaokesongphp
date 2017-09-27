package kr.ds.data;

public interface BaseHeaderResultListener {
	public <T> void OnComplete();
	public <T> void OnComplete(Object header_data, Object content_data);
	public void OnMessage(String str);
}
