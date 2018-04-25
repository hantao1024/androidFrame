package frame.base.com.publicviews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	
	public MyViewPager(Context context) {
		super(context);
	}
	
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		
		boolean supv = false;
		try {
			supv = super.onInterceptTouchEvent(arg0);
		} catch (Exception e) {
			e.printStackTrace() ; 
		}
		return supv;
	}
	
}


