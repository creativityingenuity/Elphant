package com.gxs.elphant.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class AutoLoadRecylerView extends RecyclerView {
    private loadMoreListener loadMoreListener;
    private AutoLoadScroller autoLoadScroller;
    private boolean isLoading = false;

    public interface loadMoreListener {
        void onLoadMore();
    }

    public AutoLoadRecylerView(Context context) {
        this(context, null);
    }


    public AutoLoadRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoLoadScroller = new AutoLoadScroller();
        addOnScrollListener(autoLoadScroller);
    }


    public void setLoadMoreListener(AutoLoadRecylerView.loadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void removeAutoScroller() {
        removeOnScrollListener(autoLoadScroller);
    }

    private class AutoLoadScroller extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
			//判断是否是列表排列
            if (getLayoutManager() instanceof LinearLayoutManager) {
				//findLastVisibleItemPosition获取列表底部item对应记录下标
                int lastVisiblePos = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int itemCount = getAdapter().getItemCount();
                if (loadMoreListener != null && !isLoading && lastVisiblePos > itemCount - 2 && dy > 0) {
                    loadMoreListener.onLoadMore();
                    isLoading = true;
                }
            }
        }
    }
}
