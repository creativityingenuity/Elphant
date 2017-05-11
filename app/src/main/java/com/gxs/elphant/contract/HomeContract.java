package com.gxs.elphant.contract;

import com.gxs.elphant.base.BasePresenter;
import com.gxs.elphant.base.BaseView;
import com.gxs.elphant.entity.NewsEntity;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public interface HomeContract {
    interface HomePresenter extends BasePresenter<HomeView>{

        void getServiceData(int rcRefresh);
    }
    interface HomeView extends BaseView{

        void onGetServerData(int code, List<NewsEntity.ResultBean.DataBean> result);
    }
}
