package frame.base.com.publicutils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来对字符串和对象的转换
 * @author LiuGuoyan
 */
public final class ObjectMaker {
	
	/** 把json串转换成相对应的类 */
	public static <T> T convert(String jsonStr, Class<T> classOfT) {
		if (jsonStr==null) return null ; 
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonStr);
		return gson.fromJson(element, classOfT);
	}
	
	public static <T> T convert(JSONObject jObject, Class<T> classOfT) {
		if (jObject==null) return null ;
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(jObject.toString());

			return gson.fromJson(element, classOfT);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> ArrayList<T> convert2List(JSONArray jsonArray, Class<T> classOfT){
		if (jsonArray==null) return null ; 
		ArrayList<T> result = new ArrayList<T>();
		for (int i = 0; i < jsonArray.length(); i++) {
			result.add(convert(jsonArray.optJSONObject(i), classOfT));
		}
		return result ; 
	}
	//新评论最后一条是总数 int型的。不取出来。
	public static <T> ArrayList<T> convert2Lists(JSONArray jsonArray, Class<T> classOfT){
		if (jsonArray==null) return null ;
		ArrayList<T> result = new ArrayList<T>();
		for (int i = 0; i < jsonArray.length()-1; i++) {
			result.add(convert(jsonArray.optJSONObject(i), classOfT));
		}
		return result ;
	}


	/** 把对象转换成json串 */
	public static String unConVer(Object obj){
		Gson gson = new Gson();
		return gson.toJson(obj);
	}


	/**
	 * json字符串转成对象
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}



	/**
	 * 解析为List对象
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<T> parseListData(String json, Class<T> clazz) throws JSONException {
		Gson gson = new Gson();
		return gson.fromJson(json, new Ptype<T>(clazz));
	}

	private static class Ptype<X> implements ParameterizedType {
		private Class<?> wrapped;

		public Ptype(Class<X> wrapped) {
			this.wrapped = wrapped;
		}

		public Type[] getActualTypeArguments() {
			return new Type[] {wrapped};
		}

		public Type getRawType() {
			return List.class;
		}

		public Type getOwnerType() {
			return null;
		}

	}

}
