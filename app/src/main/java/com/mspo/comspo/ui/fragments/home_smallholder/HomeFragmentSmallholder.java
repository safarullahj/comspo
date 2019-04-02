package com.mspo.comspo.ui.fragments.home_smallholder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.ui.fragments.OfflineFragment;
import com.mspo.comspo.ui.fragments.home_smallholder.external.SmallholderExternalFragment;
import com.mspo.comspo.ui.fragments.home_smallholder.internal.SmallholderInternalFragment;


public class HomeFragmentSmallholder extends Fragment {

    private ViewPager mViewPager;
    private AuditsPagerAdapter auditsPagerAdapter;


    public static HomeFragmentSmallholder newInstance() {
        return new HomeFragmentSmallholder();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_smallholder, container, false);

        auditsPagerAdapter = new AuditsPagerAdapter(getChildFragmentManager());

        mViewPager = view.findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(auditsPagerAdapter);


        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }


    private class AuditsPagerAdapter extends FragmentPagerAdapter {

        AuditsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = SmallholderExternalFragment.newInstance();
                    break;
                case 1:
                    fragment = SmallholderInternalFragment.newInstance();
                    break;
                case 2:
                    fragment = OfflineFragment.newInstance("operator");
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "External";
                case 1:
                    return "Internal";
                case 2:
                    return "Offline";

            }
            return null;
        }
    }
}
