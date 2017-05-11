package com.gxs.elphant.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.gxs.elphant.R;
import com.gxs.elphant.activity.NewDetailActivity;
import com.gxs.elphant.adapter.HomeAdapter;
import com.gxs.elphant.base.BaseFragment;
import com.gxs.elphant.contract.TopContract;
import com.gxs.elphant.entity.NewsEntity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.presenter.TopPresenterImpl;
import com.gxs.elphant.widget.AutoLoadRecylerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class TopFragment extends BaseFragment implements TopContract.TopView, HomeAdapter.OnItemClickListener {
    @Bind(R.id.recycler_home)
    AutoLoadRecylerView mRecyclerView;
    @Bind(R.id.refresh_home)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.loading)
    ProgressBar mPbLoading;

    public View mView;
    public Context mContext;
    private HomeAdapter mHomeAdapter;
    private TopContract.TopPresenter mPresenter;
    private String type;

    public static TopFragment newInstance(String type) {
        TopFragment topFragment = new TopFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        topFragment.setArguments(args);
        return topFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            type = getArguments().getString("type");
            XLog.i(type);
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
        mPresenter = new TopPresenterImpl();
        mPresenter.attachView(this);
        if (!TextUtils.isEmpty(type)) {
            mPresenter.getServiceData(Constant.RC_REFRESH, type);
        }
    }

    private void initView() {
        mPbLoading.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setLoadMoreListener(new AutoLoadRecylerView.loadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getServiceData(Constant.RC_LOADMORE, type);
            }
        });
        //解决bug
        mRecyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mSwipeRefresh.isRefreshing()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );

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
                mPresenter.getServiceData(Constant.RC_REFRESH, type);
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
        Toast.makeText(mContext, "网络错误", Toast.LENGTH_SHORT).show();
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
        intent.putExtra(Constant.NEWDETAIL, contact);
        startActivity(intent);
    }
}
