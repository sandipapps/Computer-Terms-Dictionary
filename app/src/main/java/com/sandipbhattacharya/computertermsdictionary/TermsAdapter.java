package com.sandipbhattacharya.computertermsdictionary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Create the Adapter by extending RecyclerView.Adapter. This custom ViewHolder
// will give access to your views
public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    // Declare variables to store data from the constructor
    Context context;
    ArrayList<Term> termsList;
    RecyclerView rvTerms;
    // Instantiate a custom OnClickListener
    final View.OnClickListener onClickListner = new MyOnClickListner();
    // Create a static inner class and provide references to all the views
    // for each data item. This is particularly useful for caching the views
    // within the item layout for fast access.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Declare member variables for all the views in a row
        TextView term;
        TextView full_form;
        // Create the constructor in a way that accepts the entire row and search the view hierarchy
        // to find each subview.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Store the item subviews in member variables
            term = itemView.findViewById(R.id.tvTerm);
            full_form = itemView.findViewById(R.id.tvFullForm);
        }
    }

    // Provide a suitable constructor for TermsAdapter
    public TermsAdapter(Context context, ArrayList<Term> termsList, RecyclerView rvTerms){
        this.context = context;
        this.termsList = termsList;
        this.rvTerms = rvTerms;
    }

    // In onCreateViewHolder() method you create new views that will be invoked
    // by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a LayoutInflater object
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.single_item, parent, false);
        // RecyclerView don’t allow to set an OnItemClickListener as ListView does,
        // so you have to create our own way to do it.
        view.setOnClickListener(onClickListner);
        // Create and return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // In onBindViewHolder() method you replace the contents of a view and this will be
    // invoked by the layout manager
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Here, get element from your dataset at this position and replace the
        // contents of the view with that element
        Term term = termsList.get(position);
        holder.term.setText(term.getTerm());
        holder.full_form.setText(term.getFullForm());
    }

    // In getItemCount() method you return the size of your dataset
    @Override
    public int getItemCount() {
        return termsList.size();
    }

    // Define the custom OnClickListener
    private class MyOnClickListner implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // Get the child layout position from RecyclerView object by passing the child view
            int itemPosition = rvTerms.getChildLayoutPosition(v);
            // Get the corresponding Term object from termsList by passing the itemPosition
            Term termSelected = termsList.get(itemPosition);
            // Create an Intent to go to TermDetails Activity with termSelected and itemPosition as extra
            Intent intent = new Intent(context, TermDetail.class);
            intent.putExtra("termSelected", termSelected);
            intent.putExtra("itemPosition", itemPosition);
            // Start the Activity using the Intent
            context.startActivity(intent);
            // Next, create the TermDetail class
        }
    }
}