package com.enesigneci.passwordreminder.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.enesigneci.passwordreminder.fragment.TabDeviceAccounts;
import com.enesigneci.passwordreminder.fragment.TabEmailAccounts;
import com.enesigneci.passwordreminder.fragment.TabSocialMediaAccounts;

public class ReminderViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;


    public ReminderViewPagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabSocialMediaAccounts tabSocialMediaAccounts = new TabSocialMediaAccounts();
                return tabSocialMediaAccounts;
            case 1:
                TabEmailAccounts tabEmailAccounts = new TabEmailAccounts();
                return tabEmailAccounts;
            case 2:
                TabDeviceAccounts tabDeviceAccounts = new TabDeviceAccounts();
                return tabDeviceAccounts;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
