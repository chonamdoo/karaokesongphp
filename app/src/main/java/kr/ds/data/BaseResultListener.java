package kr.ds.data;

public interface BaseResultListener {
	public <T> void OnComplete();
	public <T> void OnComplete(Object data);
	public void OnMessage(String str);
}
