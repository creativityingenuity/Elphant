package com.gxs.elphant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gxs.elphant.fragment.TopFragment;
import com.gxs.elphant.global.Constant;

/**
 * home界面与标签联动的viewpager的adapter
 * Call:vipggxs@163.com
 * Created by YT
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] mFragments = {
            Constant.TOP, Constant.SHEHUI, Constant.GUONEI, Constant.GUOJI, Constant.YULE, Constant.TIYU, Constant.JUNSHI, Constant.KEJI, Constant.CAIJING, Constant.SHISHANG
    };

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return TopFragment.newInstance(mFragments[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
