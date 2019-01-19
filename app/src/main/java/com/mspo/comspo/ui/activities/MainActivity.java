package com.mspo.comspo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.requests.LogoutRequest;
import com.mspo.comspo.data.remote.model.responses.ErrorResponse;
import com.mspo.comspo.data.remote.model.responses.LogoutResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.ErrorUtils;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.LogoutService;
import com.mspo.comspo.ui.activities.login.LoginActivity;
import com.mspo.comspo.ui.activities.profile.AuditorProfileActivity;
import com.mspo.comspo.ui.activities.profile.ProfileActivity;
import com.mspo.comspo.ui.activities.settings.SettingsActivity;
import com.mspo.comspo.ui.fragments.home_externalaudit.HomeFragmentExternalAudit;
import com.mspo.comspo.ui.fragments.home_smallholder.external.SmallholderExternalFragment;
import com.mspo.comspo.ui.fragments.home_smallholder.HomeFragmentSmallholder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_0 = 0;
    public static final int FRAGMENT_1 = 1;
    public static final int FRAGMENT_2 = 2;

    //private BottomNavigationView bottomNavigation;
    private FloatingActionButton fab;
    private NavigationView navigationView,navigationViewFilter;
    private DrawerLayout drawer;
    private Fragment currentFragment;
    private RadioGroup statusRadioGroup;
    private AppCompatEditText search;
    private AppCompatButton apply;
    private AppCompatImageView reset;

    private FilterInterface filterInterfaceExternal,filterInterfaceInternal;



    public void setFilterListenerExternal(FilterInterface anInterface){
        filterInterfaceExternal = anInterface ;
    }

    public void setFilterListenerInternal(FilterInterface anInterface){
        filterInterfaceInternal = anInterface ;
    }
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bottom_nav_external:
                    fab.hide();
                    updateMainFragment(Pages.PAGE_0.getPagePosition());
                    return true;
                case R.id.bottom_nav_internal:
                   // fab.show();
                    updateMainFragment(Pages.PAGE_1.getPagePosition());
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //bottomNavigation =  findViewById(R.id.navigation);
        //bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here

                Log.e("drawer_:", "close");
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    Log.e("drawer_:", "open 2");

                    PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
                    String flt = managerFilter.getFilterStatus();

                    switch(flt){

                        case "":
                            statusRadioGroup.check(R.id.rad_showAll);
                            break;
                        case "newly_assigned":
                            statusRadioGroup.check(R.id.rad_newlyAssigned);
                            break;
                        case "pending":
                            statusRadioGroup.check(R.id.rad_pending);
                            break;
                        case "on_going":
                            statusRadioGroup.check(R.id.rad_onGoing);
                            break;
                        case "completed":
                            statusRadioGroup.check(R.id.rad_notApproved);
                            break;
                        case "approved":
                            statusRadioGroup.check(R.id.rad_approved);
                            break;

                    }

                    search.setText(managerFilter.getFilterKey());


                }else {
                    Log.e("drawer_:", "open 1");
                }

            }
        };




        drawer.addDrawerListener(toggle);
        toggle.syncState();



        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationViewFilter = findViewById(R.id.nav_view_filter);
        View headerView = navigationViewFilter.getHeaderView(0);
        statusRadioGroup = headerView.findViewById(R.id.statusRadio);
        search = headerView.findViewById(R.id.edt_search);

        apply = headerView.findViewById(R.id.btnApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);

                int id = statusRadioGroup.getCheckedRadioButtonId();
                if(id == R.id.rad_showAll) {
                    Log.e("Filter_:", "status : showAll " );
                    managerFilter.setFilterStatus("");
                }else if(id == R.id.rad_newlyAssigned) {
                    Log.e("Filter_:", "status : newlyAssigned" );
                    managerFilter.setFilterStatus("newly_assigned");
                }else if(id == R.id.rad_pending) {
                    Log.e("Filter_:", "status : pending" );
                    managerFilter.setFilterStatus("pending");
                }else if(id == R.id.rad_onGoing) {
                    Log.e("Filter_:", "status : onGoing" );
                    managerFilter.setFilterStatus("on_going");
                }else if(id == R.id.rad_notApproved) {
                    Log.e("Filter_:", "status : notApproved" );
                    managerFilter.setFilterStatus("completed");
                }else if(id == R.id.rad_approved) {
                    Log.e("Filter_:", "status : approved" );
                    managerFilter.setFilterStatus("approved");
                }

                managerFilter.setFilterKey(search.getText().toString());

                filterInterfaceExternal.filter();
                filterInterfaceInternal.filter();
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        reset = headerView.findViewById(R.id.imgReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
                managerFilter.clearFilter();
                filterInterfaceExternal.filter();
                filterInterfaceInternal.filter();
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        if(PrefManager.getUserType(MainActivity.this).equals("admin")){
            updateMainFragment(Pages.PAGE_2.getPagePosition());
        }else {
            /*if(currentFragment instanceof SmallholderExternalFragment) {
                updateMainFragment(Pages.PAGE_0.getPagePosition());
            }else if(currentFragment instanceof HomeFragmentSmallholder){
                updateMainFragment(Pages.PAGE_1.getPagePosition());
            }else {*/
                updateMainFragment(Pages.PAGE_1.getPagePosition());
            //}
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("loging", "main : "+PrefManager.getUserType(MainActivity.this));
        if(PrefManager.getUserType(MainActivity.this).equals("admin")){
            navigationView.getMenu().findItem(R.id.nav_audits).setVisible(false);
            //bottomNavigation.setVisibility(View.GONE);
            fab.hide();
            navigationView.getMenu().getItem(1).setChecked(true);
        }else {
            navigationView.getMenu().findItem(R.id.nav_external_audits).setVisible(false);
            //bottomNavigation.setVisibility(View.VISIBLE);
            fab.hide();
            navigationView.getMenu().getItem(0).setChecked(true);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(currentFragment instanceof HomeFragmentSmallholder) {
                PrefManagerFilter managerFilter = new PrefManagerFilter(MainActivity.this);
                managerFilter.clearFilter();
                finish();
                super.onBackPressed();
            }/*else if(currentFragment instanceof HomeFragmentSmallholder){
                updateMainFragment(Pages.PAGE_0.getPagePosition());
            }*/else if(currentFragment instanceof HomeFragmentExternalAudit){
                finish();
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_audits) {
            //bottomNavigation.setVisibility(View.VISIBLE);
            fab.hide();
            //bottomNavigation.setSelectedItemId(R.id.bottom_nav_external);
            updateMainFragment(Pages.PAGE_1.getPagePosition());
        } else if (id == R.id.nav_profile) {
            if(PrefManager.getUserType(MainActivity.this).equals("admin")) {
                startActivity(AuditorProfileActivity.getIntent(MainActivity.this));
            }else {
                startActivity(ProfileActivity.getIntent(MainActivity.this));
            }

        }else if(id == R.id.nav_settings){
            startActivity(SettingsActivity.getIntent(MainActivity.this));
        } else if (id == R.id.nav_signout) {

            if(Connectivity.checkInternetIsActive(MainActivity.this)) {
                if (PrefManager.getUserId(MainActivity.this) != 0) {

                    Log.e("logTest", "user_id : " + PrefManager.getUserId(MainActivity.this));
                    Log.e("logTest", "user_accessToken : " + PrefManager.getAccessToken(MainActivity.this));
                    LogoutRequest logoutRequest = new LogoutRequest(PrefManager.getUserId(MainActivity.this));

                    APIClient.getDrinkClient()
                            .create(LogoutService.class)
                            .doLogout(PrefManager.getAccessToken(MainActivity.this),logoutRequest)
                            .enqueue(new Callback<LogoutResponse>() {
                                @Override
                                public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

                                    if (response.isSuccessful()) {
                                        if (response.body().getStatus()) {
                                            PrefManager.clearLoginShared(MainActivity.this);
                                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                            finish();
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }

                                    } else {
                                        ErrorResponse error = ErrorUtils.parseError(response);
                                        Log.e("logTest", "fail" + error.getErrorMessage());

                                        if (error.getErrorMessage()!=null &&!error.getErrorMessage().equals("")) {
                                            Snackbar.make(findViewById(android.R.id.content), error.getErrorMessage(), Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }

                                    }

                                }

                                @Override
                                public void onFailure(Call<LogoutResponse> call, Throwable t) {
                                    Snackbar.make(findViewById(android.R.id.content), "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            });
                }
            }else {
                Snackbar.make(findViewById(android.R.id.content), "Check Internet Connectivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }



        }else if(id == R.id.nav_external_audits){
            //bottomNavigation.setVisibility(View.GONE);
            fab.hide();
            updateMainFragment(Pages.PAGE_2.getPagePosition());
        }

        //DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateMainFragment(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(position);

        currentFragment = getFragment(position);

        Log.e("TEST","position_:"+position+" , frg_:"+fragment);
        if (fragment != null) {
            transaction.replace(R.id.fragment, fragment);
        }
        transaction.commit();
    }

    private Fragment getFragment(int position) {
        if (position == Pages.PAGE_0.getPagePosition()) {
            return Pages.PAGE_0.getFragment();
        }else if (position == Pages.PAGE_1.getPagePosition()) {
            return Pages.PAGE_1.getFragment();
        }else if (position == Pages.PAGE_2.getPagePosition()) {
            return Pages.PAGE_2.getFragment();
        }
        return null;
    }

    private String getFragmentTitle(int position) {
        if (position == Pages.PAGE_0.getPagePosition()) {
            return "HOME";
        }else if (position == Pages.PAGE_1.getPagePosition()) {
            return "HOME";
        }else if (position == Pages.PAGE_2.getPagePosition()) {
            return "HOME";
        }
        return null;
    }


    private enum Pages {
        PAGE_0(FRAGMENT_0, SmallholderExternalFragment.newInstance()),
        PAGE_1(FRAGMENT_1, HomeFragmentSmallholder.newInstance()),
        PAGE_2(FRAGMENT_2, HomeFragmentExternalAudit.newInstance());

        int position;
        Fragment fragment;

        Pages(int position, Fragment fragment) {
            this.position = position;
            this.fragment = fragment;
        }

        int getPagePosition() {
            return position;
        }

        Fragment getFragment() {
            return fragment;
        }
    }
}
