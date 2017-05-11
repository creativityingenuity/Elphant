package com.gxs.elphant.presenter;

import com.gxs.elphant.contract.NewDetailContract;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class NewDetailPresenterImpl implements NewDetailContract.NewDetailPresenter {
    private NewDetailContract.NewDetailView mView;

    @Override
    public void attachView(NewDetailContract.NewDetailView view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }
}
