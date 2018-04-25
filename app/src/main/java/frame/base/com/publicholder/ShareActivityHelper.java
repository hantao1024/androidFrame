package frame.base.com.publicholder;

/**
 * 应用分享统一入口
 * @author liuguoyan
 */
public class ShareActivityHelper {

//	/**
//	 * @param activity
//	 * @param img_url   分享图片的url
//	 * @param content_url  分享内容的url
//	 * @param share_text   分享话术
//	 */
//	public static void share(Activity activity , String title , String img_url , String content_url, String share_text){
//		share(activity, title, img_url, content_url, share_text, null) ;
//	}
//
//	/**
//	 * @param activity
//	 * @param img_url   分享图片的url
//	 * @param content_url  分享内容的url
//	 * @param share_text   分享话术
//	 */
//	public static void share(Activity activity , String title , String img_url , String content_url, String share_text, String name){
//		share(activity, title, img_url, content_url, share_text, null,name) ;
//	}
//	public static void share(Activity activity , String title , String img_url , String content_url, String share_text , Bundle extra, String name){
//		Intent shareIntent=new Intent(activity,ShareActivity.class);
//		shareIntent.putExtra(ShareActivity.INTENT_EXTRA_TEXT, share_text) ;
//		shareIntent.putExtra(ShareActivity.INTENT_EXTRA_TITLE, title) ;
//		shareIntent.putExtra(ShareActivity.INTENT_EXTRA_IMGURL, img_url) ;
//		shareIntent.putExtra(ShareActivity.INTENT_EXTRA_URL, content_url) ;
//		shareIntent.putExtra(ShareActivity.INTENT_EXTRA_NAME, name) ;
//		shareIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) ;
//
//		if (extra != null) {
//			Set<String> keyset = extra.keySet() ;
//			Iterator<String> interator = keyset.iterator() ;
//			while (interator.hasNext()) {
//				String string = (String) interator.next();
//				shareIntent.putExtra(string, extra.getString(string)) ;
//			}
//		}
//
//		activity.startActivity(shareIntent);
//	}

}
