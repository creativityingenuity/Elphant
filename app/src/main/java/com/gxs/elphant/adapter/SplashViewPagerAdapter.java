package com.gxs.elphant.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gxs.elphant.widget.MyVideoView;

import java.util.List;


/**
 * Created by GOG on 2016/12/10.
 */

public class SplashViewPagerAdapter extends PagerAdapter {
    private List<MyVideoView> videos;

    public SplashViewPagerAdapter(List<MyVideoView> videos) {
        this.videos = videos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MyVideoView view = videos.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
