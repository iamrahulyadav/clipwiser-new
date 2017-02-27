/*
 * Copyright © 2016, Craftsvilla.com
 *  Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package com.clipwiser.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clipwiser.R;


/**
 * Created by Mahesh Nayak on 05-02-2016.
 */
public class BasePagerFragment extends BaseFragment {

    protected ViewPager mFragmentPager;
    protected TabLayout mTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayout( R.layout.fragment_pager);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPager = (ViewPager ) view.findViewById( R.id.viewpager);
        mTabLayout = (TabLayout ) view.findViewById( R.id.sliding_tabs);
    }

}
