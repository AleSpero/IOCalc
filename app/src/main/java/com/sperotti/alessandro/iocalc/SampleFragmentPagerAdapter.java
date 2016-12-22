package com.sperotti.alessandro.iocalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alessandro on 27/11/2016.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Number Converter", "MIPS Converter"};
    private int nTabs;

    public SampleFragmentPagerAdapter(FragmentManager fm, int nTab) {
        super(fm);
        this.nTabs=nTab;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            return FragmentNumConv.newInstance();
        }
        else{
            return MipsCalculator.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}