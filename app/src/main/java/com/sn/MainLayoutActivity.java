package com.sn;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sn.Config.Config;
import com.sn.loadmore.RecyclerViewLoadMore;

import java.util.ArrayList;
import java.util.List;

/**
 * try to moving for MVP
 */

public class MainLayoutActivity extends BaseActivity {

    SwipeRefreshLayout swipeRefreshLayout_mla;
    RecyclerViewLoadMore recyclerView_mla;
    RVAdapter mRVAdapter=null;

    private List<Model>list=null;
    private List<Model>listTem=null;



    private int page_=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.main_layout);
        context=MainLayoutActivity.this;
//        toastMes(context,"who?");
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.sendEmptyMessage(1);
    }

    private void init(){

        swipeRefreshLayout_mla= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_mla);


        recyclerView_mla= (RecyclerViewLoadMore) findViewById(R.id.recyclerView_mla);
        /**
         *recyclerview init
         */
        recyclerView_mla.initParams(context,new LinearLayoutManager(context));


        swipeRefreshLayout_mla.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout_mla.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout_mla.setRefreshing(false);
                    }
                },2000);
                page_=1;
                handler.sendEmptyMessage(1);

            }
        });
        recyclerView_mla.setLoadmoreListener(new RecyclerViewLoadMore.LoadmoreListener() {
            @Override
            public void loadmore(int page, List<Model> list) {
                page_=page;
                page_++;
                handler.sendEmptyMessage(2);

            }

            @Override
            public void finishLoadmore(int page, List<Model> list) {
            }

            @Override
            public void isTop(boolean isTop) {

            }

            @Override
            public void isBottom(boolean isBottom) {

            }

            @Override
            public void scrollItem(int itemPosition) {

            }

            @Override
            public void scrollItemLast(int lastPostion) {

            }
        });


    }

    private void refreshData(){
        if(list==null){
            list=new ArrayList<Model>();
        }else{
            list.clear();
        }
        Model mModel=null;
        for (int i=0;i<30;i++){
            mModel=new Model();
            mModel.setContent_("page "+page_+" n:"+i);
            mModel.setType_(Config.TYPE_VIEWHOLDER_NORMAL);
            mModel.setCodeViewType(Config.CODE_VIEWTYPE_NORMAL);
            list.add(mModel);
            mModel=null;
        }
    }
    private void loadmore(int page_){


            if(listTem==null){
                listTem=new ArrayList<Model>();
            }else{
                listTem.clear();
            }

        int normal_=page_+20;
        Model mModel=null;
        for (int i=0;i<30;i++){
            mModel=new Model();
            mModel.setContent_("page "+page_+" n:"+i);
            mModel.setType_(Config.TYPE_VIEWHOLDER_NORMAL);
            mModel.setCodeViewType(Config.CODE_VIEWTYPE_NORMAL);
            listTem.add(mModel);
            mModel=null;
        }

    }

    Handler handler= new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                refreshData();
                mRVAdapter=new RVAdapter(list,context);
                recyclerView_mla.setAdapter(mRVAdapter);
            }else if(msg.what==2){
                loadmore(page_);
                recyclerView_mla.finishLoadmore(page_,listTem);
            }
        }
    };
}
