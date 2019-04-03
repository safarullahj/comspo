package com.mspo.comspo.ui.fragments.home_smallholder.external;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.listener.EndlessRecyclerViewScrollListener;
import com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list.SmallholderAuditListResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.SmallholderAuditListService;
import com.mspo.comspo.ui.activities.FilterInterface;
import com.mspo.comspo.ui.activities.MainActivity;
import com.mspo.comspo.ui.adapters.SmallholderExternalAuditAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmallholderExternalFragment extends Fragment implements FilterInterface {

    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private SmallholderExternalAuditAdapter smallholderExternalAuditAdapter;
    private ProgressBar progressBar;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private AppCompatTextView empty;


    public static SmallholderExternalFragment newInstance() {
        return new SmallholderExternalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setFilterListenerExternal(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_small_externa_auditl, container, false);

        progressBar = view.findViewById(R.id.progress);
        recyclerViewAuditList = view.findViewById(R.id.recycler_view);
        refreshView = view.findViewById(R.id.refresh_view);
        empty = view.findViewById(R.id.txt_empty);
        empty.setVisibility(View.GONE);

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAuditList.setLayoutManager(verticalLayoutmanager);
        recyclerViewAuditList.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.spacing_normal));


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
            PrefManagerFilter managerFilter = new PrefManagerFilter(getActivity());

            APIClient.getClient()
                    .create(SmallholderAuditListService.class)
                    .getAuditList(PrefManager.getFarmId(getActivity()),
                            PrefManager.getAccessToken(getActivity()),
                            "15",
                            offset,
                            managerFilter.getFilterKey(),
                            "",
                            managerFilter.getFilterStatus(),
                            PrefManager.getFarmId(getActivity()),
                            managerFilter.getFilterYear(),
                            false
                    )
                    .enqueue(new Callback<SmallholderAuditListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SmallholderAuditListResponse> call, @NonNull Response<SmallholderAuditListResponse> response) {

                            if (response.isSuccessful()) {
                               // Log.e("empty_:", "ext success ");
                                if (response.body() != null) {
                                  //  Log.e("empty_:", "not null ");
                                   // Log.e("empty_:", "size " + response.body().getAudits().size());

                                    if (response.body().getAudits() != null && response.body().getAudits().size() > 0) {
                                        refreshView.setVisibility(View.VISIBLE);
                                        empty.setVisibility(View.GONE);
                                    } else {
                                        refreshView.setVisibility(View.GONE);
                                        empty.setVisibility(View.VISIBLE);
                                    }
                                    smallholderExternalAuditAdapter = new SmallholderExternalAuditAdapter(getContext(), response.body().getAudits());
                                    recyclerViewAuditList.setAdapter(smallholderExternalAuditAdapter);

                                } else {
                                   // Log.e("empty_:", "ext null ");
                                }


                            } else {

                                /*Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();*/
                                Snackbar.make(refreshView, R.string.response_fail, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<SmallholderAuditListResponse> call, Throwable t) {

                            progressBar.setVisibility(View.GONE);
                            /*Snackbar.make(refreshView, "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();*/
                            Snackbar.make(refreshView, ""+t.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(refreshView, R.string.check_internet_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void filter() {
        PrefManagerFilter managerFilter = new PrefManagerFilter(getActivity());
        Log.e("Filter_:", "External Fragment " + managerFilter.getFilterStatus());
        getAuditList("0");
    }
}
