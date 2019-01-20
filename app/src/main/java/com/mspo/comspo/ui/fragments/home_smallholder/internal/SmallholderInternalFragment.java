package com.mspo.comspo.ui.fragments.home_smallholder.internal;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.listener.EndlessRecyclerViewScrollListener;
import com.mspo.comspo.data.remote.model.requests.NewInternalAuditRequest;
import com.mspo.comspo.data.remote.model.responses.NewInternalAuditResponse;
import com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list.SmallholderAuditListResponse;
import com.mspo.comspo.data.remote.utils.Connectivity;
import com.mspo.comspo.data.remote.utils.PrefManager;
import com.mspo.comspo.data.remote.utils.PrefManagerFilter;
import com.mspo.comspo.data.remote.webservice.APIClient;
import com.mspo.comspo.data.remote.webservice.NewInternalAuditService;
import com.mspo.comspo.data.remote.webservice.SmallholderAuditListService;
import com.mspo.comspo.ui.activities.FilterInterface;
import com.mspo.comspo.ui.activities.MainActivity;
import com.mspo.comspo.ui.adapters.InternalAuditAdapter;
import com.mspo.comspo.ui.decorators.SpacesItemDecoration;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmallholderInternalFragment extends Fragment implements FilterInterface{

    private ProgressBar progressBar;
    private RecyclerView recyclerViewAuditList;
    private SwipeRefreshLayout refreshView;
    private FloatingActionButton fab;
    private InternalAuditAdapter internalAuditAdapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    private long startTimeStamp = 0, endTimeStamp = 0;

    private AlertDialog b;
    private DatePickerDialog datePickerDialog;

    public SmallholderInternalFragment() {
    }

    public static SmallholderInternalFragment newInstance() {
        return new SmallholderInternalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setFilterListenerInternal(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_small_internal_audit, container, false);

        progressBar = view.findViewById(R.id.progress);
        recyclerViewAuditList = view.findViewById(R.id.recycler_view);
        refreshView = view.findViewById(R.id.refresh_view);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAuditList.setLayoutManager(verticalLayoutmanager);
        recyclerViewAuditList.addItemDecoration(new SpacesItemDecoration(getContext(), R.dimen.spacing_normal));


        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.setRefreshing(false);
                getAuditList("0");
            }
        });

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(verticalLayoutmanager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

            }
        };

        recyclerViewAuditList.addOnScrollListener(endlessRecyclerViewScrollListener);

        getAuditList("0");
        return view;
    }

    private void showDialog() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams") final View dialog = getLayoutInflater().inflate(R.layout.dialog_new_audit, null);
        dialogBuilder.setView(dialog);

        b = dialogBuilder.create();
        b.show();

        final AppCompatTextView startDate = dialog.findViewById(R.id.textView_StartDate);
        final AppCompatTextView endtDate = dialog.findViewById(R.id.textView_endDate);
        AppCompatImageView startCalender = dialog.findViewById(R.id.startCalender);
        AppCompatImageView endCalender = dialog.findViewById(R.id.endCalender);
        AppCompatButton create = dialog.findViewById(R.id.btnCreate);


        startCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(startDate);
            }
        });

        endCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(endtDate);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startTimeStamp == 0){
                    Toast.makeText(getActivity(),"Select Start Date",Toast.LENGTH_LONG).show();
                }else if(endTimeStamp == 0){
                    Toast.makeText(getActivity(),"Select End Date",Toast.LENGTH_LONG).show();
                }else if(startTimeStamp > endTimeStamp){
                    Toast.makeText(getActivity(),"End Date Mismatch",Toast.LENGTH_LONG).show();
                }else {
                    createNewAudit();
                    b.dismiss();
                }

            }
        });


    }

    private void createNewAudit() {

        if (Connectivity.checkInternetIsActive(getActivity())) {
            if (PrefManager.getFarmId(getActivity()) != 0) {

                progressBar.setVisibility(View.VISIBLE);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);

                NewInternalAuditRequest internalAuditRequest = new NewInternalAuditRequest(String.valueOf(mYear),
                        PrefManager.getFarmId(getActivity()),
                        String.valueOf(startTimeStamp),
                        String.valueOf(endTimeStamp));

                APIClient.getClient()
                        .create(NewInternalAuditService.class)
                        .createAudit(PrefManager.getAccessToken(getActivity()), internalAuditRequest)
                        .enqueue(new Callback<NewInternalAuditResponse>() {
                            @Override
                            public void onResponse(@NonNull Call<NewInternalAuditResponse> call, @NonNull Response<NewInternalAuditResponse> response) {

                                progressBar.setVisibility(View.GONE);
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus()) {
                                        Snackbar.make(refreshView, "New Audit Created", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        getAuditList("0");
                                    } else {
                                        Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }

                                } else {
                                    Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }

                            }

                            @Override
                            public void onFailure(@NonNull Call<NewInternalAuditResponse> call, @NonNull Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
            }else {
                Snackbar.make(refreshView, "Farm Not Registered yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(refreshView, "Check Internet Connectivity", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }

    }

    private void getDate(final AppCompatTextView txtDate) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        int month = monthOfYear + 1;
                        txtDate.setText("" + dayOfMonth + "/" + month + "/" + year);

                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

                        Log.e("ts", "ts : " + TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()));


                        /*Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis()) * 1000L);
                        String date = DateFormat.format("dd-MM-yyyy", cal).toString();

                        Log.e("ts" , "dt : "+date);*/

                        switch (txtDate.getId()) {
                            case R.id.textView_StartDate:
                                startTimeStamp = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                                break;
                            case R.id.textView_endDate:
                                endTimeStamp = TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                                break;
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (endlessRecyclerViewScrollListener != null)
            recyclerViewAuditList.addOnScrollListener(endlessRecyclerViewScrollListener);

    }

    @Override
    public void onPause() {
        super.onPause();

        if (endlessRecyclerViewScrollListener != null) {
            recyclerViewAuditList.removeOnScrollListener(endlessRecyclerViewScrollListener);
        }

        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            datePickerDialog.dismiss();
        }

        if (b != null && b.isShowing()) {
            b.dismiss();
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
                            "",
                            true
                    )
                    .enqueue(new Callback<SmallholderAuditListResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<SmallholderAuditListResponse> call, @NonNull Response<SmallholderAuditListResponse> response) {

                            if (response.isSuccessful()) {

                                if (response.body() != null) {

                                        internalAuditAdapter = new InternalAuditAdapter(getContext(), response.body().getAudits());
                                        recyclerViewAuditList.setAdapter(internalAuditAdapter);

                                }


                            } else {

                                Snackbar.make(refreshView, "Something Went Wrong", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(@NonNull Call<SmallholderAuditListResponse> call, @NonNull Throwable t) {

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


    @Override
    public void filter() {
        PrefManagerFilter managerFilter = new PrefManagerFilter(getActivity());
        Log.e("Filter_:", "internal fragment "+managerFilter.getFilterStatus() );
        getAuditList("0");
    }
}
