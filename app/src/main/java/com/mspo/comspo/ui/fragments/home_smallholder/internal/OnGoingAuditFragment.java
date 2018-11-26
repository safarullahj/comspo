package com.mspo.comspo.ui.fragments.home_smallholder.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mspo.comspo.R;
import com.mspo.comspo.ui.adapters.InternalAuditAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;


public class OnGoingAuditFragment extends Fragment {

    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private InternalAuditAdapter internalAuditAdapter;

    public static OnGoingAuditFragment newInstance() {
        return new OnGoingAuditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_going_audit, container, false);

        recyclerViewAuditList = view.findViewById(R.id.recycler_view);
        refreshView = view.findViewById(R.id.refresh_view);

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAuditList.setLayoutManager(verticalLayoutmanager);
        recyclerViewAuditList.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.line_spacing_normal));

        internalAuditAdapter = new InternalAuditAdapter(getContext());
        recyclerViewAuditList.setAdapter(internalAuditAdapter);

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.setRefreshing(false);
            }
        });

        return view;
    }
}
