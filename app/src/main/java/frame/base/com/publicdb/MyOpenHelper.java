package frame.base.com.publicdb;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import frame.base.com.green.gen.DaoMaster;
import frame.base.com.green.gen.DaoSession;
import frame.base.com.green.gen.UserDao;

/**
 * Created by hantao on 2018/2/22.
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    public static final String DBNAME = "yqzj.db";

    public MyOpenHelper(Context context){
        super(context,DBNAME,null);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if (oldVersion < newVersion) {
            MigrationHelper.getInstance().migrate(db, UserDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserNameDao.class,XXDao.class);
        }
    }
    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DBNAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
