package com.android.project.orderfoodsserver_app.adapter;

import java.util.List;

import com.android.project.orderfoodsserver_app.model.Banner;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class CustomSliderAdapter extends SliderAdapter {
    private List<Banner> bannerList;

    public CustomSliderAdapter(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide(bannerList.get(position).getImage());
    }
}
