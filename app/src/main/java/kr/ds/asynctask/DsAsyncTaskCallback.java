package kr.ds.asynctask;
/**
 * 콜백함수
 * @author Chodongsuk
 * @since 20150128
 * @param <T>
 */
public interface DsAsyncTaskCallback<T> {
	public void onPreExecute();
	public void onPostExecute(T result);
	public void onCancelled();
	public void Exception(Exception e);
	
}
