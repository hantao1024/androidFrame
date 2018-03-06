package frame.base.com.publicutils;

/***
 * 
 * @author hantao
 * <p>
 * 网络请求状态监听器,可用于用于界面上的控件组织等操作<br>
 * 例如我们可以封装一个loading,实现Obeserver的接口,并在不同的方法中使用loading有不同变化,<br>
 * 加入到protocol中,这样就能实现自动状态变化了,同样也可以有多个状态的变化更新<br>
 */
public interface ProtocolStatusObserver {
	
	public void onObserverStart();
	
	public void onObserverSuccess() ;
	
	public void onObserverFailure() ;
	
	public void onObserverCancel() ;
	
	public void onObserverFinish() ;
	
	public void onObserverProgress(long bytesWritten, long totalSize) ;
	
	public void onObserverRetry(int retryNo) ; 
	
}
