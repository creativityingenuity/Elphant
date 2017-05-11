package com.gxs.elphant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gxs.elphant.R;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public abstract class FooterViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //FootView
    public List<T> list;
    private boolean hasMoreData = true;// TODO: 2017/2/12

    public FooterViewAdapter(List<T> list) {
        this.list = list;
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer_view, parent, false);
            return new FooterViewHolder(foot_view);
        } else {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    //数据itemViewHolder 实现
    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            //没有更多数据
            if (hasMoreData) {
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.VISIBLE);
                ((FooterViewHolder) holder).mTextView.setText("加载中...");
            } else {
                ((FooterViewHolder) holder).mProgressView.setVisibility(View.GONE);
                ((FooterViewHolder) holder).mTextView.setText("没有更多数据了...");
            }
        } else {
            onBindItemViewHolder((VH) holder, position);
        }
    }

    //数据itemViewHolder 实现
    public abstract void onBindItemViewHolder(final VH holder, int position);

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public final ProgressBar mProgressView;
        public final TextView mTextView;

        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (ProgressBar) view.findViewById(R.id.progress_view);
            mTextView = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
