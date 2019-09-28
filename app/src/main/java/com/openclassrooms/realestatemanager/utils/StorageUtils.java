package com.openclassrooms.realestatemanager.utils;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class StorageUtils {

    private static File createOrGetFile(File destination, String fileName, String folderName){
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    public static File getFileFromStorage(File rootDestination, Context context, String fileName, String folderName){
        return createOrGetFile(rootDestination, fileName, folderName);
    }

    // ----------------------------------
    // READ & WRITE ON STORAGE
    // ----------------------------------

    private static String readOnFile(Context context, File file){

        String result = null;
        if (file.exists()) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }
                    result = sb.toString();
                }
                finally {
                    br.close();
                }
            }
            catch (IOException e) {
                Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
            }
        }

        return result;
    }

    // ---

    private static void writeOnFile(Context context, String text, File file){

        try {
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            Writer w = new BufferedWriter(new OutputStreamWriter(fos));

            try {
                w.write(text);
                w.flush();
                fos.getFD().sync();
            } finally {
                w.close();
                Toast.makeText(context, context.getString(R.string.saved), Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.error_happened), Toast.LENGTH_LONG).show();
        }
    }

    private String saveToInternalStorage(Context context,Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public static String getTextFromStorage(File rootDestination, Context context, String fileName, String folderName){
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return readOnFile(context, file);
    }

    public static void setTextInStorage(File rootDestination, Context context, String fileName, String folderName, String text){
        File file = createOrGetFile(rootDestination, fileName, folderName);
        writeOnFile(context, text, file);
    }

    // ----------------------------------
    // EXTERNAL STORAGE
    // ----------------------------------

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }


    /**
     * This method to load image from storage.
     * @param image_name, the name of the image.
     *
     * @param imageDirectory, the directory of the image directory.
     * @return a Bitmap
     */
    private Bitmap getBitmap(String image_name,String imageDirectory){
        String fullPath = Environment.getExternalStorageDirectory()+imageDirectory;
        Bitmap thumbnail =null;
        try {
            if (isExternalStorageReadable()) {
                thumbnail = BitmapFactory.decodeFile(fullPath + "/" + image_name);
            }
        } catch (Exception e) {
            Log.e("getThumbnail() storage ", e.getMessage());
        }


        return thumbnail;
    }

}
