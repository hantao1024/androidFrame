package frame.base.com.publicadapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiplexingPagerAdapter<T> extends PagerAdapter {
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;  
	}
	
	/**
	* 获取view方法，子类实现这个方法来获取渲染View
	*
	* @param convertView 如果是null则没有可复用的View，如果非null则是可复用的View
	* @param position
	* @return
	*/
	protected abstract View getView(View convertView, int position);
	
	/**命名跟ListView的Adapter留下的接口名一样。
	内部实现是维护一个View池来保存被destroyItem销毁的View，在instantiateItem的时候先看池子里有没有闲置可用的View。
	使用这个View池的代码如下：*/
	private SparseArray<View> mPageViews = new SparseArray<View>();
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
	    // 获取复用的view给子类去用，没有可复用的view时为null
	    //pullViewFromPool()就是传给实现类的convertView
	    View view = getView(pullViewFromPool(), position);
	    // 记录该view以在destroyItem中能找到
	    mPageViews.put(position, view);
	    // 添加到view pager
	    container.addView(view);
	    return view;
	}
	
	public View getItemView(int position){
		return mPageViews.get(position) ; 
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	    View view = mPageViews.get(position);
	    // 把要删除的view放到pool里以供复用
	    pushViewToPool(view);
	    // 从view pager中删除
	    container.removeView(view);
	}
	
	/*View池的实现如下：*/
	//View池数据结构
	private List<View> mPageViewPool = new ArrayList<View>();
	
	 //从池子里拿View
	private View pullViewFromPool() {
	    View view = null;
	    for (View v : mPageViewPool) {
	        view = v;
	        break;
	    }
	    if (view != null) {
	    mPageViewPool.remove(view);
	    }
	    return view;
	}
	
	 //把View倒进池子
	private void pushViewToPool(View view) {
	    if (!mPageViewPool.contains(view)) {
	        mPageViewPool.add(view);
	    }
	}
	
	protected List<T> mData;
    
    @Override
    public int getCount() {  
        return mData ==null ? 0 : mData.size();  
    }
    
    public T getItem(int position) {  
        return mData.get(position);  
    }  
    
    public void setData(List<T> mData){
    	this.mData = mData ; 
    }
    
    public List<T> getData(){
    	return mData ; 
    }
    
    public void reLoad(List<T> appedList){
		setData(appedList) ;
	}
	
	
	
}
