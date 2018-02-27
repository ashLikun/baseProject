package com.ashlikun.baseproject.core;

import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.orm.db.assit.QueryBuilder;
import com.ashlikun.baseproject.mode.javabean.CityData;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/17　19:11
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：项目内存缓存的全局数据
 */

public class CacheData {

    public static CityData getCityData() {
        try {
            return LiteOrmUtil.get().query(QueryBuilder.create(CityData.class).where("isLogin=?", true).limit(0, 1)).get(0);
        } catch (IndexOutOfBoundsException e) {
        }
        return null;
    }



}
