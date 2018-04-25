package frame.base.com.publicutils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 吐司 工具类
 * @author zhangjun
 *
 */
public class AlertManager {

	public enum HintType {
		HT_SUCCESS, HT_LOADING, HT_FAILED
	}

	public static final String TAG = "AlertManager";

	private ProgressDialog processDialog;

	private static AlertManager instance = null;

	private AlertManager() {
		processDialog = null;
	}

	/*
	 * get Singleton instance
	 */
	public static synchronized AlertManager getInstance() {
		if (instance == null)
			instance = new AlertManager();
		return instance;
	}

	public void showHint(Context context, HintType ht, String text) {
		makeToast(context, ht, text,null);
	}
	
	public void showHint(Context context, HintType ht, String text, DialogInterface.OnCancelListener dismissListener){
		makeToast(context, ht, text, dismissListener);
	}
	
	/***
	 * 阻止频繁弹toast
	 * @param mView
	 * @param context
	 * @param ht
	 * @param text
	 */
	public void showHintNoMultiply(Context context, View mView, HintType ht, String text){
		if(toastWrapers==null){
			toastWrapers=new ArrayList<View>();
		}
		if(toastWrapers.contains(mView)){
			return;
		} else {
			toastWrapers.add(mView);
		}
		makeToast(context, ht, text,null);
	}
	
	private List<View> toastWrapers;
	
	private void makeToast(Context context, HintType ht, String text, DialogInterface.OnCancelListener cancelListener){
		Toast toast = null;
		
		switch (ht) {
		
		case HT_LOADING:
			processDialog = new ProgressDialog(context);
			processDialog.setMessage(text);
			if (cancelListener!=null) {
				processDialog.setOnCancelListener(cancelListener);
			}
			break;
			
		case HT_SUCCESS:
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.BOTTOM, 0, 0);
//			LinearLayout toastView = (LinearLayout) toast.getView();
//			ImageView imageView = new ImageView(context);
//			imageView.setImageResource(R.drawable.ic_launcher);
			// toastView.addView(imageView, 0);
			break;
		case HT_FAILED:
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//			toast.setGravity(Gravity.BOTTOM, 0, 0);
//			LinearLayout fView = (LinearLayout) toast.getView();
//			ImageView fimageView = new ImageView(context);
//			fimageView.setImageResource(android.R.drawable.ic_dialog_alert);
			// fView.addView(fimageView, 0);
			break;

		default:
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			break;
		}
		
		if (processDialog!=null && ht == HintType.HT_LOADING) {
			if (context instanceof Activity) {
				Activity a = (Activity) context;
				if (!a.isFinishing()) {
					processDialog.show();
				}
			}
		}else if (toast!=null) {
			toast.show();
		}
	}
	
	public void dismissToast(){
		if (processDialog!=null && processDialog.isShowing()) {
			processDialog.dismiss();
		}
	}
	
	
	
}

