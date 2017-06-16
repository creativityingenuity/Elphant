package com.gxs.elphant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gxs.elphant.R;
import com.gxs.elphant.entity.NewsEntity;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class HomeAdapter extends FooterViewAdapter<NewsEntity.ResultBean.DataBean,HomeAdapter.HomeViewHolder>{
    private Context mContext;
    public HomeAdapter(List<NewsEntity.ResultBean.DataBean> result, Context mContext) {
        super(result);
        this.mContext = mContext;
    }
    @Override
    public HomeViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_home, parent, false));
    }
    @Override
    public void onBindItemViewHolder(HomeViewHolder holder, int position) {
        final NewsEntity.ResultBean.DataBean data = list.get(position);
        Glide.with(mContext).load(data.thumbnail_pic_s).into(holder.mIcon);
        holder.mTitle.setText(data.title);
        holder.mAuthor.setText(data.author_name);
        //设置item点击事件
        if(mOnItemClickListener!=null){
            final int layoutPosition = holder.getLayoutPosition();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(data.url,layoutPosition);
                }
            });
        }
    }
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String contact,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mTitle;
        TextView mAuthor;
        public HomeViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
            mAuthor = (TextView) itemView.findViewById(R.id.item_author);
        }
    }
}
