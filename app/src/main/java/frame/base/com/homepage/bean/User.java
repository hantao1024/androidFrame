package frame.base.com.homepage.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hantao on 2018/2/9.
 *
 *
 * 1.实体@Entity注解

 schema：告知GreenDao当前实体属于哪个schema
 active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
 nameInDb：在数据中使用的别名，默认使用的是实体的类名
 indexes：定义索引，可以跨越多个列
 createInDb：标记创建数据库表
 2.基础属性注解

 @Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
 @Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
 @NotNul：设置数据库表当前列不能为空
 @Transient ：添加次标记之后不会生成数据库表的列
 3.索引注解

 @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
 @Unique：向数据库列添加了一个唯一的约束
 4.关系注解

 @ToOne：定义与另一个实体（一个实体对象）的关系
 @ToMany：定义与多个实体对象的关系
 编译项目，User实体类会自动编译，生成get、set方法并且会在com.greendao.gen目录下生成三个文件，DaoMaster 、DaoSession、Dao类；
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;//主键  自增长
    private String name;
    private String url;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 177842330)
    public User(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
