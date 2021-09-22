package com.sandipbhattacharya.computertermsdictionary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ShowTerm extends AppCompatActivity {
    // Declare a DatabaseAdapter object reference
    static DatabaseAdapter databaseAdapter;
    // Declare a RecyclerView object reference
    static RecyclerView rvTerms;
    // Declare an Adapter object reference
    TermsAdapter termsAdapter;
    // Declare a LayoutManager object reference
    RecyclerView.LayoutManager layoutManager;
    // Define an ArrayList of type Term
    static ArrayList<Term> termsList = new ArrayList<>();
    private AdView mAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_term);
        mAdView = findViewById(R.id.adViewShowTerm);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Get the received String from Intent
        String termStartsWith = getIntent().getStringExtra("termStartsWith");
        // Instantiate DatabaseAdapter class and pass this for the Context
        databaseAdapter = new DatabaseAdapter(this);
        // Call getSomeTerms() on databaseAdapter object and store the returned ArrayList in
        // termsList
        termsList = databaseAdapter.getSomeTerms(termStartsWith);
        // Obtain a handle for the RecyclerView
        rvTerms = findViewById(R.id.rvTerms);
        // You may use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvTerms.setHasFixedSize(true);
        // Instantiate the linear layout manager
        layoutManager = new LinearLayoutManager(this);
        // Set the layout with RecyclerView
        rvTerms.setLayoutManager(layoutManager);
        // Create an instance of TermsAdapter. Pass context, termsList and the
        // RecyclerView to the constructor
        termsAdapter = new TermsAdapter(this, termsList, rvTerms);
        // Finally, attach the adapter with the RecyclerView
        rvTerms.setAdapter(termsAdapter);
    }
}
