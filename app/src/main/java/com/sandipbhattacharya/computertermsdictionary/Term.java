package com.sandipbhattacharya.computertermsdictionary;

import java.io.Serializable;

// Since we'll be passing Term objects with intent, it's a good idea to make the class Term, Serializable
public class Term implements Serializable {
    // Declare the member variables
    private long id;
    private String term;
    private String full_form;

    // Generate the constructor and choose all member variables to initialize by the
    // constructor
    public Term(long id, String term, String full_form) {
        this.id = id;
        this.term = term;
        this.full_form = full_form;
    }
    // Generate getter methods for all the member variables
    public long getId() {
        return id;
    }

    public String getTerm() {
        return term;
    }

    public String getFullForm() {
        return full_form;
    }
}
