package frame.base.com.homepage.dao;

import java.util.List;

import frame.base.com.CApplication;
import frame.base.com.homepage.bean.User;

/**
 * Created by handsome on 2016/4/19.
 */
public class UserNameDao {

    /**
     * 添加数据
     *
     * @param user
     */
    public static void insertUser(User user) {
        CApplication.getDaoInstant().getUserDao().insert(user);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteUser(long id) {
        CApplication.getDaoInstant().getUserDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param user
     */
    public static void updateUser(User user) {
        CApplication.getDaoInstant().getUserDao().update(user);
    }

    /**
     * 查询所有的数据
     *
     * @return
     */
    public static List<User> queryUser() {
        return CApplication.getDaoInstant().getUserDao().queryBuilder().list();
    }

//    /**
//     * 查询一条数据
//     *
//     * @return
//     */
//    public static User queryUser(User user) {
//        return CApplication.getDaoInstant().getUserDao().queryBuilder().where().unique();
//    }
}
