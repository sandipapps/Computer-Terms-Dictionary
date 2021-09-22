package com.sandipbhattacharya.computertermsdictionary;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    // Store the text to be shared in a String
    String shareBody = "Download CTD App now and know about all the important Computer Terms and their Full Forms: \n" +
            "https://play.google.com/store/apps/details?id=com.sandipbhattacharya.computertermsdictionary";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // Create a class containing static methods to copy the database file
        // from assets folder into: data/data/package-name/databases folder, from where the app can access it.
        // Lets name it PreCreateDB.
        // From MainActivity, call the copyDB method of PreCreateDB and pass "this" for Context
        PreCreateDB.copyDB(this);
    }

    public void show(View view) {
        // We have set a text with every button. This text simply contains an alphabet.
        // Get the clicked Button's text and store in a String variable
        String termStartsWith = ((Button) view).getText().toString().trim();
        // Create an Intent to go to another Activity where you can show all the Terms that start with a letter termStartsWith contains
        Intent intent = new Intent(this, ShowTerm.class);
        // Set termStartsWith with the Intent object as Extra
        intent.putExtra("termStartsWith", termStartsWith);
        // Start the Activity with the Intent
        startActivity(intent);
        // Create the ShowTerm class.
    }

    public void reset(View view) {
        // You'll use Android AlertDialog to ask the user about his/her choice to continue or discontinue the reset operation.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Resetting will delete all your personal data. Proceed?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Here, you call a method and pass MainActivity.this as context,
                        // to replace the database file from assets folder to databases folder.
                        PreCreateDB.resetDB(MainActivity.this);
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void addNew(View view) {
        // You'll use an Intent to go to AddNew Activity
        Intent intent = new Intent(this, AddNew.class);
        startActivity(intent);
    }

    public void rate(View view) {
        // Create an Intent that opens a URL in Google Play
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
        // As you configure this intent, pass "com.android.vending" into Intent.setPackage() so that users see your app's details
        // in the Google Play Store app instead of a chooser. Make sure your emulator has pre-installed Play Store app.
        intent.setPackage("com.android.vending");
        // Start the Activity
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Couldn't launch Play Store", Toast.LENGTH_LONG).show();
        }
    }

    public void shareApp(View view) {
        // Create a send Intent
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        // Set the Sharing Type
        sendIntent.setType("text/plain");
        // Pass your sharing content using the putExtra() method of the Intent
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Share CTD App");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        // Next, instruct Android system to let the user choose their sharing medium
        startActivity(Intent.createChooser(sendIntent, "Share using"));
        // This will pass the sendIntent along with a title to be displayed at the top of the chooser.
        // When the user chooses an application from the list, your share content will be passed to that application,
        // where he/she will be able to edit the content before sending it if they wish to do so.
    }
}