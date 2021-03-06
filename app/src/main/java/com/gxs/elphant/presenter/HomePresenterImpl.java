package com.gxs.elphant.presenter;

import com.elvishew.xlog.XLog;
import com.gxs.elphant.adapter.ProgressSubscriber;
import com.gxs.elphant.contract.HomeContract;
import com.gxs.elphant.entity.NewsEntity;
import com.gxs.elphant.global.Constant;
import com.gxs.elphant.util.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Call:vipggxs@163.com
 * Created by YT
 * 废弃
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {
    private HomeContract.HomeView mView;
    private List<NewsEntity.ResultBean.DataBean> mList = new ArrayList<>();
    private int currentPage = 0;

    @Override
    public void attachView(HomeContract.HomeView view) {
        this.mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    @Override
    public void getServiceData(final int code) {
        if (code == Constant.RC_REFRESH) {
            //下拉刷新
            mList.clear();
        }
        Observable<NewsEntity> keji = HttpClientUtil.getInstance().getApiService().getNews("keji", "77ff047df618d89e053ac75588f9021e");
        HttpClientUtil.getInstance().getServiceData(keji, new ProgressSubscriber<NewsEntity>() {
            @Override
            public void preTask() {

            }

            @Override
            protected void endTask() {
            }

            @Override
            public void onError(Throwable e) {
                XLog.i(e);
                if (mView != null) {
                    mView.onError();
                }
            }

            @Override
            public void onNext(NewsEntity newsEntity) {
                if (newsEntity.error_code == 0) {
                    mList.addAll(newsEntity.result.data);
                    if (mView != null) {
                        mView.onGetServerData(code, mList);
                    }
                }
            }
        });
    }
}
