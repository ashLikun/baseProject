package com.ashlikun.baseproject.mode.javabean.base;

import android.text.TextUtils;

import com.ashlikun.baseproject.core.MyApplication;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.wheelview3d.adapter.LoopViewData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/4.
 * 已区为对象保存一个区的数据到数据库
 */

public class CityDataXml implements Serializable {
    private String jsonData;

    public boolean isZhiXia = false;//是否是直辖市
    private int proId;
    private String proName;
    private int cityId;

    private String cityName;
    private int areaId;

    private String areaName;

    private int jichang;
    private String jichangName;


    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proId <= 0 ? "" : StringUtils.dataFilter(proName, "");
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityId <= 0 ? "" : StringUtils.dataFilter(cityName, "");
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaId <= 0 ? "" : StringUtils.dataFilter(areaName, "");
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getJichang() {
        return jichang;
    }

    public void setJichang(int jichang) {
        this.jichang = jichang;
    }

    public String getJichangName() {
        return jichang <= 0 ? "" : StringUtils.dataFilter(jichangName, "");
    }

    public void setJichangName(String jichangName) {
        this.jichangName = jichangName;
    }

    /**
     * 获取组合 地址
     */
    public String getData() {
        StringBuilder res = new StringBuilder();
        if (!TextUtils.isEmpty(proName)) {
            proName.replace("省", "");
            if (StringUtils.isEquals(cityName, proName) || TextUtils.isEmpty(cityName)) {
                res.append(proName + "市");
            } else {
                res.append(proName + "省");
            }
        }
        if (!TextUtils.isEmpty(cityName) && !StringUtils.isEquals(cityName, proName)) {
            res.append(cityName + "市");
        }
        if (areaId > 0 && !TextUtils.isEmpty(areaName)) {
            res.append(areaName + (areaName.contains("区") ? "" : (areaName.contains("县") ? "" : (areaName.contains("市") ? "" : "市"))));
        }
        return res.toString();
    }

    public void init() {
        getJsonData();
    }

    private String getJsonData() {
        if (TextUtils.isEmpty(jsonData)) {
            InputStream is = null;
            try {
                is = MyApplication.myApp.getAssets().open("address.json");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                jsonData = new String(buffer, "utf-8");
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
        }
        return jsonData;
    }

    /**
     * 获取省,先获取本地数据，没有就获取服务器的
     */

    public List getProvinceListData() {
        List<LoopViewData> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(getJsonData());
            JSONArray province = jsonObject.getJSONArray("Province");
            for (int i = 0; i < province.length(); i++) {
                JSONObject proJson = province.getJSONObject(i);
                LoopViewData proD = new LoopViewData();
                proD.setId(proJson.getInt("-id"));
                proD.setTitle(proJson.getString("-name"));
                list.add(proD);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取城市,先获取本地数据，没有就获取服务器的
     */
    public List getCityListData(int provinceId) {
        List<LoopViewData> list = new ArrayList<>();
        if (provinceId > 0) {
            try {
                JSONObject jsonObject = new JSONObject(getJsonData());
                JSONArray province = jsonObject.getJSONArray("Province");
                for (int i = 0; i < province.length(); i++) {
                    JSONObject proJson = province.getJSONObject(i);
                    if (proJson.getInt("-id") == provinceId) {
                        //x.getDb(DbConfig.getDbConfig()).saveOrUpdate(proD);
                        if (proJson.has("City")) {
                            Object temp = proJson.get("City");
                            JSONArray city = null;
                            if (temp instanceof JSONArray) {
                                city = (JSONArray) temp;
                            } else {
                                city = new JSONArray();
                                city.put(temp);
                            }
                            for (int j = 0; j < city.length(); j++) {
                                JSONObject cityJson = city.getJSONObject(j);
                                LoopViewData proD = new LoopViewData();
                                proD.setId(cityJson.getInt("-id"));
                                proD.setTitle(cityJson.getString("-name"));
                                list.add(proD);
                            }
                        }
                        break;

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        if (list.size() > 1 || list.size() == 0) {
//            list.add(0, new LoopViewData(-1, "不限"));
//        }
        return list;
    }

    /**
     * 获取县,先获取本地数据，没有就获取服务器的
     */
    public List getAreaListData(int cityId) {
        List<LoopViewData> list = new ArrayList<>();
        if (cityId > 0) {
            try {
                JSONObject jsonObject = new JSONObject(getJsonData());
                JSONArray province = jsonObject.getJSONArray("Province");
                for (int i = 0; i < province.length(); i++) {
                    JSONObject proJson = province.getJSONObject(i);
                    if (proJson.has("City")) {
                        Object temp = proJson.get("City");
                        JSONArray city = null;
                        if (temp instanceof JSONArray) {
                            city = (JSONArray) temp;
                        } else {
                            city = new JSONArray();
                            city.put(temp);
                        }
                        int j = 0;
                        for (; j < city.length(); j++) {
                            JSONObject cityJson = city.getJSONObject(j);
                            if (cityJson.getInt("-id") == cityId) {
                                if (cityJson.has("District")) {
                                    Object area = cityJson.get("District");
                                    JSONArray array = null;
                                    if (area instanceof JSONArray) {
                                        array = (JSONArray) area;
                                    } else {
                                        array = new JSONArray();
                                        array.put(area);
                                    }
                                    for (int k = 0; k < array.length(); k++) {
                                        JSONObject areaJson = array.getJSONObject(k);
                                        LoopViewData proA = new LoopViewData();
                                        proA.setId(areaJson.getInt("-id"));
                                        proA.setTitle(areaJson.getString("-name"));
                                        list.add(proA);
                                    }
                                }
                                break;
                            }
                        }
                        if (j != city.length()) {
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (list.size() > 1 || list.size() == 0) {
            list.add(0, new LoopViewData(-1, "不限"));
        }
        return list;
    }


    /**
     * 比较两个数据(省id与城市id)是否一致
     */
    public boolean isEquals(CityDataXml data) {
        if (data.getProId() == getProId() && data.getCityId() == getCityId()) {
            return true;
        }
        return false;
    }
}
