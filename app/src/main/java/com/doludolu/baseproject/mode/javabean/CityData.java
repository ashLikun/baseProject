package com.doludolu.baseproject.mode.javabean;

import com.ashlikun.okhttputils.json.GsonHelper;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.orm.db.annotation.Column;
import com.ashlikun.orm.db.annotation.PrimaryKey;
import com.ashlikun.orm.db.annotation.Table;
import com.ashlikun.orm.db.assit.WhereBuilder;
import com.ashlikun.orm.db.enums.AssignType;
import com.ashlikun.orm.db.model.ColumnsValue;
import com.ashlikun.orm.db.model.ConflictAlgorithm;
import com.doludolu.baseproject.code.MyApplication;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.hbung.charbar.CharComparator;
import com.hbung.charbar.HanziToPinyin;
import com.hbung.charbar.TargetField;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2016/8/27.
 */
@Table("CityData")
public class CityData implements Serializable {
    public static final int LOCATION_DEFAULT = -100;//未开始定位
    public static final int LOCATION_ERROR = -99;//定位失败
    public static final int LOCATION_NO_EXIST = -98;//定位在当前app服务器不存在
    @Column("isLogin")
    private boolean isLogin = false;
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("CityID")
    @SerializedName("id")
    private int cityID;
    /**
     * 城市名字
     */
    @Column("CityName")
    @SerializedName("name")
    private String cityName;

    /**
     * 增加首字母
     */
    @Column("allInitial")
    @TargetField
    private transient String allInitial;
    //全部拼音
    @Column("completeSpelling")
    private transient String completeSpelling;


    public CityData(int cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.allInitial = cityName;
    }

    public CityData() {
    }

    public void setAllInitial() {
        this.allInitial = HanziToPinyin.getPinYinInitial(cityName);
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
        this.allInitial = HanziToPinyin.getPinYinInitial(cityName);
        this.completeSpelling = HanziToPinyin.getPinYin(cityName);
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }


    //获取首字母
    public String getInitial() {
        if (allInitial == null) {
            this.allInitial = HanziToPinyin.getPinYinInitial(cityName);
        }
        return (allInitial == null || allInitial.length() == 0) ? "#" : allInitial.substring(0, 1).toUpperCase();
    }


    public String getAllInitial() {
        if (allInitial == null) {
            this.allInitial = HanziToPinyin.getPinYinInitial(cityName);
        }
        return allInitial;
    }

    public String getCompleteSpelling() {
        if (completeSpelling == null) {
            this.completeSpelling = HanziToPinyin.getPinYin(cityName);
        }
        return completeSpelling;
    }

    public void setCompleteSpelling(String completeSpelling) {
        this.completeSpelling = completeSpelling;
    }

    /**
     * 列表排序
     *
     * @param list
     */
    public static void sortList(List<CityData> list) {
        Collections.sort(list, new CharComparator());
    }

    /**
     * 保存到数据库
     */
    public void save() {
        //清楚上一次
        LiteOrmUtil.get().update(WhereBuilder.create(CityData.class).where("isLogin=?", true), new ColumnsValue(new String[]{"isLogin"},
                new Boolean[]{false}), ConflictAlgorithm.None);
        isLogin = true;
        LiteOrmUtil.get().save(this);
    }

    public int getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }


    private static String getJsonData() {
        InputStream is = null;
        try {
            is = MyApplication.myApp.getAssets().open("address.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static List<CityData> getCityDatas() {
        return GsonHelper.getGson().fromJson(getJsonData(), TypeToken.getParameterized(List.class, CityData.class).getType());
    }
}
