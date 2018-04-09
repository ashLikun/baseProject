package com.ashlikun.baseproject.view.other.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.adapter.databind.recycleview.CommonBindAdapter;
import com.ashlikun.core.factory.Presenter;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.core.base.view.BaseListActivity;
import com.ashlikun.baseproject.databinding.AaaBinding;
import com.ashlikun.baseproject.presenter.login.LoginPresenter;

import static com.ashlikun.libarouter.constant.ARouterPath.TEST;


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = TEST)
@Presenter(LoginPresenter.class)
public class TestActivity extends BaseListActivity<LoginPresenter, AaaBinding, String> {


    @Override
    public void initView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(0xff0000);
        tv.setText("我是头部");
        listSwipeView.getRecyclerView().addHeaderView(tv);

        for (int i = 0; i < 20; i++) {
        }
        //listSwipeView.getRecyclerView().addItemDecoration();
    }

//    @Override
//    public RecyclerView.ItemDecoration getItemDecoration() {
//        return new StickyHeadersBuilder()
//                .setAdapter(adapter)
//                .setDrawOrder(DrawOrder.UnderItems)
//                .setRecyclerView(listSwipeView.getRecyclerView())
//                .setStickyHeadersAdapter(new CommonHeaderAdapter<String>(this, R.layout.item_header_test, listDatas) {
//                    @Override
//                    public void convert(ViewHolder holder, String o) {
//                    }
//                }, false)
//                .setHeaderSize(1)
//                .setFooterSize(1)
//                .build();
//    }

//    @Override
//    public CommonBindAdapter getAdapter() {
//        return new CommonBindAdapter<String, ItemTestBinding>(this, R.layout.item_test, listDatas) {
//            @Override
//            public void convert(DataBindHolder<ItemTestBinding> holder, String s) {
//                holder.dataBind.setItemData(s);
//            }
//        };
//    }


    @Override
    public void onLoadding() {
        listSwipeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    adapter.notifyDataSetChanged();
                    listSwipeView.getStatusChangListener().complete();

                }
            }
        }, 6000);

    }

    @Override
    public void onRefresh() {
        listSwipeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                }
                adapter.notifyDataSetChanged();
                listSwipeView.refreshLayout.setRefreshing(false);
            }
        }, 4000);
    }


    @Override
    public int getLayoutId() {
        return R.layout.aaa;
    }

    @Override
    public void parseIntent(Intent intent) {

    }

    @Override
    public void onItemClick(ViewGroup parent, View view, String data, int position) {

    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    @Override
    public CommonBindAdapter getAdapter() {
        return null;
    }

    @Override
    public void onRetryClick(ContextData data) {

    }

    @Override
    public void onEmptyClick(ContextData data) {

    }
}
