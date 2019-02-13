package com.mspo.comspo.ui.fragments.home_externalaudit;

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


public class HomeFragmentExternalAudit extends Fragment {

    private ViewPager mViewPager;
    private AuditsPagerAdapter auditsPagerAdapter;

    public static HomeFragmentExternalAudit newInstance() {
        return new HomeFragmentExternalAudit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_externalaudit, container, false);

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
                    fragment = SingleAuditFragment.newInstance();
                    break;
                case 1:
                    fragment = GroupAuditFragment.newInstance();
                    break;
                case 2:
                    fragment = SubAuditFragment.newInstance();
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
                    return "Single";
                case 1:
                    return "Group";
                case 2:
                    return "Sub";
            }
            return null;
        }
    }
}
