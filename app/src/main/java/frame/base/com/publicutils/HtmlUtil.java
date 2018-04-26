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

	private static String HEAD="<head>\n" +
			"  </head>";

	private static String BODYHEAD="<body>";
	private static String BODYEND="</body>";

	private static String IMGHEAD="<img src=\"";
	private static String IMGEND="\"alt=\"图片占位符\">";


	private static String SCRIPTHEAD="<script>";
	private static String SCRIPTEND="</script>";

	private static String IMGBODYONE="<img onload=\"this.onclick = function() {  \n" +
			"        window.location.href = 'sx://github.com/dsxNiubility?src=' +this.src+'&top=' + this.getBoundingClientRect().top + '&whscale=' + this.clientWidth/this.clientHeight ;\n" +
			"    };\" ";


	private static String IMGBODYTWO= "    width=\"%d\" \n";
	private static String IMGBODYTHREE= "    height=\"%d\" \n";


	private static String IMGBODYFOUR= "    src=\"%s\">";


	//  %d  整数类型（十进制）
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
		if (htmlImgBeanList != null && htmlImgBeanList.size() > 0) {
			for (int i = 0; i < htmlImgBeanList.size(); i++) {
				HtmlImgBean htmlImgBean = htmlImgBeanList.get(i);
				if (htmlImgBean != null && !TextUtils.isEmpty(htmlImgBean.getSrc())) {

					String strOne=TextUtils.isEmpty(strTwo)?str:strTwo;
					String imgStr1 = String.format(IMGBODYTWO,669);
					String imgStr2 = String.format(IMGBODYTHREE,576);
					String imgStr3 = String.format(IMGBODYFOUR,htmlImgBean.getSrc());

					String imgStr=IMGBODYONE+imgStr1+imgStr2+imgStr3;
					strTwo = strOne.replace(htmlImgBean.getRef(), imgStr);
				}
			}
		} else {
			strTwo=str;
		}
		htmlStr=HTMLHEAD+HEAD+BODYHEAD+strTwo+BODYEND+HTMLEND;
		return htmlStr;
	}

}
