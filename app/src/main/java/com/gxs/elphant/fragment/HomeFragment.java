package com.gxs.elphant.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gxs.elphant.R;
import com.gxs.elphant.activity.NewDetailActivity;
import com.gxs.elphant.adapter.HomeAdapter;
import com.gxs.elphant.base.BaseFragment;
import com.gxs.elphant.contract.HomeContract;
import com.gxs.elphant.entity.NewsEntity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.presenter.HomePresenterImpl;
import com.gxs.elphant.widget.AutoLoadRecylerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Call:vipggxs@163.com
 * Created by YT
 * 废弃
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView, HomeAdapter.OnItemClickListener {

    @Bind(R.id.recycler_home)
    AutoLoadRecylerView mRecyclerView;
    @Bind(R.id.refresh_home)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.loading)
    ProgressBar mPbLoading;
    private HomeContract.HomePresenter mPresenter;

    public View mView;
    public Context mContext;
    private HomeAdapter mHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, mView);
        }
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new HomePresenterImpl();
        mPresenter.attachView(this);
        mPresenter.getServiceData(Constant.RC_REFRESH);
    }

    private void initView() {
        mPbLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setLoadMoreListener(new AutoLoadRecylerView.loadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getServiceData(Constant.RC_LOADMORE);
            }
        });

        mSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //设置下拉监听
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getServiceData(Constant.RC_REFRESH);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.dettachView();
    }

    @Override
    public void onError() {
        Toast.makeText(mContext,"网络错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetServerData(int code, List<NewsEntity.ResultBean.DataBean> result) {
        mPbLoading.setVisibility(View.GONE);
        if (code == Constant.RC_REFRESH) {
            mSwipeRefresh.setRefreshing(false);
        } else if (code == Constant.RC_LOADMORE) {
            mRecyclerView.setLoading(false);
        }
        if (mHomeAdapter == null) {
            mHomeAdapter = new HomeAdapter(result, mContext);
            mRecyclerView.setAdapter(mHomeAdapter);
            mHomeAdapter.setOnItemClickListener(this);
        } else {
            mHomeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(String contact, int position) {
        Intent intent = new Intent(mContext, NewDetailActivity.class);
        intent.putExtra(Constant.NEWDETAIL,contact);
        startActivity(intent);
    }
}
