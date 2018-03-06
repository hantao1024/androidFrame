/**
 * 
 */
package frame.base.com.publicmanager;


/**
 * 所有业务处理都应该继承此类：
 * 1.并且必须有一个空参的构造函数
 * 2.如果需要注册到事件总线，在preferences.xml中注册manager，注册的次序就是事件发布的次序
 * @author tangchao
 *
 */
public abstract class BaseManager implements Manager{
	/**
	 * 在应用启动的时候注册到事件总线
	 */
	public void onAppStart(){

	}
}
