package main.vvwe.com.lifestory.base.cache;

import android.content.Context;
import com.mbs.sdk.db.DBServer;
import main.vvwe.com.lifestory.base.app.MyApplication;

/**
 * ahtor: super_link
 * date: 2019/4/22 15:37
 * remark:
 */
public class DataServiceCache extends DBServer {

    private static final String DB_NAME = "life_story";
    private static final int VERSION_CODE = 1;

    private static DataServiceCache cacheDataService;
    /**
     * 初始化
     *
     * @param context     上下文
     */
    public DataServiceCache(Context context) {
        super(context, DB_NAME, VERSION_CODE);
    }

    @Override
    public Class[] createTable() {

        // 在这里你可以创建表，返回类类型信息即可创建！

        return null;
    }

    @Override
    public Class[] dropTable() {

        // 在这里你可以删除表，返回类类型信息即可删除！

        return null;
    }

    /**
     * 获取实例对象
     * @return
     */
    public static DataServiceCache getInstance(){

        if(cacheDataService == null){
            cacheDataService = new DataServiceCache(MyApplication.getContext());
        }

        return cacheDataService;
    }
}
