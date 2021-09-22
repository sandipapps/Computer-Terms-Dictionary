package com.sandipbhattacharya.computertermsdictionary;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PreCreateDB {
    static String destPath;
    static String destPathwithFilename;
    // Lets define copyDB() method
    public static void copyDB(Context context){
        // Defile two String variables containing path upto "database" folder and "CTDB" file respectively
        destPath = "/data/data/" + context.getPackageName() + "/databases";
        destPathwithFilename = destPath+"/CTDB";
        // Create two File objects from those Strings
        File fPath = new File(destPath);
        File fPathWithName = new File(destPathwithFilename);
        // Now, the question is, why we created two separate File objects?
        // It's because in some devices databases folder will be automatically created by Android system.
        // In some other devices it won't be there by default.
        // So, we need to check if it's not present in the device.
        if(!fPath.exists()){
            // If true, you'll create the databases folder
            fPath.mkdirs();
            // And then copy the CTDB Database file from assets folder to databases folder.
            // You'll define a method named rawCopy that takes an InputStream and an OutputStream.
            // This method will copy the file.
            try {
                rawCopy(context.getAssets().open("CTDB"), new FileOutputStream(destPathwithFilename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void rawCopy(InputStream inputStream, OutputStream outputStream) throws IOException {
        // To copy 1k bytes at a time, create a byte array of size 1024
        byte[] buffer = new byte[1024];
        // Declare an integer variable to store the total number of bytes read from the buffer.
        int length;
        // If you call read() method on inputStream object and pass buffer as parameter, it will read 1024 bytes at a time.
        // It returns -1 if there is no more data because the end of the stream has been reached.
        // Using this information you use a while loop to read from the inputStream and write to the outputStream.
        // This copies the database file CTDB from assets folder to data/data/[package-name]/databases folder.
        while((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }
        // Close the input and output streams once you're done
        inputStream.close();
        outputStream.close();
    }

    public static void resetDB(Context context) {
        // Call rawCopy() inside try block
        try {
            rawCopy(context.getAssets().open("CTDB"), new FileOutputStream(destPathwithFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
