package kr.ds.data;

public interface BaseSummeryResultListener {
	public <T> void OnComplete();
	public <T> void OnComplete(Object data, Object summery);
	public <T> void OnError();
	public void OnMessage(String str);


}
