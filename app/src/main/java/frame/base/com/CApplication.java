package frame.base.com;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import frame.base.com.green.gen.DaoMaster;
import frame.base.com.green.gen.DaoSession;
import frame.base.com.publicdb.MyOpenHelper;
import frame.base.com.publicmanager.Manager;
import frame.base.com.publicutils.AppConfig;

/**
 * Created by hantao on 2018/2/7.
 */

public class CApplication extends Application {
    private static ExecutorService singleExecutor;
    private static CApplication instance;
    private static ExecutorService threadPool;

    List<Manager> registeredManagers=new ArrayList<Manager>();

    private static DaoSession daoSession;
    /**
     * 获取Application实例
     * @return
     */
    public static CApplication getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    public Handler getHandler() {
        return appHandler;
    }


    private Handler appHandler;

    public CApplication() {
        instance = this;
        appHandler = new Handler();
        threadPool = Executors.newCachedThreadPool();
        singleExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadAsyncTask();
        registerManager(getRegisterManager());
        setupDatabase();
        if (!AppConfig.RELEASE) {
            try {
                Stetho.initializeWithDefaults(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        refresh();
    }

    private void refresh() {
        //ClassicsHeader 和 ClassicsFooter 的描述文字是可以修改的
//        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.header_pulldown);//"下拉可以刷新";
//        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.header_refreshing);//"正在刷新...";
//        ClassicsHeader.REFRESH_HEADER_LOADING = getString(R.string.header_loading);//"正在加载...";
//        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.header_release);//"释放立即刷新";
//        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.header_finish);//"刷新完成";
//        ClassicsHeader.REFRESH_HEADER_FAILED = getString(R.string.header_failed);//"刷新失败";
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.header_lasttime);//"上次更新 M-d HH:mm";
//        ClassicsHeader.REFRESH_HEADER_LASTTIME = getString(R.string.header_lasttime);//"'Last update' M-d HH:mm"
//
//        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.footer_pullup);//"上拉加载更多";
//        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.footer_release);//"释放立即加载";
//        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.footer_refreshing);//"正在刷新...";
//        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.footer_loading);//"正在加载...";
//        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.footer_finish);//"加载完成";
//        ClassicsFooter.REFRESH_FOOTER_FAILED = getString(R.string.footer_failed);//"加载失败";
//        ClassicsFooter.REFRESH_FOOTER_ALLLOADED = getString(R.string.footer_allloaded);//"全部加载完成";
    }

    protected int getRegisterManager() {
        return R.array.managers;
    }


    /**
     * 这里使用一个空的AsyncTask，确保第一个AsyncTask在UI线程中执行，避免出现：NoClassDefFoundError android/os/AsyncTask
     *
     */
    private void loadAsyncTask() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
        }.execute();
    }
    /**
     * 注册manager，启动的时候注册到事件总线
     */
    private void registerManager(int managers) {
        TypedArray managerClasses = getResources().obtainTypedArray(managers);
        for (int index = 0; index < managerClasses.length(); index++){
            try {
                Manager manager=(Manager) Class.forName(managerClasses.getString(index)).newInstance();
                registeredManagers.add(manager);
                manager.onAppStart();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        managerClasses.recycle();
    }

    /**
     * 在UI线程中执行
     * @param runnable
     */
    public void runOnUiTread(Runnable runnable) {
        appHandler.post(runnable);
    }

    /**
     * 在UI线程中执行
     * @param runnable
     * @param delayMillis
     */
    public void runOnUiTread(Runnable runnable,long delayMillis) {
        appHandler.postDelayed(runnable,delayMillis);
    }

    /**
     * 获取线程池(创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。)
     * @return
     */
    public static ExecutorService getThreadPool() {
        if(threadPool.isTerminated() || threadPool.isShutdown()){
            threadPool=Executors.newCachedThreadPool();
        }
        return threadPool;
    }

    /**
     * 获取线程池(创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行)
     * @return
     */
    public static ExecutorService getSingleExecutor() {
        if(singleExecutor.isTerminated() || singleExecutor.isShutdown()){
            singleExecutor= Executors.newSingleThreadExecutor();
        }
        return singleExecutor;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "yqzj.db", null);
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
