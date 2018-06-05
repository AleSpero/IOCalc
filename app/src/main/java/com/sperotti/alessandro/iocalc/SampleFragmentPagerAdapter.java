package com.sperotti.alessandro.iocalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alessandro on 27/11/2016.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Number Converter", "MIPS Converter"}; //"Hex To String", "MIPS Converter"};
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

        Fragment newFragment =  FragmentNumConv.newInstance();

            switch(position) {

                case Constants.NUMBER_CONVERTER:
                    newFragment = FragmentNumConv.newInstance();
                    break;

                case Constants.HEX_TO_STRING:
                    //TODO
                    break;

                    case Constants.MIPS_CONVERTER:
                        newFragment = MipsCalculator.newInstance();
                        break;
            }

            return newFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}