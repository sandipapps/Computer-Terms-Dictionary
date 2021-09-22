package com.sandipbhattacharya.computertermsdictionary;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AddNew extends AppCompatActivity {
    // Declare the View object references
    EditText etNewTerm, etNewTermFullForm;
    TextView tvAddResult;
    // Declare a DatabaseAdapter object reference, since you'll be calling a method in DatabaseAdapter
    DatabaseAdapter databaseAdapter;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new);
        // Instantiate databaseAdapter
        databaseAdapter = new DatabaseAdapter(this);
        // Get handles for the Views
        etNewTerm = findViewById(R.id.etNewTerm);
        etNewTermFullForm = findViewById(R.id.etNewTermFullForm);
        tvAddResult = findViewById(R.id.tvAddResult);
        mAdView = findViewById(R.id.adViewAddNew);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void addNewTerm(View view) {
        // Get the values of Term and Full Form from EditTexts and store in Strings
        String etNew = etNewTerm.getText().toString().trim();
        String etNewFullForm = etNewTermFullForm.getText().toString().trim();
        // Check if both the EditTexts are not empty
        if (!etNew.equals("") && !etNewFullForm.equals("")) {
            // If true, call insertTerm() method in DatabaseAdapter and pass Term and Full Form as parameter
            long rowInserted = databaseAdapter.insertTerm(etNew, etNewFullForm);
            // You will define insertTerm in DatabaseAdapter
            // If rowInserted is not -1, then the record has been successfully inserted, otherwise there was a problem
            if (rowInserted != -1) {
                tvAddResult.setText("One Term successfully inserted.");
            } else {
                tvAddResult.setText("Something went wrong.");
            }
        }else{
            // When any EditText is empty, show an error message in TextView
            tvAddResult.setText("Fields should not be empty.");
        }
    }
}
