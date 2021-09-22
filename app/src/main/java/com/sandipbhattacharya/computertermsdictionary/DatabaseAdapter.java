package com.sandipbhattacharya.computertermsdictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseAdapter {

    // Declare a DatabaseHelper object reference
    DatabaseHelper helper;
    // Declare a SQLiteDatabase object reference.
    SQLiteDatabase db;
    // SQLiteDatabase class has methods to create, delete, execute SQL commands and perform other common database
    // management tasks.
    // Define an ArrayList of Term object.
    ArrayList<Term> termsList = new ArrayList<Term>();
    // What is Term?
    // You'll create a Term class to contain and model the information and make it more easy to implement.
    // Define the constructor for DatabaseAdapter
    public DatabaseAdapter(Context context){
        // Instantiate helper
        helper = new DatabaseHelper(context);
        // Call getWritableDatabase() method on helper. This is going to give you an object of SQLiteDatabase. Store that in db.
        db = helper.getWritableDatabase();
        // Now, this SQLiteDatabase object, db, is going to represent the database you have and you are going to use that object
        // to perform the different queries that you want to do, for example, insert, update or delete from database.
    }

    // Define a method to close the database
    public void close() {
        helper.close();
    }

    public int deleteData(long id) {
        // Define the whereArgs String array
        String whereArgs[] = {""+id};
        // Call delete() method on db
        return db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.KEY_ID + "=?", whereArgs);
        // delete() returns the number of rows deleted as an integer.
    }

    public int updateTermFullForm(long id, String fullForm) {
        // To update the database, you need to create an object of the class called ContentValues that acts like a map, inside which you can
        // put your key-value pairs.
        // Here, what is expected is the name of the key that you give here is the name of the column in your table
        // and the value you want to put inside the column goes in the second parameter.
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_FULL_FORM, fullForm);
        // You need to create whereArgs[] array. whereArgs[] is just an array that contains the values that are substituted inside the
        // question mark (?) of whereClause at run-time, when you are executing the query.
        // whereArgs[] is going to contain the values for against you want to compare.
        String whereArgs[] = {""+id};
        // Call update() method on db
        return db.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.KEY_ID + "=?", whereArgs);
        // update() returns the number of rows affected as an integer.
        // The plain sql statement for this can be:
        // UPDATE ct SET full_form="New Value" WHERE _id=2

    }

    public long insertTerm(String term, String fullForm) {
        // Define a new ContentValues object
        ContentValues contentValues = new ContentValues();
        // Add term and fullForm into that
        contentValues.put(DatabaseHelper.KEY_TERM, term);
        contentValues.put(DatabaseHelper.KEY_FULL_FORM, fullForm);
        // Call insert() method on db object and return
        return db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    // Next, define a method that returns an ArrayList of specific Term objects where the term starts with the String in parameter.
    public ArrayList<Term> getSomeTerms(String termStartsWith){
        // Call query() method on db and store the returned cursor.
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{DatabaseHelper.KEY_ID, DatabaseHelper.KEY_TERM,
                        DatabaseHelper.KEY_FULL_FORM}, DatabaseHelper.KEY_TERM + " like '"
                + termStartsWith + "%'",null,null,null,null);
        // Here, % is a wildcard character which indicates 0 or any number of characters. So, there can be any number of characters
        // after “A”, or "B" or "S" etc.
        // The plain sql statement for this can be:
        // SELECT * FROM ct WHERE terms LIKE 'A%';
        // Use a while loop to traverse the database and populate the ArrayList of Term objects
        while (cursor.moveToNext()){
            // Get the database column index or position by passing the column name
            int index1 = cursor.getColumnIndex(DatabaseHelper.KEY_ID);
            // Now, get the value of id for that cell
            long id = cursor.getInt(index1);
            // Do the same thing to get values from other two columns
            int index2 = cursor.getColumnIndex(DatabaseHelper.KEY_TERM);
            String name = cursor.getString(index2);
            int index3 = cursor.getColumnIndex(DatabaseHelper.KEY_FULL_FORM);
            String fullForm = cursor.getString(index3);
            // Create a Term object from database values
            Term term = new Term(id, name, fullForm);
            // Add the Term object to termsList
            termsList.add(term);
        }
        // return termList
        return termsList;
    }


    // For managing all the operations related to the database, a helper class has been provided by Android
    // and it is called SQLiteOpenHelper.
    // It takes care of opening the database if it exists, creating it if it does not exists, and upgrading it as necessary.
    // So, inside DatabaseAdapter you'll create a static inner class that extends SQLiteOpenHelper.
    private static class DatabaseHelper extends SQLiteOpenHelper{

        // Define some private static final String variables to store information related to the database
        private static final String DATABASE_NAME = "CTDB";
        // Database name must be unique within an app, not across all the apps.
        private static final String TABLE_NAME = "ct";
        // When you do change the structure of the database change the version number from 1 to 2
        private static final int DATABASE_VERSION = 1;
        static final String KEY_ID = "_id";
        static final String KEY_TERM = "terms";
        static final String KEY_FULL_FORM = "full_form";
        private Context context;

        // Define the constructor
        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // Store the context received from constructor into this class's context variable
            this.context = context;
        }

        // Since, you're not creating or upgrading the database since you're using a pre-created database file
        // copied to the right location, you don't need to write any code inside onCreate() or onUpgrade().

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}