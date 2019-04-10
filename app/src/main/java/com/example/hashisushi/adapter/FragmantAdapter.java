package com.example.hashisushi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hashisushi.views.fragmant_tab.ComboFragmanto;
import com.example.hashisushi.views.fragmant_tab.DriksFragmanto;
import com.example.hashisushi.views.fragmant_tab.EnterFragmanto;
import com.example.hashisushi.views.fragmant_tab.PlatAceFragmanto;
import com.example.hashisushi.views.fragmant_tab.PlatHotFragmanto;
import com.example.hashisushi.views.fragmant_tab.TemakisFragmanto;

public class FragmantAdapter extends FragmentStatePagerAdapter {

    private String[] mTabTitles;

    public FragmantAdapter(FragmentManager fm, String[] mTabTitle) {
        super(fm);
        this.mTabTitles = mTabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EnterFragmanto();
            case 1:
                return new PlatHotFragmanto();
            case 2:
                return new PlatAceFragmanto();
            case 3:
                return new TemakisFragmanto();
            case 4:
                return new ComboFragmanto();
            case 5:
                return new DriksFragmanto();
            default:
                return null;
        }
    }
    //conta qt de titolos
    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return this.mTabTitles[position];
    }

}
