package frame.base.com.publicutils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import frame.base.com.R;


/**
 * 网络请求框
 * @author liuguoyan
 * 
 * <p>这里我们把我们的提示框添加如下的部分
 * <br>1:圆角半透灰色背景
 * <br>2:左侧圆形loading(最少有一个左侧的loading)
 * <br>3:中间loading文字
 * <br>4:右侧关闭加载按钮
 * 
 * 注意:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * <p>这里我们不要把此Dialog做成单例或者池化,因为它持有  Context 对象,单例或者池化的话,有可能会致Activity泄露
 */
public class ProtocolDectorDialog extends Dialog implements ProtocolStatusObserver {
	
	public static final int DEFAULT_FRAME_WIDTH = 200 ;
	
	public static enum STYLE{
		DEF,
		NO_CLOSE,
		NO_CLOSE_TXT,
		NO_CLOSE_TXT_BACK
	}
	
	/**主布局**/
	private RelativeLayout mainLayout ;
	
	/**圆角半透灰色背景**/
	private RelativeLayout mRoundCornerBack ;
	
	/**左侧圆形loading**/
	private ProgressBar mLeftLoadingView ;
	
	/**中间加载文字**/
	private TextView mLoadingText ;
	
	/**右侧关闭按钮**/
	private TextView mCloseView ;
	
	/** 消息 */
	private String message ;
	
	private ProtocolDectorDialog(Context context) {
		super(context, R.style.protocol_res_http_loading_transbac_full) ;
		genrateLayout(context) ;
	}
	
	@Override
	public void show() {
		try {
			if (getContext()==null) return ;
			setContentView(mainLayout) ;
			this.getWindow().setWindowAnimations(R.style.protocol_res_dialogWindowAnim);
			super.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private View genrateLayout(Context context){
		
		GradientDrawable mGradientDrawable = new GradientDrawable() ;
		mGradientDrawable.setAlpha(150);
		mGradientDrawable.setCornerRadius(dip2px(context, 5));
		mGradientDrawable.setColor(Color.GRAY);
		
		mainLayout = new RelativeLayout(context) ;
		LayoutParams mainLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT) ;
		mainLayout.setLayoutParams(mainLayoutParams) ;

		mRoundCornerBack = new RelativeLayout(context) ;
		int rBackVpadding = dip2px(context, 5) ;
		int rBackHpadding = dip2px(context, 10) ;
		mRoundCornerBack.setPadding(rBackHpadding, rBackVpadding, rBackHpadding, rBackVpadding) ;
		mRoundCornerBack.setBackgroundDrawable(mGradientDrawable);
		LayoutParams mRoundCornerBackParams = new LayoutParams(dip2px(context, DEFAULT_FRAME_WIDTH) , LayoutParams.WRAP_CONTENT) ;
		mRoundCornerBackParams.addRule(RelativeLayout.CENTER_IN_PARENT) ;
		mainLayout.addView(mRoundCornerBack, mRoundCornerBackParams) ;

		mLeftLoadingView = new ProgressBar(context) ;
		LayoutParams mLeftLoadingViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) ;
		mLeftLoadingViewParams.addRule(RelativeLayout.CENTER_VERTICAL) ;
		mRoundCornerBack.addView(mLeftLoadingView, mLeftLoadingViewParams) ;

		mLoadingText = new TextView(context) ;
		mLoadingText.setSingleLine(true) ;
		mLoadingText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dip2px(context, 15)) ;
		mLoadingText.setTextColor(Color.WHITE) ;
		LayoutParams mLoadingTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) ;
		mLoadingTextParams.addRule(RelativeLayout.CENTER_IN_PARENT) ;
		mLoadingTextParams.leftMargin =  dip2px(context, 5) ;
		mRoundCornerBack.addView(mLoadingText, mLoadingTextParams) ;

		StateListDrawable mStateListDrawable = new StateListDrawable();
		mStateListDrawable.addState(new int[]{-android.R.attr.state_focused,android.R.attr.state_pressed}, context.getResources().getDrawable( R.mipmap.protocol_res_mj)) ;
		mStateListDrawable.addState(new int[]{-android.R.attr.state_focused,-android.R.attr.state_pressed}, context.getResources().getDrawable( R.mipmap.protocol_res_mk)) ;
		mCloseView  = new TextView(context) ;
		mCloseView.setBackgroundDrawable(mStateListDrawable) ;
		LayoutParams mCloseViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) ;
		mCloseViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT) ;
		mCloseViewParams.addRule(RelativeLayout.CENTER_VERTICAL) ;
		mRoundCornerBack.addView(mCloseView, mCloseViewParams) ;
		mCloseView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtocolDectorDialog.this.dismiss() ;
			}
		}) ;

		return mainLayout ;
	}

	public static ProtocolDectorDialog getDector(Context mContext, STYLE style){
		return getDector(mContext, style, "")  ;
	}

	public static ProtocolDectorDialog getDector(Context mContext, STYLE style, String msg){
		return getDector(mContext, style, msg, null)  ;
	}

	public static ProtocolDectorDialog getDector(Context mContext, STYLE style, String msg, OnDismissListener listener){
		ProtocolDectorDialog mDector = new ProtocolDectorDialog(mContext) ;
		mDector.setMessageInfo(msg) ;
		mDector.setStyle(style) ;
		mDector.setOnDismissListener(listener) ;
		return mDector  ;
	}

	public void setStyle(STYLE style){
		switch (style) {
		case NO_CLOSE:
			mCloseView.setVisibility(View.GONE) ;
			break;
		case NO_CLOSE_TXT:
			mCloseView.setVisibility(View.GONE) ;
			mLoadingText.setVisibility(View.GONE) ;
			LayoutParams mRoundCornerBackParams = new LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT) ;
			mRoundCornerBack.setLayoutParams(mRoundCornerBackParams) ;
			break;
		case NO_CLOSE_TXT_BACK :
			mCloseView.setVisibility(View.GONE) ;
			mLoadingText.setVisibility(View.GONE) ;
			LayoutParams mRoundCornerBackParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT) ;
			mRoundCornerBack.setLayoutParams(mRoundCornerBackParams2) ; 
			mRoundCornerBack.setBackgroundColor(Color.TRANSPARENT) ;
			break ;
		default:
			break;
		}
	}
	
	public void setMessageInfo(String msg){
		mLoadingText.setText(msg) ; 
	}
	
	@Override
	public void onObserverStart() {
		try {
			this.show(); ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObserverSuccess() {
		try {
			this.dismiss() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObserverFailure() {
		try {
			this.dismiss() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObserverCancel() {
		try {
			this.dismiss() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObserverFinish() {
		try {
			this.dismiss() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onObserverProgress(long bytesWritten, long totalSize) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onObserverRetry(int retryNo) {
		// TODO Auto-generated method stub
	}
	
	public static  int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
	
    public static  int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	
	
}
