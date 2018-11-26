package com.mspo.comspo.ui.activities.record_inspection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mspo.comspo.R;


public class RecordInspectionActivity extends AppCompatActivity {

    String[] actions = new String[] {
            "BLOCK A : Management",
            "BLOCK B : Production",
            "BLOCK C : Distribution"};

    Toolbar toolbar;
    Spinner spinner;

    public static Intent getIntent(Context context) {
        return new Intent(context, RecordInspectionActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_inspection);

        toolbar = findViewById(R.id.toolbar);
        spinner = findViewById(R.id.spinner_toolBar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, actions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}

