package com.gxs.elphant.contract;

import com.gxs.elphant.base.BasePresenter;
import com.gxs.elphant.base.BaseView;
import com.gxs.elphant.entity.NewsEntity;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public interface SheHuiContract {
    interface SheHuiPresenter extends BasePresenter<SheHuiContract.SheHuiView> {

        void getServiceData(int rcRefresh);
    }
    interface SheHuiView extends BaseView {

        void onGetServerData(int code, List<NewsEntity.ResultBean.DataBean> result);
    }
}
