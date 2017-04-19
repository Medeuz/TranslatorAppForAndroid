package com.medeuz.translatorapp.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentsAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentsList;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
        fragmentsList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment) {
        fragmentsList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        if (fragmentsList == null) {
            return 0;
        } else {
            return fragmentsList.size();
        }
    }

}
