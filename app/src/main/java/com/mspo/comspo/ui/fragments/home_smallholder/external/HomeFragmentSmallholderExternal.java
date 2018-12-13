package com.mspo.comspo.ui.fragments.home_smallholder.external;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.listener.EndlessRecyclerViewScrollListener;
import com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list.SmallholderAuditListResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.SmallholderAuditListService;
import com.mspo.comspo.ui.adapters.ExternalAuditAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragmentSmallholderExternal extends Fragment {

    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private ExternalAuditAdapter externalAuditAdapter;
    private ProgressBar progressBar;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;


    public static HomeFragmentSmallholderExternal newInstance() {
        return new HomeFragmentSmallholderExternal();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_smallholder_external, container, false);

        progressBar = view.findViewById(R.id.progress);
        recyclerViewAuditList = view.findViewById(R.id.recycler_view);
        refreshView = view.findViewById(R.id.refresh_view);

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAuditList.setLayoutManager(verticalLayoutmanager);
        recyclerViewAuditList.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.line_spacing_normal));


        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.setRefreshing(false);
            }
        });

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(verticalLayoutmanager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        };

        recyclerViewAuditList.addOnScrollListener(endlessRecyclerViewScrollListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (endlessRecyclerViewScrollListener != null)
            recyclerViewAuditList.addOnScrollListener(endlessRecyclerViewScrollListener);

        getAuditList("0");
    }


    @Override
    public void onPause() {
        super.onPause();

        if (endlessRecyclerViewScrollListener != null) {
            recyclerViewAuditList.removeOnScrollListener(endlessRecyclerViewScrollListener);
        }
    }

    private void getAuditList(String offset) {

        if (Connectivity.checkInternetIsActive(getContext())) {

            progressBar.setVisibility(View.VISIBLE);

            APIClient.getDrinkClient()
                    .create(SmallholderAuditListService.class)
                    .getAuditList(PrefManager.getFarmId(getActivity()),
                            PrefManager.getAccessToken(getActivity()),
                            "15",
                            offset,
                            "",
                            "",
                            "",
                            PrefManager.getFarmId(getActivity()),
                            "",
                            false
                    )
                    .enqueue(new Callback<SmallholderAuditListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SmallholderAuditListResponse> call, @NonNull Response<SmallholderAuditListResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                    //if(externalAuditAdapter == null){
                                        externalAuditAdapter = new ExternalAuditAdapter(getContext() , response.body().getAudits());
                                        recyclerViewAuditList.setAdapter(externalAuditAdapter);
                                    //}

                                }


                            } else {

                                Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<SmallholderAuditListResponse> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(refreshView, "SOmething went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(refreshView, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
    }
}
