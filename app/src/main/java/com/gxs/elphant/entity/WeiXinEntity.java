package com.gxs.elphant.entity;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class WeiXinEntity {

    /**
     * reason : 请求成功
     * result : {"list":[{"id":"wechat_20170116034547","title":"整容脸也可以是演技派，瞅瞅李小璐就知道了！","source":"电影天堂","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-12136083.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170116034547"},{"id":"wechat_20170117023908","title":"她是唐嫣同学，艳压佟丽娅逼杨幂下跪，还抢刘诗诗风头！演技再好也只能当配！","source":"美美娱乐","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-12159805.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170117023908"}],"totalPage":3739,"ps":2,"pno":1}
     * error_code : 0
     */

    public String reason;
    public ResultBean result;
    public int error_code;

    public static class ResultBean {
        /**
         * list : [{"id":"wechat_20170116034547","title":"整容脸也可以是演技派，瞅瞅李小璐就知道了！","source":"电影天堂","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-12136083.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170116034547"},{"id":"wechat_20170117023908","title":"她是唐嫣同学，艳压佟丽娅逼杨幂下跪，还抢刘诗诗风头！演技再好也只能当配！","source":"美美娱乐","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-12159805.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170117023908"}]
         * totalPage : 3739
         * ps : 2
         * pno : 1
         */

        public int totalPage;
        public int ps;
        public int pno;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : wechat_20170116034547
             * title : 整容脸也可以是演技派，瞅瞅李小璐就知道了！
             * source : 电影天堂
             * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-12136083.jpg/640
             * mark :
             * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20170116034547
             */

            public String id;
            public String title;
            public String source;
            public String firstImg;
            public String mark;
            public String url;
        }
    }
}
