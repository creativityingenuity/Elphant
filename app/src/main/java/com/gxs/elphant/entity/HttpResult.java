package com.gxs.elphant.entity;

/**
 * 1所有实体的基类
 * 对服务器返回的响应进行封装：
 *      一般返回的是这样的格式
 *      {"code":0,"desc":"success","content":{返回结果集} }
 *
 */

public abstract class HttpResult<T> {
    public int code;
    public String desc;
    public T data;

    /**
     * 判断响应是否成功
     * @return
     */
    public abstract boolean isSuccess();

    /**
     * 响应是否为空
     * @return
     */
    public abstract boolean isEmpty();

    /**
     * 是否没有更多
     * @return
     */
    public abstract boolean isNoMore();
}
