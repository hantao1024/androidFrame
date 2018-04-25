package frame.base.com.publicholder;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import frame.base.com.publicviews.LoadImageView;


/**
 * 漫画阅读界面的ViewHolder抽象
 * @author liuguoyan
 */
public abstract class CartoonReadHolder {
	
	public ProgressBar spinner;
	
	public TextView spinnerText;
	
	public RelativeLayout layout_ad ;
	
	/** 得到LoadImageView **/
	public abstract LoadImageView getLoadImageView();
	
	/**
	 * 得到广告布局
	 * @return
	 */
	public RelativeLayout getLayoutAd() {
		return layout_ad ; 
	}; 
}
