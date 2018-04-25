package frame.base.com.publicviews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import frame.base.com.publicadapter.MultiplexingPagerAdapter;
import frame.base.com.publicholder.ViewPackerHelper;


/**
 * 
 * Hacky fix for Issue #4 and
 * http://code.google.com/p/android/issues/detail?id=18990
 * 
 * ScaleGestureDetector seems to mess up the touch events, which means that
 * ViewGroups which make use of onInterceptTouchEvent throw a lot of
 * IllegalArgumentException: pointerIndex out of range.
 * 
 * There's not much I can do in my code for now, but we can mask the result by
 * just catching the problem and ignoring it.
 * 
 * @author Chris Banes
 * 
 */
public class HackyViewPager extends ViewPager {
	
	public HackyViewPager(Context context) {
		super(context);
	}
	
	public HackyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		try {
			boolean superbool = super.onInterceptTouchEvent(event); 
			if (!superbool && event.getAction() != MotionEvent.ACTION_DOWN && getAdapter() instanceof MultiplexingPagerAdapter) {
				MultiplexingPagerAdapter adapter = (MultiplexingPagerAdapter) getAdapter() ; 
				View view = adapter.getItemView(getCurrentItem()) ;
				if (view != null) {
					ViewPackerHelper.PagerViewHolder holder = (ViewPackerHelper.PagerViewHolder) view.getTag() ;
					if (holder!=null && holder.imageView.getVisibility() == View.VISIBLE) {
						superbool = true ; 
					}
				}
			}
			return superbool;
		} catch (IllegalArgumentException e) {
			//不理会
			e.printStackTrace();
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			//不理会
			e.printStackTrace();
            return false;
        }catch (Exception e){
			return false;
		}
	}
	
}
