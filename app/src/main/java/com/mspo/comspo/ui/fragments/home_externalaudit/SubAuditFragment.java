package com.mspo.comspo.ui.fragments.home_externalaudit;

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
import com.mspo.comspo.data.remote.model.responses.auditor_home_audit_list.AuditorAuditListResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.AuditorAuditListService;
import com.mspo.comspo.ui.activities.FilterInterface;
import com.mspo.comspo.ui.activities.MainActivity;
import com.mspo.comspo.ui.adapters.AuditorAuditsAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubAuditFragment extends Fragment implements FilterInterface {

    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private ProgressBar progressBar;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private AppCompatTextView empty;
    private AuditorAuditsAdapter auditorAuditsAdapter;

    public static SubAuditFragment newInstance() {
        return new SubAuditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setFilterListenerSub(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_audits, container, false);

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
                    .create(AuditorAuditListService.class)
                    .getAuditList(PrefManager.getAccessToken(getActivity()),
                            offset,
                            "15",
                            "1",
                            managerFilter.getFilterKey(),
                            managerFilter.getFilterYear(),
                            PrefManager.getFarmId(getActivity()),
                            managerFilter.getFilterStatus(),
                            "sub_audit",
                            ""
                    )
                    .enqueue(new Callback<AuditorAuditListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<AuditorAuditListResponse> call, @NonNull Response<AuditorAuditListResponse> response) {

                            if (response.isSuccessful()) {
                                Log.e("empty_:", "ext success ");
                                if (response.body() != null) {
                                    Log.e("empty_:", "not null ");
                                    Log.e("empty_:", "size " + response.body().getAudits().size());

                                    if (response.body().getAudits() != null && response.body().getAudits().size() > 0) {
                                        refreshView.setVisibility(View.VISIBLE);
                                        empty.setVisibility(View.GONE);
                                    } else {
                                        refreshView.setVisibility(View.GONE);
                                        empty.setVisibility(View.VISIBLE);
                                    }

                                    auditorAuditsAdapter = new AuditorAuditsAdapter(getContext(), response.body().getAudits());
                                    recyclerViewAuditList.setAdapter(auditorAuditsAdapter);


                                } else {
                                    Log.e("empty_:", "ext null ");
                                }


                            } else {
                                Log.e("chk_adtr:", "else err");
                                Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<AuditorAuditListResponse> call, Throwable t) {
                            Log.e("chk_adtr:", "err msg : " + t.getMessage());
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(refreshView, "Something went wrong. Try again...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(refreshView, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
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
