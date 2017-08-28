package com.sn.loadmore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.sn.Config.Config;
import com.sn.Model;
import com.sn.RVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16Fan.Saila2007 on 2017/8/25.
 */

public class RecyclerViewLoadMore extends RecyclerView{

    private ReScrollListener mReScrollListener=null;
    private LayoutManager mLayoutManager = null;
    private List<Model>list;
    //    private boolean isLoadmore=false;
    private Context context=null;

    private RecyclerView.Adapter mAdapter=null;

    private int page_=1;

    public RecyclerViewLoadMore(Context context) {
        super(context);
        this.context=context;
    }


    public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public RecyclerViewLoadMore(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
    }

    public void initParams(Context context,LayoutManager lm){

        setLayoutManager(lm);
        setItemAnimator(new DefaultItemAnimator());
        ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);

        if(mLayoutManager==null){
            mLayoutManager= getLayoutManager();
        }

        if(mLayoutManager==null){
            return;
        }

        if(mLayoutManager instanceof LinearLayoutManager){
            mReScrollListener = new ReScrollListener((LinearLayoutManager) mLayoutManager);
            addOnScrollListener(mReScrollListener);
        }else if(mLayoutManager instanceof GridLayoutManager){
            mReScrollListener = new ReScrollListener((GridLayoutManager) mLayoutManager);
            addOnScrollListener(mReScrollListener);
        }

    }

    public  void getAdapterListData(){

        mAdapter=getAdapter();

        if(mAdapter instanceof  RVAdapter){
            list=((RVAdapter) mAdapter).getList();
        }

    };

    public void  finishLoadmore(
            final int page
            , final List<Model> listTem){

        postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                getAdapterListData();
                list.remove(list.size() - 1);
                int page2=page;
                if(listTem!=null&&listTem.size()>0){
                    list.addAll(listTem);
                }else{
                    page2=page-1;
                }
                if(mAdapter!=null){
                    mAdapter.notifyDataSetChanged();
                }

                page_=page2;

                if(mLoadmoreListener!=null){
                    mLoadmoreListener.finishLoadmore(page2,list);
                }

                if(mReScrollListener!=null){
                    mReScrollListener.finishLoadmore();
                }
            }
        }, 1000);

    };



    public void doLoadmore() {


        getAdapterListData();

        if (list != null && list.size() > 0 ) {
            Model mModel = null;
            try {
                mModel = list.get(list.size() - 1);
            } catch (Exception e) {
                // TODO: handle exception
                return;
            }

            if (mModel == null) {
                return;
            }
            if (Config.TYPE_VIEWHOLDER_LOAD_MORE.equals(mModel.getType_())) {

            } else {

                /**
                 * now
                 */
                mModel = new Model();
                mModel.setType_(Config.TYPE_VIEWHOLDER_LOAD_MORE);
                mModel.setCodeViewType(Config.CODE_VIEWTYPE_LOAD_MORE);
                list.add(mModel);
                mAdapter.notifyDataSetChanged();


            }

            if(mLoadmoreListener!=null){
                mLoadmoreListener.loadmore(page_,list);
            }
        }

    }

    public interface  LoadmoreListener{
        void loadmore(int page,List<Model>list);
        void finishLoadmore(int page,List<Model>list);
        void isTop(boolean isTop);
        void isBottom(boolean isBottom);
        void scrollItem(int itemPosition);
        void scrollItemLast(int lastPostion);

    };
    public LoadmoreListener mLoadmoreListener;
    public void  setLoadmoreListener(LoadmoreListener mLoadmoreListener){
        this.mLoadmoreListener=mLoadmoreListener;
    }


    class ReScrollListener extends RecyclerView.OnScrollListener{

        private LayoutManager mLayoutManager = null;
//        private LinearLayoutManager mLinearLayoutManager = null;

        private int firstVisibleItem = 0;
        private int totalItemCount = 0;
        private int visibleItemCount = 0;
        private int lastVisibleItem=0;

        private int previousTotal = 0;
        private boolean isLoading = false;

        public ReScrollListener(LayoutManager mLayoutManager) {
            // TODO Auto-generated constructor stub
            this.mLayoutManager = mLayoutManager;
        }

        public void finishLoadmore() {
            isLoading = false;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // TODO Auto-generated method stub
            super.onScrolled(recyclerView, dx, dy);

            if (mLoadmoreListener != null) {
                if (!recyclerView.canScrollVertically(-1)) {
                    mLoadmoreListener.isTop(true);
                }else {
                    mLoadmoreListener.isTop(false);
                }
//			if (!recyclerView.canScrollVertically(1)) {
//				mRecyclerViewLoadMore.isBottom();
//			}
            }
            visibleItemCount = recyclerView.getChildCount();
            if(mLayoutManager instanceof  LinearLayoutManager){
                firstVisibleItem = ((LinearLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager)mLayoutManager).findLastVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
            }else if(mLayoutManager instanceof  GridLayoutManager){
                firstVisibleItem = ((GridLayoutManager)mLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = ((GridLayoutManager)mLayoutManager).findLastVisibleItemPosition();
                totalItemCount = mLayoutManager.getItemCount();
            }

            if (mLoadmoreListener != null) {
                mLoadmoreListener.scrollItem(firstVisibleItem);
                mLoadmoreListener.scrollItemLast(lastVisibleItem);
            }
            if (isLoading) {
                if (totalItemCount >previousTotal) {
//				isLoading = false;
                    previousTotal = totalItemCount;
                } else if (totalItemCount < previousTotal) {
                    previousTotal = 0;
                    previousTotal = totalItemCount;
                } else if(totalItemCount ==previousTotal){
                    if(firstVisibleItem==0){
//                    isLoading=false;
                        previousTotal = 0;
                    } else if((firstVisibleItem+visibleItemCount)<totalItemCount){
//                    isLoading=false;
                    }else{
                    }
                }
            }

            if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem)&&firstVisibleItem>9) {
                doLoadmore();
                isLoading = true;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            // TODO Auto-generated method stub
            super.onScrollStateChanged(recyclerView, newState);
        }

    }

}
