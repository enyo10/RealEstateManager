package com.openclassrooms.realestatemanager.models;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RealEstateImage {
    private static final String TAG=RealEstateImage.class.getName();

    private String imageName;
    private Bitmap bitmap;
    private String imageDescription;
    private String uri;


    public RealEstateImage(String imageName, Bitmap bitmap, String imageDescription) {
        this.imageName = imageName;
        this.bitmap = bitmap;
        this.imageDescription = imageDescription;
    }

    public RealEstateImage(String uri,String imageDescription){
        this.imageDescription=imageDescription;
        this.imageName=imageDescription;
        this.uri=uri;

    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @BindingAdapter("bitmap")
    public static void loadImage(ImageView view, Bitmap bitmap) {
        Log.i(TAG, " the loadImage method is call");
        Glide.with(view.getContext())
                .load(bitmap)
                .apply(new RequestOptions())
                .into(view);

    }

    @BindingAdapter("uri")
    public static void loadImageFromUri(ImageView view, String uri){
        Log.i(TAG," the load image from uri method is call");
        Glide.with(view.getContext())
                .load(uri)
                .apply(new RequestOptions())
                .into(view);
    }
}
