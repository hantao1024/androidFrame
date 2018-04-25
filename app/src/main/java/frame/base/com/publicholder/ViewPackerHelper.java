package frame.base.com.publicholder;

import android.widget.TextView;

import frame.base.com.publicviews.EventImageView;
import frame.base.com.publicviews.LoadImageView;
import frame.base.com.publicviews.MyViewPager;
import frame.base.com.publicviews.PhotoView;

/**
 * 用来预加载图片信息,非系统的图片加载机制
 * @author liuguoyan
 */
public class ViewPackerHelper {
	
	public static class ViewHolder extends CartoonReadHolder{
		
		public EventImageView imageView;
		
		public TextView txt_terminal;
		
		@Override
		public LoadImageView getLoadImageView() {
			return imageView;
		} 
		
	}
	
	public static class PagerViewHolder extends CartoonReadHolder{
		
		public PhotoView imageView  ;
		
		public MyViewPager viewpager  ;
		
		@Override
		public LoadImageView getLoadImageView() {
			return imageView;
		}
		
	}
	
	
}
