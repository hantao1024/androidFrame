package frame.base.com.homepage.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
import frame.base.com.publicevent.EventBusUtils;
import frame.base.com.publicutils.AppBeanUtils;
import frame.base.com.publicutils.HtmlUtil;
import frame.base.com.publicutils.URLData;


public class FourActivity extends StepActivity {
    WebView wb_content;

    private static final String TAG = "ThreeActivity";
    private String mBaseUrlTwo = "https://c.m.163.com/nc/article/DG3D5T4R0001899O/full.html";
    private String mBaseUrlOne = "https://c.m.163.com/nc/article/DG3BC6DK0001899O/full.html";
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
    }

    @Override
    protected void findViews() {
        setContentView(R.layout.activity_four);
//        showActionButton().setText("图片");
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


        wb_content.setWebChromeClient(new WebChromeClient());

        wb_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (htmlImgBeanList!=null&&htmlImgBeanList.size()>0) {
                    AppBeanUtils.startImagePagerActivity(getActivity(),0,true,htmlImgBeanList.get(0).getSrc(),htmlImgBeanList.get(1).getSrc());
                    return true;
                }


                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    protected void initData() {
        setTitle("第三页");
        setWebview(body);
//        HomePageManager.getInstance().doGetTwo(false, mBaseUrlTwo, null);
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
        setWebview(event.getMessage());

    }
    String body="{\"DG3D5T4R0001899O\":{\"template\":\"normal\",\"img\":[{\"ref\":\"<!--IMG#0-->\",\"src\":\"http://cms-bucket.nosdn.127.net/c09fb0d1fa324086a1644d518aa4b02120180423161024.jpeg\",\"alt\":\"\",\"pixel\":\"669*576\"}],\"sourceinfo\":{\"ename\":\"T1512041937142\",\"alias\":\"世界十大报纸之一《人民日报》建设的以新闻为主的大型网上信息发布平台\",\"tname\":\"人民网-人民日报\",\"topic_icons\":\"http://cms-bucket.nosdn.127.net/75703910cc2c4512b61128fbb689004c20171124195828.jpeg\",\"tid\":\"T1512041937142\"},\"searchKw\":[{\"weight\":\"3.698042\",\"keyword\":\"公安机关\",\"tag_source\":1},{\"weight\":\"2.347301\",\"keyword\":\"公文\",\"tag_source\":1},{\"weight\":\"1.476579\",\"keyword\":\"伪造文件\",\"tag_source\":1},{\"weight\":\"1.384328\",\"keyword\":\"国家机关\",\"tag_source\":1},{\"weight\":\"1.271154\",\"keyword\":\"陈志\",\"tag_source\":1},{\"weight\":\"2.105982\",\"keyword\":\"美国国务院\",\"tag_source\":1},{\"weight\":\"1.69268\",\"keyword\":\"在逃人员\",\"tag_source\":1}],\"topiclist_news\":[{\"ename\":\"pushliebiao\",\"hasCover\":false,\"tname\":\"要闻推送\",\"alias\":\"yaowentuisong\",\"subnum\":\"0\",\"tid\":\"T1350294190231\",\"cid\":\"C1350294152567\"},{\"ename\":\"yaowenspecial\",\"hasCover\":false,\"tname\":\"要闻\",\"alias\":\"yaowenspecial\",\"subnum\":\"0\",\"tid\":\"T1467284926140\",\"cid\":\"C1348647991705\"},{\"ename\":\"todayNews2\",\"hasCover\":false,\"tname\":\"今日要闻\",\"alias\":\"todayNews2\",\"subnum\":\"0\",\"tid\":\"T1429173762551\",\"cid\":\"C1350294152567\"},{\"ename\":\"androidpush\",\"hasCover\":false,\"tname\":\"push列表（android）\",\"alias\":\"androidpush\",\"subnum\":\"0\",\"tid\":\"T1371543208049\",\"cid\":\"C1350294152567\"},{\"ename\":\"newsToday1\",\"hasCover\":false,\"tname\":\"今日要闻\",\"alias\":\"newsToday1\",\"subnum\":\"0\",\"tid\":\"T1429173683626\",\"cid\":\"C1350294152567\"},{\"ename\":\"androidnews\",\"hasCover\":false,\"tname\":\"头条\",\"alias\":\"Top News\",\"subnum\":\"0\",\"tid\":\"T1348647909107\",\"cid\":\"C1348646712614\"},{\"ename\":\"iosnews\",\"hasCover\":false,\"tname\":\"头条\",\"alias\":\"Top News\",\"subnum\":\"11\",\"tid\":\"T1348647853363\",\"cid\":\"C1348646712614\"}],\"book\":[],\"link\":[],\"shareLink\":\"https://c.m.163.com/news/a/DG3D5T4R0001899O.html?spss=newsapp\",\"source\":\"人民网-人民日报\",\"video\":[{\"broadcast\":\"in\",\"sizeHD\":7261,\"url_mp4\":\"http://flv3.bn.netease.com/videolib3/1804/23/yAzlF2567/SD/yAzlF2567-mobile.mp4\",\"videoRatio\":1.78,\"alt\":\"重庆侦破郭文贵等人伪造国家机关公文案\",\"mp4Hd_url\":\"http://flv3.bn.netease.com/videolib3/1804/23/yAzlF2567/HD/yAzlF2567-mobile.mp4\",\"length\":103,\"videosource\":\"新媒体\",\"appurl\":\"\",\"m3u8Hd_url\":\"http://flv.bn.netease.com/videolib3/1804/23/yAzlF2567/HD/movie_index.m3u8\",\"mp4_url\":\"http://flv3.bn.netease.com/videolib3/1804/23/yAzlF2567/SD/yAzlF2567-mobile.mp4\",\"sizeSD\":4480,\"sid\":\"VC2LLLL34\",\"cover\":\"http://vimg.nosdn.127.net/snapshot/20180423/CSdM92567_4.jpg\",\"vid\":\"VDF685AID\",\"url_m3u8\":\"http://flv3.bn.netease.com/videolib3/1804/23/yAzlF2567/SD/yAzlF2567-mobile.mp4\",\"sizeSHD\":12514,\"ref\":\"<!--VIDEO#0-->\",\"topicid\":\"1000\",\"commentboard\":\"video_bbs\",\"size\":\"\",\"commentid\":\"DF685AID008535RB\",\"m3u8_url\":\"http://flv.bn.netease.com/videolib3/1804/23/yAzlF2567/SD/movie_index.m3u8\"}],\"threadVote\":467,\"title\":\"郭文贵等人伪造国家机关公文案被侦破\",\"body\":\"<p><!--VIDEO#0--><\\/p><!--IMG#0--><!--IMG#1--><p>　　重庆市公安局23日下午召开案件通报会，向中外记者通报重庆公安机关近期破获的郭文贵陈志煜等人伪造国家机关公文案相关情况。参与侦办该案的重庆市公安局有关负责人出席通报会。50余家中外媒体记者参加通报会。<\\/p><p>　　根据通报，<strong>2017年8月以来，潜逃美国的国际刑警组织红色通报在逃人员郭文贵为寻求政治庇护，编造大量虚假信息，进行所谓网上“爆料”，授意并指使犯罪嫌疑人陈志煜、陈志恒伪造30余份以中共中央、国务院以及国务院有关部委名义印发的国家机关公文，作为其“爆料”的主要内容，在境外公开散布传播，误导公众，造成恶劣影响。<\\/strong>公安部指定重庆公安机关立案侦查。2018年2月18日，重庆市公安局分别在广东、湖南将陈志煜、陈志恒抓获归案，依法扣押了相关涉案物品。<\\/p><p>　　据介绍，郭文贵，国际刑警组织红色通报在逃人员, 男，51岁，户籍地为北京市大兴区，同时拥有香港居民身份，河南裕达投资有限公司、北京盘古氏投资有限公司实际控制人。<\\/p><p>　　陈志煜、陈志恒为双胞胎兄弟，41岁，广东东莞人。陈志煜早年曾在广州市社会医疗保险服务管理局等单位工作并担任领导职务，2012年辞职后在加拿大生活；陈志恒2008年获得加拿大国籍，但长期在国内工作生活，现任广州某科技公司技术总监，负责软件开发工作。<\\/p><p>　　公安机关查明，2017年5月，陈志煜、陈志恒看到郭文贵公开悬赏征集中国政府所谓“秘密文件”等消息后，觉得有利可图，由陈志煜出面，化名“周国明”主动与郭联系。经多次试探接触，郭文贵认为陈志煜有很强的伪造文件能力，遂于2017年8月正式与其建立起合作关系。双方商定，郭文贵以每月4000美元的工资雇佣陈志煜，让陈志煜专职为其提供“爆料”所需材料，并为陈支付差旅费及购置手机等费用，郭文贵还承诺出资5000万美元建立基金供陈支配。此外，应郭文贵要求，陈志煜还四次到美国与郭文贵和其助手见面。<\\/p><p>　　按照郭文贵的授意和指使，2017年8月以来，陈志煜、陈志恒2人伪造了《国务院办公厅、中央国家安全委员会办公室关于2017年度秘密增派何建峰等27名国安部人民警察赴美值勤工作方案的批复》《中共中央办公厅关于调整针对特大犯罪嫌疑人郭文贵宣传工作策略的批复》《中共中央办公厅关于我国与朝鲜民主主义人民共和国就进一步深化解决该国核问题开展沟通协调工作的决定》《中共中央关于进一步加强司法力度打击以特大犯罪嫌疑人郭文贵为首的境内敌对势力的指示》《中共中央办公厅关于2017年度加强针对美国科学技术领域统战力度工作计划的批复》等30余份以中共中央、国务院和国安委、财政部、人社部等相关部委名义印发的国家机关公文，涉及“朝核问题”“统战工作”“境外情报”“科研项目”等所谓“绝密”“机密”内容，分批次提供给郭文贵。<\\/p><p>　　2017年10月起，郭文贵及美国媒体“华盛顿自由灯塔”多次对外公布所谓得到美国政府机构验证的中国政府“秘密文件”，如，《国务院办公厅、中央国家安全委员会办公室关于2017年度秘密增派何建峰等27名国安部人民警察赴美值勤工作方案的批复》《中共中央办公厅关于我国与朝鲜民主主义人民共和国就进一步深化解决该国核问题开展沟通协调工作的决定》《中共中央办公厅关于2017年度加强针对美国科学技术领域统战力度工作计划的批复》等，引起媒体高度关注，美国国务院也表示关注。经侦查鉴定，上述文件均系郭文贵、陈志煜、陈志恒三人伪造。<\\/p><p>　　公安机关侦查调查和讯问勘验发现，郭文贵和陈志煜、陈志恒在伪造国家机关公文过程中分工明确、手法专业。先由郭文贵提出文件涉及方向或主题，陈志煜利用其曾在国家机关工作过、熟悉文件起草和制作规范的经验，研究公文的行文规范和习惯，在互联网上大量搜索造假相关主题的资料、公文样式和最新信息、专业用语，编造所需的公文内容，再用公文制作软件，套用国家机关公文版式格式，编辑排版后加密传送给陈志恒。陈志恒利用掌握的计算机专业技术，将网上下载的文头、公章图谱进行编辑处理，套用于相应的伪造文件。伪造公文成型后，陈志恒将其打印拍照回传给陈志煜，由陈志煜提供给郭文贵。陈志煜、陈志恒从接到郭文贵指令到完成一份伪造文件，大约需要一个星期。<\\/p><p>　　到案后，陈志煜、陈志恒对受郭文贵指使伪造国家机关公文的犯罪事实供认不讳。<\\/p><p>　　公安机关侦查还发现，除受郭文贵指使造假外，为牟取经济利益，陈志煜、陈志恒从2013年起就开始伪造国家机关公文，并有偿提供给境外一些机构。到案后，公安机关在其电脑、移动硬盘里查获了大量伪造的国家机关公文，发文单位包括中共中央、国务院以及国安委、中宣部、中央编办、人社部、教育部、财政部等，涉及中国军事、国防、外交、统战、金融政策、经费预算等多个方面，甚至还有伪造的中纪委的办案案卷。<\\/p><p>　　重庆公安机关表示，目前，公安机关已以涉嫌伪造国家机关公文罪对陈志煜、陈志恒依法采取刑事强制措施。对郭文贵谎称假文件是经美国FBI等政府机构认证，以及发现的大量郭文贵向个别美国议员和前政府官员提供政治献金等情况，公安机关将通过执法合作渠道，与美方执法部门合作进行核查，相信美方执法部门也不会容忍这种犯罪行为的存在。<\\/p><p>　　郭文贵、陈志煜、陈志恒伪造国家机关公文的行为，严重危害国家安全。公安机关将坚决捍卫国家政治安全，坚决捍卫国家利益，坚决依法严厉打击伪造国家机关公文这种严重犯罪活动。<\\/p><p>　　侦查发现，<strong>郭文贵还伙同陈志煜、陈志恒等人编造了包括多位中央领导和省部级领导在境外有私生子、房产、情妇、巨额存款等虚假信息，以及涉及其他单位、企业和公民个人的虚假信息，情节严重、影响恶劣，已经涉嫌严重犯罪<\\/strong>，公安机关还在进一步侦办中，并将继续公布相关案情。<\\/p><!--SPINFO#0--><\\/p><p>原标题：郭文贵等人伪造国家机关公文案被侦破<\\/p>\",\"tid\":\"\",\"picnews\":true,\"spinfo\":[{\"ref\":\"<!--SPINFO#0-->\",\"spcontent\":\"<a target=\\\"_blank\\\" href=\\\"http://news.163.com/17/0710/03/COV2K6MU00018AOP.html\\\"><br/>郭文贵海航“爆料”真相调查<br/><\\/a><br/>2017年4月19日以来，被国际刑警组织在全球发出红色通报的北京盘古氏公司实际控制人、潜逃海外的犯罪嫌疑人郭文贵通过境外个别媒体和网络频频进行“爆料”，称其从国内高层获得所谓海航豪华公务机所有权等信息，并大肆编造种种离奇的“贪腐”“情色”故事，以博取眼球、吸引关注。<br/><a href=\\\"http://news.163.com/17/0714/23/CPBFCTVH0001875N.html\\\">新华社揭郭文贵\\\"爆料\\\":称来自高层实为向网友买料<\\/a><br/>2017年6月16日，郭文贵在境外媒体上展示中国东方资产管理公司、北京慧时恩投资有限公司等公司股份持有情况图，声称某领导的亲属控制着20万亿元的资产，他还声称这些信息是国内高层领导提供给他的。\",\"sptype\":\"背景\"}],\"advertiseType\":\"0001\",\"relative_sys\":[{\"docID\":\"DG44SHBL0519JFL1\",\"digest\":\"4月23日，处置非法集资部际联席会议在京召开“2018年防范和处置非法集资法律政策宣传座谈会”，该座谈会每年都会召开一次，通报过去一年我国在防范和处置非法集资方面的进展和下一步的工作重点。\",\"index\":1,\"from\":\"new\",\"href\":\"\",\"id\":\"DG44SHBL0519JFL1\",\"imgsrc\":\"http://dmr.nos.netease.com/images/804f416c7c5078cd72f52cbc8d82144c8d8.jpeg\",\"keyword\":\"集资诈骗 案件 金融犯罪 犯罪案件 诈骗罪 集资模式 严厉打击 非法吸收公众存款 大案 罪名 非法集资 犯罪分子 涉案金额 不法分子 违法违规 e租宝 吸收公众存款 要案 非法集资案 数额较大 集资诈骗罪\",\"recallBy\":\"poi\",\"title\":\"1800亿被骗难追回！老人农民最易受骗，这十大骗术你全都能识破吗\",\"type\":\"doc\",\"ptime\":\"2018-04-23 23:13:33\"},{\"docID\":\"DFOBG46404078CPA\",\"digest\":\"4月11日，新华网报道《差错百出、法人被“变更”一份“带病”判决书遭质疑》，安徽省高院近日作出《民事裁定书》，称之前作出的民事判决书存在笔误，应予补正。\",\"index\":2,\"from\":\"new\",\"href\":\"\",\"id\":\"DFOBG46404078CPA\",\"imgsrc\":\"http://cms-bucket.nosdn.127.net/a1c7db391c624adbb6277a3e104cced420180419090814.png\",\"keyword\":\"差错 裁定书 原告 案件 高级人民法院 合同纠纷 安徽高院 判决书 民事裁定书 权利 当事人 合同 被告 张涛 笔误 皖民 程序性 司法 纠纷 高院 本院\",\"recallBy\":\"poi\",\"title\":\"差错百出 安徽高院回应“带病”判决书\",\"type\":\"doc\",\"ptime\":\"2018-04-19 09:08:15\"},{\"docID\":\"D9CST9E10525S861\",\"digest\":\"极限运动是由多项成型运动项目以及游戏、生活和工作中的各种动作演变来，参与人群以年轻人为主的高难度观赏性体育运动。\",\"index\":3,\"from\":\"new\",\"href\":\"\",\"id\":\"D9CST9E10525S861\",\"imgsrc\":\"http://dmr.nosdn.127.net/v-20180418-dd70bf42d90b44ced58aaa1c795b70ab.jpg\",\"keyword\":\"爱好者 极限 穿越 运动项目 栈道 生理极限 骑手 山谷 竞技 山崖 骑摩托车 道路 摩托车 摩托车手 车手 体育运动 极限运动 山路 高难度 游戏 印度\",\"recallBy\":\"Category\",\"title\":\"印度摩托车手穿越死亡栈道，最窄处仅十几厘米宽\",\"type\":\"doc\",\"ptime\":\"2018-01-30 08:47:35\"}],\"articleType\":\"wemedia\",\"digest\":\"\",\"boboList\":[],\"ptime\":\"2018-04-23 16:09:16\",\"ec\":\"韩佳鹏_NN9841\",\"relative_res\":[{\"docID\":\"VWF851K6A\",\"index\":0,\"from\":\"new\",\"href\":\"\",\"id\":\"VWF851K6A\",\"imgsrc\":\"http://nos.netease.com/vimg/snapshot/20180424/HKRFC6457_cover.jpg\",\"keyword\":\"假币 克隆 警方 出租车 北京警方 克隆出租车 打掉 团伙 诈骗 诈骗团伙 北京\",\"recallBy\":\"poi-video\",\"title\":\"北京警方打掉克隆出租车假币诈骗团伙\",\"type\":\"video\",\"ptime\":\"2018-04-24 15:54:18\"}],\"docid\":\"DG3D5T4R0001899O\",\"threadAgainst\":1,\"hasNext\":false,\"dkeys\":\"郭文贵,重庆市公安局,陈志恒,公文,公安\",\"ydbaike\":[],\"replyCount\":4666,\"voicecomment\":\"off\",\"replyBoard\":\"news2_bbs\",\"votes\":[],\"topiclist\":[{\"ename\":\"guonei\",\"hasCover\":false,\"tname\":\"网易国内\",\"alias\":\"最快速最全面的国内政经资讯\",\"subnum\":\"964.7万\",\"tid\":\"T1348648101594\",\"cid\":\"C1378977941637\"}],\"category\":\"社会\"}}";
    private void setWebview(String message) {

        String htmlUrl="";
        if (!TextUtils.isEmpty(message)) {
            try {
                JSONObject jb = new JSONObject(message);
                JSONObject object=jb.optJSONObject("DG3D5T4R0001899O");//DG3BC6DK0001899O  DG3D5T4R0001899O
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


                HtmlImgBean htmlImgBean=new HtmlImgBean();
                htmlImgBean.setAlt("");
                htmlImgBean.setPixel("669*576");
                htmlImgBean.setRef("<!--IMG#1-->");
                htmlImgBean.setSrc("http://cms-bucket.nosdn.127.net/993383cb8a2f4feebce960ed3071689120180425154324.jpeg");


                htmlImgBeanList.add(htmlImgBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url=HtmlUtil.getHtmlStr(htmlUrl,htmlImgBeanList);
        if (!TextUtils.isEmpty(url)) {
            wb_content.loadDataWithBaseURL(null, url, "text/html", "utf-8",null);
        }
    }
}
