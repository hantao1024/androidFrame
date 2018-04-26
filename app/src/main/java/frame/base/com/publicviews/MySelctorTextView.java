package frame.base.com.publicviews;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MySelctorTextView extends TextView {
	
	public MySelctorTextView(Context context) {
		super(context);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}
	public MySelctorTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}
	public MySelctorTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}
	
	public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50,
			0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
	
	//已经放弃,即:用户已经把手指滑出控件区域
		private boolean unlock = false ; 
		
		public OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
			// 变暗(三个-50，值越大则效果越深)
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					setColorFileter(v) ; 
					unlock = false ; 
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					//合理区域内
					if (event.getX() < v.getWidth() && event.getX()>0 && event.getY() < v.getHeight() && event.getY() > 0  && !unlock) {
						clearColorFilter(v) ; 
						mPerformClick();
					}
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					//不在合理区域内
					if (!(event.getX() < v.getWidth() && event.getX()>0 && event.getY() < v.getHeight() && event.getY() > 0 )) {
						clearColorFilter(v) ; 
						unlock = true ; 
					}
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					clearColorFilter(v) ; 
				}
				
				return true; 
			}
		} ;
		
		private void mPerformClick() {
			MySelctorTextView.this.performClick();
		}
		
		private void setColorFileter(View v){
			TextView textView = (TextView) v ;
			
			Drawable drawable = textView.getBackground()  ;
			if (drawable!=null) {
				drawable.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK)) ;
			}
			
			Drawable[] componds = textView.getCompoundDrawables() ;
			
			for (int i = 0; i < componds.length; i++) {
				if (componds[i]!=null) {
					componds[i].setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK)) ;
				}
			}
			
		}
		
		private void clearColorFilter(View v){
			TextView textView = (TextView) v ;
			
			Drawable drawable = textView.getBackground()  ;
			if (drawable!=null) {
				drawable.clearColorFilter() ; 
			}
			
			Drawable[] componds = textView.getCompoundDrawables() ;
			for (int i = 0; i < componds.length; i++) {
				if (componds[i]!=null) {
					componds[i].clearColorFilter() ; 
				}
			}
			
			
		}
	
}
