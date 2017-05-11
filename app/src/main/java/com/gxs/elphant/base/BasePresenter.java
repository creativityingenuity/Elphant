package com.gxs.elphant.base;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public interface BasePresenter<T extends BaseView> {
    //onstart
    void attachView(T view);
    void dettachView();
}
