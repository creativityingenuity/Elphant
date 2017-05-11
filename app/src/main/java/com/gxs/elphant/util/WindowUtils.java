package com.gxs.elphant.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * window工具类
 * Call:vipggxs@163.com
 * Created by YT on 2016/12/22.
 */

public class WindowUtils {
    /**
     * 获取屏幕宽高22
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getWindowSize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
