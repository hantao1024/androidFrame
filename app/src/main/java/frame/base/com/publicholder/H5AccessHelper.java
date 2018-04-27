package frame.base.com.publicholder;

import android.app.Activity;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import frame.base.com.publicutils.AppBeanUtils;

/**
 * h5界面访问工具集
 * @author liuguoyan
 */
public final class H5AccessHelper {
	
	public static final String APP_SCEHMA = "dmzjandroid" ;
	
	public static final String IMG_SCEHMA = "dmzjimage" ;
	
	/** 漫画介绍页 **/
	//dmzjandroid://www.dmzj.com/cartoon_description?id={漫画id}&title={漫画标题}
	public static final String CARTOON_DESCRIPTION = "/cartoon_description" ;
	
	/** 小说介绍页 **/
	//dmzjandroid://www.dmzj.com/novel_description?id={小说id}&title={小说标题}
	public static final String NOVEL_DESCRIPTION = "/novel_description" ;
	/** 新闻介绍页 **/
	public static final String NOVEL_NEWS = "/news_description" ;
	
	/**
	 * @param url
	 * @return boolean 当true时,说明是需要访问app里的某个组件,不再由系统默认处理
	 */
	public static boolean accessAppComponet(String url , Activity activity ){
		boolean app_access = false; 
		//dmzjandroid://www.dmzj.com/novel_description?id=404&title=Sword Art Online%E5%88%80%E5%89%91%E7%A5%9E%E5%9F%9F
		
		url = url.contains(" ") ? url.replace(" ", "") : url ; 
		
		String decodeurl = null ;
		try {
			decodeurl = URLDecoder.decode(url, "UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (Exception e){
//			Answers.getInstance().logCustom(new CustomEvent("H5AccessHelper")
//					.putCustomAttribute("accessAppComponet", url));
		}
		
		URI uri = null ;
		try {
			uri = URI.create(decodeurl) ;
		} catch (Exception e) {
			return true ;
		}

		if (decodeurl == null || uri == null) return true;
		if (uri.getScheme().equals(APP_SCEHMA)) {
			Map<String, String> params = getPathParmas(uri.getQuery()) ;
			if (params != null) {
				//漫画介绍页
				if (uri.getPath().equals(CARTOON_DESCRIPTION)) {
				}
				//小说介绍页
				else if (uri.getPath().equals(NOVEL_DESCRIPTION)) {
				}
				//新闻介绍页
				else if (uri.getPath().equals(NOVEL_NEWS)) {
				}
				app_access = true;
			} else {
				app_access=false;
			}
		}
		
		else if (uri.getScheme().equals(IMG_SCEHMA)) {
			Map<String, String> params = getPathParmas(uri.getQuery()) ;
			if (params != null) {
				String src = params.get("src");
				AppBeanUtils.startImagePagerActivity(activity,0, true, src);
				app_access = true;
			} else {
				app_access=false;
			}
		}
		
		return app_access ; 
	}
	
	
	/**
	 * 得到url中的参数列表
	 * @param queryStr
	 * @return
	 */
	public static Map<String, String> getPathParmas(String queryStr){
		Map<String, String> para = new HashMap<String, String>();
		try {
			if (queryStr == null || queryStr.equals("")) {
                para=null;
            } else {
                String params[] = queryStr.split("&");
                for (int i = 0; i < params.length; i++) {
                    String onp[] = params[i].split("=") ;
                    para.put(onp[0], onp[1]) ;
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			para=null;
		}
		return para ;
	}
	
}
