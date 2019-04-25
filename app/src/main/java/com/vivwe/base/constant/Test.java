package com.vivwe.base.constant;

import com.vivwe.base.cache.DataServiceCache;

/**
 * ahtor: super_link
 * date: 2019/4/23 09:44
 * remark:
 */
public class Test {

    public static void main(String[] args) {
        DataServiceCache.getInstance().save("13432767755", "username", "superlink");

        String usrename = DataServiceCache.getInstance().get("13432767755", "username");

        DataServiceCache.getInstance().remove("13432767755");
        DataServiceCache.getInstance().remove("13432767755", "username");
    }
}
