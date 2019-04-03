package com.mspo.comspo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.listener.EndlessRecyclerViewScrollListener;
import com.mspo.comspo.data.remote.model.responses.offline_audit_sheet.OfflineAuditSheetResponse;
import com.mspo.comspo.ui.adapters.OfflineAuditAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class OfflineFragment extends Fragment {

    private static final String SEARCH_IYPE = "searchType";

    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private ProgressBar progressBar;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private AppCompatTextView empty;
    private OfflineAuditAdapter offlineAuditAdapter;

    private Realm realm;

    private String user_type="";

    public static OfflineFragment newInstance(String user_type) {
        OfflineFragment fragment = new OfflineFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_IYPE, user_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_type = getArguments().getString(SEARCH_IYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_audits, container, false);

        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
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

        getOfflineAuditList();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (endlessRecyclerViewScrollListener != null) {
            recyclerViewAuditList.removeOnScrollListener(endlessRecyclerViewScrollListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getOfflineAuditList() {

        if(user_type != null && !user_type.equals("")) {

            RealmResults<OfflineAuditSheetResponse> result = realm.where(OfflineAuditSheetResponse.class)
                    .equalTo("userType", user_type)
                    .sort("auditId", Sort.DESCENDING)
                    .findAll();

            if (result != null && result.size() > 0) {
                offlineAuditAdapter = new OfflineAuditAdapter(getActivity(), result, true);
                recyclerViewAuditList.setAdapter(offlineAuditAdapter);
            }
        }
    }

}
