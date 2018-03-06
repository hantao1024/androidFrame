package frame.base.com.homepage.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

import frame.base.com.R;
import frame.base.com.base.StepActivity;
import frame.base.com.homepage.adapter.ShopListAdapter;
import frame.base.com.homepage.bean.User;
import frame.base.com.homepage.dao.UserNameDao;
import frame.base.com.homepage.events.HomePageEvents;
import frame.base.com.homepage.manager.HomePageManager;
import frame.base.com.publicevent.EventBusUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;


public class ThreeActivity extends StepActivity {
    ListView lv_content;

    private static final String TAG = "ThreeActivity";
    private String mBaseUrl = "https://api.base.com/dynamic/comicversion/android";
    ShopListAdapter shopListAdapter;
    RefreshLayout refreshLayout;
    private List<User> userList = new LinkedList<>();
    NavigationController navigationController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);

    }

    @Override
    protected void createContent() {

    }

    @Override
    protected void findViews() {
        setContentView(R.layout.activity_three);
        lv_content = generateFindViewById(R.id.lv_content);
        refreshLayout = generateFindViewById(R.id.refreshLayout);


        PageNavigationView tab = generateFindViewById(R.id.main_tab);
        navigationController = tab.material()
                .addItem(android.R.drawable.ic_menu_compass, "位置")
                .addItem(android.R.drawable.ic_menu_search, "搜索")
                .addItem(android.R.drawable.ic_menu_help, "帮助")
                .build();

    }

    @Override
    protected void initData() {
        setTitle("第三页");
        //设置 Header 为 BezierRadar 样式
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        userList = UserNameDao.queryUser();
        shopListAdapter = new ShopListAdapter(ThreeActivity.this, userList);
        shopListAdapter.setItemListner(itemListner);
        lv_content.setAdapter(shopListAdapter);

    }

    @Override
    protected void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                HomePageManager.getInstance().doGet(false, mBaseUrl, null);
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                HomePageManager.getInstance().doGet(true, mBaseUrl, null);
//                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });

//        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
//            @Override
//            public void onSelected(int index, int old) {
//                //选中时触发
//            }
//
//            @Override
//            public void onRepeat(int index) {
//                //重复选中时触发
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
        List<User> list = UserNameDao.queryUser();
        shopListAdapter.reLoad(list);
        shopListAdapter.notifyDataSetChanged();

    }

    private ShopListAdapter.ItemListner itemListner = new ShopListAdapter.ItemListner() {
        @Override
        public void Click(User user) {
            UserNameDao.updateUser(user);
            List<User> list = UserNameDao.queryUser();
            shopListAdapter.reLoad(list);
            shopListAdapter.notifyDataSetChanged();
        }

        @Override
        public void Delete(User user) {
            UserNameDao.deleteUser(user.getId());
            List<User> list = UserNameDao.queryUser();
            shopListAdapter.reLoad(list);
            shopListAdapter.notifyDataSetChanged();
        }
    };
}
