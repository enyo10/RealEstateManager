package com.openclassrooms.realestatemanager.models;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RealEstateImage {
    private static final String TAG=RealEstateImage.class.getName();

    private String imageName;
    private String imageUri;
    private String imageDescription;

    public RealEstateImage(String imageName, String imageUri, String imageDescription) {
        this.imageName = imageName;
        this.imageUri = imageUri;
        this.imageDescription = imageDescription;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;

    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    @BindingAdapter("imageUri")
    public static void loadImage(ImageView view,String imageUri) {
        Log.i(TAG, " the loadImage method is call");
        Log.i(TAG," imageUri -> "+imageUri);

        Glide.with(view.getContext())
                .load(imageUri).apply(new RequestOptions())
                .into(view);
    }
}
