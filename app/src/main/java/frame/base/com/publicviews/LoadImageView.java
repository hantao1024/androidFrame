package frame.base.com.publicviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 这个ImageView是用来包装下载任务的,我们不再把下载图片的工作,放在外面,我们直接把工作交到这个ImageView中,让它来处理,简化操作
 * @author liuguoyan
 */
public class LoadImageView extends ImageView {
	
	public LoadImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public LoadImageView(Context context) {
		super(context);
	}
	
	public LoadImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
}
