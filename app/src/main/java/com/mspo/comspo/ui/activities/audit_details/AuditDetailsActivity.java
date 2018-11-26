package com.mspo.comspo.ui.activities.audit_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;

import com.mspo.comspo.R;
import com.mspo.comspo.ui.activities.record_inspection.RecordInspectionActivity;


public class AuditDetailsActivity extends AppCompatActivity{

    private AppCompatButton record_inspection;

    public static Intent getIntent(Context context) {
        return new Intent(context, AuditDetailsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_details);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FarmingColours");

        record_inspection = findViewById(R.id.btn_Record);

        record_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RecordInspectionActivity.getIntent(AuditDetailsActivity.this));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

