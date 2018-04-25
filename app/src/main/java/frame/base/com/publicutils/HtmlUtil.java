package frame.base.com.publicutils;

import android.text.TextUtils;

import java.util.List;

import frame.base.com.homepage.bean.HtmlImgBean;

/**
 * 字符串校验工具类 
 * @author jorge
 *
 */
public class HtmlUtil {

	private static String HTMLHEAD="<html>";
	private static String HTMLEND="</html>";


	private static String BODYHEAD="<body>";
	private static String BODYEND="</body>";

	private static String IMGHEAD="<img src=\"";
	private static String IMGEND="\"alt=\"图片占位符\">";





	/**
	 * 拼接html
	 * @param str
	 */
	public static String getHtmlStr(String str, List<HtmlImgBean> htmlImgBeanList) {
		String htmlStr = "";
		String strTwo="";
		if (TextUtils.isEmpty(str)) {
			return htmlStr;
		}
		if (htmlImgBeanList!=null&&htmlImgBeanList.size()>0) {
			for (int i=0;i<htmlImgBeanList.size();i++) {
				HtmlImgBean htmlImgBean=htmlImgBeanList.get(i);
				if (htmlImgBean!=null&&!TextUtils.isEmpty(htmlImgBean.getSrc())) {
					String imgStr=IMGHEAD+htmlImgBean.getSrc()+IMGEND;
					strTwo=str.replace(htmlImgBean.getRef(),imgStr);
				}
			}
		}
		htmlStr=HTMLHEAD+BODYHEAD+strTwo+BODYEND+HTMLEND;
		return htmlStr;
	}

}
