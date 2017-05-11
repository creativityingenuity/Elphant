package com.gxs.elphant.entity;

import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT
 */

public class NewsEntity {

    public String reason;
    public ResultBean result;
    public int error_code;

    public static class ResultBean {
        public String stat;
        public List<DataBean> data;

        public static class DataBean {

            public String uniquekey;
            public String title;
            public String date;
            public String category;
            public String author_name;
            public String url;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;
        }
    }
}
