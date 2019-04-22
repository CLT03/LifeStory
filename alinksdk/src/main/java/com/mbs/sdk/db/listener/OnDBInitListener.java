package com.mbs.sdk.db.listener;

import android.database.sqlite.SQLiteDatabase;

/**
 * ahtor: super_link
 * date: 2019/4/22 15:53
 * remark:
 */
public interface OnDBInitListener {
    /**
     * 当数据库对象被实例化时，会调用该方法获得类类型信息，并创建需要的表。
     * 创建表的名称会按照类名的小写+前缀“table"进行命名。
     * 用户只要在继承了DBServer中的构造方法中传入此实现类，即可完成表结构创建。
     * @return 类类型数组
     */
    public Class[] createTable();

    /**
     * 当数据库版本号发生变更时，会调用该方法获得类类型信息，并删除该对应的表。
     * 用户只要在继承了DBServer中的构造方法中传入此实现类，即可完成表的删除。
     * @return 类类型数组
     */
    public Class[] dropTable();
}
