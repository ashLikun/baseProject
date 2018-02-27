package com.ashlikun.baseproject.utils;

import android.widget.Filter;

import com.ashlikun.baseproject.mode.javabean.CityData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/27 0027　1:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class CityDataFilter extends Filter {
    ArrayList<CityData> datas;
    OnSearchListener listener;

    public CityDataFilter(ArrayList<CityData> datas, OnSearchListener listener) {
        this.datas = datas;
        this.listener = listener;
    }

    public interface OnSearchListener {
        void onSearchDatas(ArrayList<CityData> datas);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        ArrayList<CityData> mFilteredArrayList = new ArrayList<>();
        if (datas != null) {
            for (Iterator<CityData> iterator = datas.iterator(); iterator.hasNext(); ) {
                CityData data = iterator.next();
                if (contains(data.getCityName(), (String) constraint) ||
                        contains(data.getCompleteSpelling(), (String) constraint) ||
                        contains(data.getAllInitial(), (String) constraint)) {
                    mFilteredArrayList.add(data);
                }
            }
        }
        filterResults.values = mFilteredArrayList;
        return filterResults;
    }

    private boolean contains(String str1, String str2) {
        return str1 != null && str2 != null && str1.toUpperCase().contains(str2.toUpperCase());
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (listener != null) {
            listener.onSearchDatas((ArrayList<CityData>) results.values);
        }
    }
}
