package com.sn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sn.Config.Config;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 16Fan.Saila2007 on 2017/8/26.
 */

public class RVAdapter extends RecyclerView.Adapter{
    private List<Model> list=null;
    private Context context=null;

    private LayoutInflater inflater=null;

    private Model mModel=null;

    public RVAdapter(List<Model> list,Context context) {
        this.list=list;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getCodeViewType()== Config.CODE_VIEWTYPE_LOAD_MORE){
            return Config.CODE_VIEWTYPE_LOAD_MORE;
        }else if (list.get(position).getCodeViewType()== Config.CODE_VIEWTYPE_NORMAL){
            return Config.CODE_VIEWTYPE_NORMAL;
        }

        return Config.CODE_VIEWTYPE_NORMAL;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (Config.CODE_VIEWTYPE_LOAD_MORE==viewType){

            return new HolderLoadmore(inflater.inflate(R.layout.loadmore_layout,parent,false));
        }else if (Config.CODE_VIEWTYPE_NORMAL==viewType){

            return new HolderNormal(inflater.inflate(R.layout.normal_layout,parent,false));
        }

        return new HolderNormal(inflater.inflate(R.layout.normal_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mModel=list.get(position);
        if(mModel==null){
            return;
        }

        if (holder instanceof  HolderNormal){
            ((HolderNormal)holder).tv_normal.setText(mModel.getContent_());
        }else if(holder instanceof  HolderLoadmore){

        }

    }

    @Override
    public int getItemCount() {
        if (list==null) {
            return 0;
        }
        return list.size();
    }
    public List<Model> getList(){
        return list;
    }


    class HolderNormal extends RecyclerView.ViewHolder{

        public TextView tv_normal;
        public HolderNormal(View itemView) {
            super(itemView);
            tv_normal= (TextView) itemView.findViewById(R.id.tv_normal);
        }
    }

    class HolderLoadmore extends RecyclerView.ViewHolder{

        public TextView tv_loadmore;
        public HolderLoadmore(View itemView) {
            super(itemView);
            tv_loadmore= (TextView) itemView.findViewById(R.id.tv_loadmore);
        }
    }
}
