package frame.base.com.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import frame.base.com.R;
import frame.base.com.base.StepActivity;
import frame.base.com.homepage.bean.HtmlImgBean;
import frame.base.com.homepage.events.HomePageEvents;
import frame.base.com.homepage.manager.HomePageManager;
import frame.base.com.publicevent.EventBusUtils;
import frame.base.com.publicutils.HtmlUtil;
import frame.base.com.publicutils.URLData;


public class FourActivity extends StepActivity {
    WebView wb_content;

    private static final String TAG = "ThreeActivity";
    private String mBaseUrl = "https://www.baidu.com";
    private String mBaseUrlTwo = "https://c.m.163.com/nc/article/DG3D5T4R0001899O/full.html";
//    private String mUrl = "http://cms-bucket.nosdn.127.net/c09fb0d1fa324086a1644d518aa4b02120180423161024.jpeg";
    RefreshLayout refreshLayout;
    private List<HtmlImgBean> htmlImgBeanList= new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);

    }

    @Override
    protected void createContent() {

    }

    @Override
    public void onAction(View v) {
        super.onAction(v);
        Intent intent=new Intent(FourActivity.this,FourActivity.class);
        startActivity(intent);
    }

    @Override
    protected void findViews() {
        setContentView(R.layout.activity_four);
        wb_content = generateFindViewById(R.id.wb_content);
        refreshLayout = generateFindViewById(R.id.refreshLayout);


        //禁止上拉加载：
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);

//        //使上拉加载具有弹性效果：或者不自动加载
        refreshLayout.setEnableAutoLoadMore(false);
//        //禁止越界拖动：
        refreshLayout.setEnableOverScrollDrag(false);


        WebSettings webSettings = wb_content.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持javascript
//        LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
//        LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
//        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
//        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//      屏幕自适应
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
//        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
    }

    @Override
    protected void initData() {
        setTitle("第三页");
        HomePageManager.getInstance().doGetTwo(false, mBaseUrlTwo, null);
    }
    @Override
    protected void setListener() {
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                HomePageManager.getInstance().doGetTwo(false, mBaseUrlTwo, null);
////                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//            }
//        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                HomePageManager.getInstance().doGetTwo(true, mBaseUrlTwo, null);
////                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
//            }
//        });
    }


    @Override
    public void free() {
        EventBusUtils.unregister(this);
    }

    @Override
    protected void onHandleMessage(Message msg) {
    }

    /**
     * 接受传递过来的数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomePageEvents event) {
        if (event.isLoadmore()) {
            refreshLayout.finishLoadmore();//传入false表示加载失败
        } else {
            refreshLayout.finishRefresh();//传入false表示刷新失败
        }
        String htmlUrl="";
        if (event.getMessage()!=null) {

            try {

                JSONObject jb = new JSONObject(event.getMessage());

                JSONObject object=jb.optJSONObject("DG3D5T4R0001899O");
                htmlUrl=object.optString(URLData.Key.BODY);
                JSONArray imgs=object.optJSONArray(URLData.Key.IMG);
                for (int i = 0; i < imgs.length(); i++) {
                    JSONObject jsonObject = imgs.getJSONObject(i);
                    HtmlImgBean htmlImgBean=new HtmlImgBean();
                    htmlImgBean.setAlt(jsonObject.optString("alt"));
                    htmlImgBean.setPixel(jsonObject.optString("pixel"));
                    htmlImgBean.setRef(jsonObject.optString("ref"));
                    htmlImgBean.setSrc(jsonObject.optString("src"));
                    htmlImgBeanList.add(htmlImgBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        wb_content.loadDataWithBaseURL(null, HtmlUtil.getHtmlStr(htmlUrl,htmlImgBeanList), "text/html", "utf-8",null);

    }
}
