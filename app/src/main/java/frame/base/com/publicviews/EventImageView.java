package frame.base.com.publicviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class EventImageView extends LoadImageView {
	
	public static final String TAG = "EventImageView" ;
	
	private GestureDetector mGestureDetector;
	
	public EventImageView(Context context) {
		super(context);
		init(context);
	}
	
	public EventImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public EventImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context mContext){
		mGestureDetector = new GestureDetector(mContext,new SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				if (mEventViewTapListener!=null) {
					mEventViewTapListener.onViewTap(EventImageView.this, e.getX(), e.getY(),e.getDownTime(),e.getRawX(), e.getRawY());
				}
				return true;
			}
		});
	}
	float mStartY;
	float mStartX;
	float mEndY;
	float mEndX;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mark) {
			if (mEventViewTapListener !=null) {
				mEventViewTapListener.onViewTap(EventImageView.this, event.getX(), event.getY(),event.getDownTime(),event.getRawX(), event.getRawY()) ;
			}
			return super.onTouchEvent(event);
		}else {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mStartY = event.getY();
					mStartX = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					mEndY = event.getY();
					mEndX = event.getX();
					float dy = mEndY - mStartY;
					if (Math.abs(dy) < 20) {
						break;  //低于20像素的滑动不算
					} else {
						mGestureDetector.onTouchEvent(event);
					}
				case MotionEvent.ACTION_UP:
					mEndY = event.getY();
					if (Math.abs(mEndY - mStartY) < 10) {  //相当于点击了屏幕  呼唤出控制界面
						if (mEventViewTapListener != null) {
							mEventViewTapListener.onViewTap(EventImageView.this, event.getX(), event.getY(), event.getDownTime(),event.getRawX(), event.getRawY());
						}
					}
					break;
			}

			return true;
		}

	}
	boolean mark=true;
	public void setMark(boolean mark){
		this.mark=mark;
	}
	private OnEventViewTapListener mEventViewTapListener;
	
	public void setOnEventViewTapListener(OnEventViewTapListener mEventViewTapListener){
		this.mEventViewTapListener = mEventViewTapListener;
	}
	
	public static interface OnEventViewTapListener {
		void onViewTap(View view, float x, float y, long downtime, float x1, float y1);
	}

	

}
